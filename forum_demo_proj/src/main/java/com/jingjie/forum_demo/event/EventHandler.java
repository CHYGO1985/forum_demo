package com.jingjie.forum_demo.event;

import java.util.List;

/**
 *
 * The interface for event handler.
 *
 * @author jingjiejiang
 * @history
 * 1. Created on Jan 31, 2018
 *
 */
public interface EventHandler {

    // handle an event
    public void handleEvent (EventModel model);

    // get the event types that the event handler supports
    List<EventType> getSupportEventType();
}
