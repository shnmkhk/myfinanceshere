package org.rabbit.model;

import java.io.Serializable;
import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable(detachable="true")
@Inheritance(strategy = InheritanceStrategy.SUBCLASS_TABLE)
public class BaseEntity implements Serializable {


             @PrimaryKey
             @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
             protected Key key;

             @Persistent
             protected Date createdOn;

             @Persistent
             protected Date lastUpdatedOn;

             @Persistent
             protected String createdBy;

             @Persistent
             protected String lastUpdatedBy;

             public Date getCreatedOn() {
                             return createdOn;
             }

             public void setCreatedOn(Date createdOn) {
                             this.createdOn = createdOn;
             }

             public Date getLastUpdatedOn() {
                             return lastUpdatedOn;
             }

             public void setLastUpdatedOn(Date lastUpdatedOn) {
                             this.lastUpdatedOn = lastUpdatedOn;
             }

             public String getCreatedBy() {
                             return createdBy;
             }

             public void setCreatedBy(String createdBy) {
                             this.createdBy = createdBy;
             }

             public String getLastUpdatedBy() {
                             return lastUpdatedBy;
             }

             public void setLastUpdatedBy(String lastUpdatedBy) {
                             this.lastUpdatedBy = lastUpdatedBy;
             }


}
