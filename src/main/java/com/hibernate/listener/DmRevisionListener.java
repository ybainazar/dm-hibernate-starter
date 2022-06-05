package com.hibernate.listener;

import com.hibernate.entity.Revision;
import org.hibernate.envers.RevisionListener;

public class DmRevisionListener implements RevisionListener {

    @Override
    public void newRevision(Object revisionEntity) {
        ((Revision) revisionEntity).setUsername("testUser");
    }
}
