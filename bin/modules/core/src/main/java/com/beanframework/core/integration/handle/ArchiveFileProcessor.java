package com.beanframework.core.integration.handle;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.compress.compressors.CompressorStreamFactory;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.file.FileHeaders;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class ArchiveFileProcessor {

	@Value("${inbound.filename.dateFormat}")
	private String dateFormat;

	@Value("${inbound.archive:false}")
	private boolean ARCHIVE;

	@Value("${inbound.archive.format:zip}")
	private String ARCHIVE_FORMAT;

	@Autowired
	public File inboundProcessedDirectory;

	byte[] buffer = new byte[1024];

	public void process(Message<String> msg) throws Exception {
		
		if (ARCHIVE) {
			String fileName = (String) msg.getHeaders().get(FileHeaders.FILENAME);

			String date = DateTimeFormatter.ofPattern(dateFormat).format(LocalDateTime.now());

			File processedFile = new File(inboundProcessedDirectory.getCanonicalPath() + File.separator + fileName);
			
			if (processedFile.exists()) {
				//Result
				File resultFile = new File(processedFile.getAbsoluteFile()+".result.txt");

				if (ARCHIVE_FORMAT.equals("gz")) {
					OutputStream outGzipFileStream = null;
					GzipCompressorOutputStream outGzipStream = null;
					FileInputStream inTarFileStream = null;
					try {
						outGzipFileStream = new FileOutputStream(processedFile.getAbsolutePath() + ".tar.gz");

						outGzipStream = (GzipCompressorOutputStream) new CompressorStreamFactory().createCompressorOutputStream(CompressorStreamFactory.GZIP, outGzipFileStream);

						inTarFileStream = new FileInputStream(processedFile.getAbsolutePath());
						IOUtils.copy(inTarFileStream, outGzipStream);						

						File gzFile = new File(processedFile.getAbsolutePath() + ".gz");
						gzFile.renameTo(new File(processedFile.getAbsolutePath() + "." + date + ".gz"));

					} finally {
						if (inTarFileStream != null) {
							inTarFileStream.close();
						}

						if (outGzipStream != null) {
							outGzipStream.close();
						}

						if (outGzipFileStream != null) {
							outGzipFileStream.close();
						}
					}

				} else if (ARCHIVE_FORMAT.equals("zip")) {
					FileOutputStream fos = null;
					ZipOutputStream zos = null;
					FileInputStream fisProcessedFile = null;
					FileInputStream fisResultFile = null;
					try {

						fos = new FileOutputStream(processedFile.getAbsolutePath() + "." + date + ".zip");
						zos = new ZipOutputStream(fos);
						ZipEntry ze = new ZipEntry(processedFile.getName());
						zos.putNextEntry(ze);
						fisProcessedFile = new FileInputStream(processedFile.getAbsolutePath());

						int len;
						while ((len = fisProcessedFile.read(buffer)) > 0) {
							zos.write(buffer, 0, len);
						}
						
						ze = new ZipEntry(resultFile.getName());
						zos.putNextEntry(ze);
						fisResultFile = new FileInputStream(resultFile.getAbsolutePath());
						
						len = 0;
						while ((len = fisResultFile.read(buffer)) > 0) {
							zos.write(buffer, 0, len);
						}

					} finally {
						if (fisProcessedFile != null) {
							fisProcessedFile.close();
						}
						if (fisResultFile != null) {
							fisResultFile.close();
						}

						if (zos != null) {
							zos.closeEntry();
							zos.close();
						}

						if (fos != null) {
							fos.close();
						}
					}

				}
				resultFile.delete();
				processedFile.delete();
			}
		}
	}
}
