package com.beanframework.core.config;

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
public class CoreCacheConfig {

  @Bean
  public CacheManager cacheManager() {
    CacheManager cacheManager =
        new org.springframework.cache.concurrent.ConcurrentMapCacheManager();
    return cacheManager;
  }

  @Bean
  public HibernatePropertiesCustomizer hibernateSecondLevelCacheCustomizer(
      CacheManager cacheManager) {
    return (properties) -> properties.put(ConfigSettings.CACHE_MANAGER, cacheManager);

  }

  @Bean
  public StatisticsService statisticsService(CacheManager cacheManager) {
    StatisticsService statisticsService = new DefaultStatisticsService();
    return statisticsService;
  }
}
