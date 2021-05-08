package com.beanframework.platform.config;

import org.ehcache.core.spi.service.StatisticsService;
import org.ehcache.core.statistics.DefaultStatisticsService;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableCaching
@Configuration
public class PlatformCacheConfig {

	@Bean
	public CacheManager cacheManager() {
		CacheManager cacheManager = new org.springframework.cache.concurrent.ConcurrentMapCacheManager();
//		ConcurrentMapCacheManager cacheManager = new ConcurrentMapCacheManager() {
//
//	        @Override
//	        protected Cache createConcurrentMapCache(final String name) {
//	            return new ConcurrentMapCache(name, CacheBuilder.newBuilder().expireAfterWrite(1440, TimeUnit.MINUTES)
//	                    .maximumSize(100).recordStats().build().asMap(), false); }
//	    };
		return cacheManager;
	}

//	@Bean
//	public JCacheCacheManager cacheManager() {
//		
//		CachingProvider cachingProvider = Caching.getCachingProvider();
//		EhcacheCachingProvider ehcacheCachingProvider = (EhcacheCachingProvider) cachingProvider;
//		
//		JCacheCacheManager jCacheCacheManager = new JCacheCacheManager();
//		jCacheCacheManager.setCacheManager(ehcacheCachingProvider.getCacheManager());
//		return jCacheCacheManager;
//	}

	@Bean
	public HibernatePropertiesCustomizer hibernateSecondLevelCacheCustomizer(CacheManager cacheManager) {
		return (properties) -> properties.put(ConfigSettings.CACHE_MANAGER, cacheManager);

	}
//
	@Bean
	public StatisticsService statisticsService(CacheManager cacheManager) {
		StatisticsService statisticsService = new DefaultStatisticsService();
		return statisticsService;
	}
}
