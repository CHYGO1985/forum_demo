package com.jingjie.forum_demo.event;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * The event model.
 * NB: for chain invoke, all setters return EventModel
 *
 * @author jingjiejiang
 * @history
 * 1. Created on Jan 31, 2018
 *
 */
public class EventModel {

    private EventType type;
    // the user who trigger the event
    private int actorId;
    private int entityType;
    private int entityId;
    private int entityOwnerId;

    // store fields in hash map
    private Map<String, String> extension = new HashMap<>();

    public EventModel () {}

    public EventModel (EventType type) {
        this.type = type;
    }

    public EventType getType() {
        return type;
    }

    public EventModel setType(EventType type) {
        this.type = type;
        return this;
    }

    public int getActorId() {
        return actorId;
    }

    public EventModel setActorId(int actorId) {
        this.actorId = actorId;
        return this;
    }

    public int getEntityType() {
        return entityType;
    }

    public EventModel setEntityType(int entityType) {
        this.entityType = entityType;
        return this;
    }

    public int getEntityId() {
        return entityId;
    }

    public EventModel setEntityId(int entityId) {
        this.entityId = entityId;
        return this;
    }

    public int getEntityOwnerId() {
        return entityOwnerId;
    }

    public EventModel setEntityOwnerId(int entityOwnerId) {
        this.entityOwnerId = entityOwnerId;
        return this;
    }

    public Map<String, String> getExtension() {
        return extension;
    }

    public EventModel setExtension(String key, String value) {
        this.extension.put(key, value);
        return this;
    }
}
