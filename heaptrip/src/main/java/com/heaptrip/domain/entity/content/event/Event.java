package com.heaptrip.domain.entity.content.event;

import com.heaptrip.domain.entity.Price;
import com.heaptrip.domain.entity.content.Content;
import com.heaptrip.domain.entity.content.Map;

/**
 * Event
 */
public class Event extends Content {

    // event types
    private EventType[] types;

    // number of members
    private long members;

    // the cost of participation
    private Price price;

    // google map
    private Map map;

    // show map or not
    private boolean showMap;

    public EventType[] getTypes() {
        return types;
    }

    public void setTypes(EventType[] types) {
        this.types = types;
    }

    public long getMembers() {
        return members;
    }

    public void setMembers(long members) {
        this.members = members;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public boolean isShowMap() {
        return showMap;
    }

    public void setShowMap(boolean showMap) {
        this.showMap = showMap;
    }

}
