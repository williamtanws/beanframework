package com.beanframework.cronjob.envers;

import org.hibernate.boot.Metadata;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.envers.boot.internal.EnversService;
import org.hibernate.envers.event.spi.EnversListenerDuplicationStrategy;
import org.hibernate.envers.event.spi.EnversPostCollectionRecreateEventListenerImpl;
import org.hibernate.envers.event.spi.EnversPostDeleteEventListenerImpl;
import org.hibernate.envers.event.spi.EnversPostInsertEventListenerImpl;
import org.hibernate.envers.event.spi.EnversPreCollectionRemoveEventListenerImpl;
import org.hibernate.envers.event.spi.EnversPreCollectionUpdateEventListenerImpl;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.integrator.spi.Integrator;
import org.hibernate.service.spi.SessionFactoryServiceRegistry;
import org.springframework.stereotype.Component;

@Component
public class CronjobEnversIntegrator implements Integrator {

	@Override
	public void integrate(Metadata metadata, SessionFactoryImplementor sessionFactory, SessionFactoryServiceRegistry serviceRegistry) {

		EventListenerRegistry listenerRegistry = serviceRegistry.getService(EventListenerRegistry.class);
		listenerRegistry.addDuplicationStrategy(EnversListenerDuplicationStrategy.INSTANCE);

		EnversService enversService = serviceRegistry.getService(EnversService.class);

		if (enversService.getEntitiesConfigurations().hasAuditedEntities()) {
			listenerRegistry.appendListeners(EventType.POST_DELETE, new EnversPostDeleteEventListenerImpl(enversService));
			listenerRegistry.appendListeners(EventType.POST_INSERT, new EnversPostInsertEventListenerImpl(enversService));
			listenerRegistry.appendListeners(EventType.PRE_UPDATE, new CronjobEnversPreUpdateEventListenerImpl(enversService));
			listenerRegistry.appendListeners(EventType.POST_UPDATE, new CronjobEnversPostUpdateEventListenerImpl(enversService));
			listenerRegistry.appendListeners(EventType.POST_COLLECTION_RECREATE, new EnversPostCollectionRecreateEventListenerImpl(enversService));
			listenerRegistry.appendListeners(EventType.PRE_COLLECTION_REMOVE, new EnversPreCollectionRemoveEventListenerImpl(enversService));
			listenerRegistry.appendListeners(EventType.PRE_COLLECTION_UPDATE, new EnversPreCollectionUpdateEventListenerImpl(enversService));
		}
	}

	@Override
	public void disintegrate(SessionFactoryImplementor sessionFactory, SessionFactoryServiceRegistry serviceRegistry) {
	}
}