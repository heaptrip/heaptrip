package com.heaptrip.domain.entity.content.event;

import com.heaptrip.domain.entity.Price;
import com.heaptrip.domain.entity.content.Content;
import com.heaptrip.domain.entity.content.ContentEnum;

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
    private String map;

    // show map or not
    private boolean showMap;

    @Override
    public ContentEnum getContentType() {
        return ContentEnum.EVENT;
    }

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

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public boolean isShowMap() {
        return showMap;
    }

    public void setShowMap(boolean showMap) {
        this.showMap = showMap;
    }

}
