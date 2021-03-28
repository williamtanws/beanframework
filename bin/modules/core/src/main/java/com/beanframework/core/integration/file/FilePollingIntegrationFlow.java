package com.beanframework.core.integration.file;

import java.io.File;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.file.DirectoryScanner;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.RecursiveDirectoryScanner;
import org.springframework.integration.file.filters.AcceptAllFileListFilter;
import org.springframework.integration.file.filters.CompositeFileListFilter;
import org.springframework.integration.file.filters.IgnoreHiddenFileListFilter;
import org.springframework.integration.file.filters.RegexPatternFileListFilter;
import org.springframework.integration.transaction.DefaultTransactionSynchronizationFactory;
import org.springframework.integration.transaction.ExpressionEvaluatingTransactionSynchronizationProcessor;
import org.springframework.integration.transaction.TransactionSynchronizationFactory;
import org.springframework.messaging.MessageChannel;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import com.beanframework.core.config.bean.IntegrationConfig;
import com.beanframework.core.integration.handle.ArchiveFileProcessor;
import com.beanframework.core.integration.handle.ImportFileProcessor;

/**
 * Inbound File Adapter looks for files that match the given regular expression
 * Any files that have already been processed with the same name within the same
 * JVM session will be ignored The poller is transactional and will move the
 * file to a processed directory on successful downstream processing. If there
 * is an exception in processing the file will be moved to a failed directory
 *
 */
@Configuration
public class FilePollingIntegrationFlow {

	@Autowired
	public File inboundReadDirectory;

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private MessageChannel processedChannel;

	@Autowired
	private MessageChannel failedChannel;

	@Autowired
	private ArchiveFileProcessor archiveFileProcessor;

	@Autowired
	private ImportFileProcessor importFileProcessor;

	@Autowired
	private PlatformTransactionManager transactionManager;

	@Bean
	public IntegrationFlow inboundFileIntegration(@Value("${inbound.file.poller.fixed.delay}") long period, @Value("${inbound.file.poller.max.messages.per.poll}") int maxMessagesPerPoll,
			TaskExecutor taskExecutor, MessageSource<File> fileReadingMessageSource) {
		return IntegrationFlows
				.from(fileReadingMessageSource,
						c -> c.poller(Pollers.fixedDelay(period).taskExecutor(taskExecutor).maxMessagesPerPoll(maxMessagesPerPoll)
								.transactionSynchronizationFactory(transactionSynchronizationFactory()).transactional(transactionManager)))
				.handle(importFileProcessor).channel(IntegrationConfig.READ_CHANNEL).get();
	}

	@Bean
	public IntegrationFlow processedFileIntegration() {
		return IntegrationFlows.from(IntegrationConfig.PROCESSED_CHANNEL).handle(archiveFileProcessor).get();
	}

	@Bean
	public IntegrationFlow failedFileIntegration() {
		return IntegrationFlows.from(IntegrationConfig.FAILED_CHANNEL).handle(archiveFileProcessor).get();
	}

	@Bean
	public TaskExecutor defaultTaskExecutor(@Value("${inbound.file.poller.thread.pool.size}") int poolSize) {
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(poolSize);
		return taskExecutor;
	}

//	@Bean
//	public PseudoTransactionManager transactionManager() {
//		return new PseudoTransactionManager();
//	}

	@Bean
	public TransactionSynchronizationFactory transactionSynchronizationFactory() {
		ExpressionParser parser = new SpelExpressionParser();

		ExpressionEvaluatingTransactionSynchronizationProcessor syncProcessor = new ExpressionEvaluatingTransactionSynchronizationProcessor();
		syncProcessor.setBeanFactory(applicationContext.getAutowireCapableBeanFactory());

		syncProcessor.setAfterCommitExpression(parser.parseExpression("payload.renameTo(new java.io.File(@inboundProcessedDirectory.path + T(java.io.File).separator + payload.name ))"));
		syncProcessor.setAfterCommitChannel(processedChannel);

		syncProcessor.setAfterRollbackExpression(parser.parseExpression("payload.renameTo(new java.io.File(@inboundFailedDirectory.path + T(java.io.File).separator + payload.name ))"));
		syncProcessor.setAfterRollbackChannel(failedChannel);

		return new DefaultTransactionSynchronizationFactory(syncProcessor);
	}

	@Bean
	public FileReadingMessageSource fileReadingMessageSource(DirectoryScanner directoryScanner) {
		FileReadingMessageSource source = new FileReadingMessageSource();
		source.setDirectory(this.inboundReadDirectory);
		source.setScanner(directoryScanner);
		source.setAutoCreateDirectory(true);

		return source;
	}

	@Bean
	public DirectoryScanner directoryScanner(@Value("${inbound.filename.regex}") String regex) {
		DirectoryScanner scanner = new RecursiveDirectoryScanner();
		CompositeFileListFilter<File> filter = new CompositeFileListFilter<>(
				Arrays.asList(new AcceptAllFileListFilter<File>(), new IgnoreHiddenFileListFilter(), new RegexPatternFileListFilter(regex)));

		scanner.setFilter(filter);
		return scanner;
	}
}
