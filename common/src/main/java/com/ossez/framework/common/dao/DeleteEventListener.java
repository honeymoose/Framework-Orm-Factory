package com.ossez.framework.common.dao;

import org.hibernate.event.internal.DefaultDeleteEventListener;
import org.hibernate.event.spi.EventSource;
import org.hibernate.persister.entity.EntityPersister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * Delete Event Listener
 *
 * @author YuCheng Hu
 */
public class DeleteEventListener extends DefaultDeleteEventListener {
    private static Logger logger = LoggerFactory.getLogger(DeleteEventListener.class);

    /**
     * Transient Entity
     */
    protected void deleteTransientEntity(EventSource session, Object entity, boolean cascadeDeleteEnabled, EntityPersister persister,
                                         Set transientEntities) {
        super.deleteTransientEntity(session, entity, cascadeDeleteEnabled, persister,
                transientEntities == null ? new HashSet() : transientEntities);
    }
}
