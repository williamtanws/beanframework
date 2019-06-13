//package com.beanframework.platform.cache;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import net.sf.ehcache.CacheException;
//import net.sf.ehcache.Ehcache;
//import net.sf.ehcache.Element;
//import net.sf.ehcache.event.CacheEventListener;
//
//public class PlatformCacheEventListener implements CacheEventListener {
//
//    private Logger logger = LoggerFactory.getLogger(getClass());
//
//    @Override
//    public void notifyElementRemoved(Ehcache ehcache, Element element) throws CacheException {
//        logger.debug("cache removed. key = {}, value = {}", element.getObjectKey(), element.getObjectValue());
//    }
//
//    @Override
//    public void notifyElementPut(Ehcache ehcache, Element element) throws CacheException {
//        logger.debug("cache put. key = {}, value = {}", element.getObjectKey(), element.getObjectValue());
//    }
//
//    @Override
//    public void notifyElementUpdated(Ehcache ehcache, Element element) throws CacheException {
//        logger.debug("cache updated. key = {}, value = {}", element.getObjectKey(), element.getObjectValue());
//    }
//
//    @Override
//    public void notifyElementExpired(Ehcache ehcache, Element element) {
//        logger.debug("cache expired. key = {}, value = {}", element.getObjectKey(), element.getObjectValue());
//    }
//
//    @Override
//    public void notifyElementEvicted(Ehcache ehcache, Element element) {
//        logger.debug("cache evicted. key = {}, value = {}", element.getObjectKey(), element.getObjectValue());
//    }
//
//    @Override
//    public void notifyRemoveAll(Ehcache ehcache) {
//        logger.debug("all elements removed. cache name = {}", ehcache.getName());
//    }
//
//    @Override
//    public Object clone() throws CloneNotSupportedException {
//        throw new CloneNotSupportedException();
//    }
//
//    @Override
//    public void dispose() {
//        logger.debug("cache dispose.");
//    }
//}