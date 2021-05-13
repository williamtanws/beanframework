// package com.beanframework.platform.cache;
//
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
//
// import net.sf.ehcache.CacheException;
// import net.sf.ehcache.CacheManager;
// import net.sf.ehcache.Status;
// import net.sf.ehcache.event.CacheManagerEventListener;
//
// public class PlatformCacheManagerEventListener implements CacheManagerEventListener {
//
// private Logger logger = LoggerFactory.getLogger(getClass());
//
// private final CacheManager cacheManager;
//
// public PlatformCacheManagerEventListener(CacheManager cacheManager) {
// this.cacheManager = cacheManager;
// }
//
// @Override
// public void init() throws CacheException {
// logger.debug("init ehcache...");
// }
//
// @Override
// public Status getStatus() {
// return null;
// }
//
// @Override
// public void dispose() throws CacheException {
// logger.debug("ehcache dispose...");
// }
//
// @Override
// public void notifyCacheAdded(String s) {
// logger.debug("cacheAdded... {}", s);
// logger.debug(cacheManager.getCache(s).toString());
// }
//
// @Override
// public void notifyCacheRemoved(String s) {
//
// }
// }
