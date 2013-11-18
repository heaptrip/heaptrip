package com.heaptrip.web.model.profile;

import com.heaptrip.web.model.content.DateModel;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: user
 * Date: 15.11.13
 * Time: 17:48
 * To change this template use File | Settings | File Templates.
 */
public class KnowledgeModel {


    private String id;

    private DateModel begin;

    private DateModel end;

    private String specialist;

    private String location;

    private String document;

    public DateModel getBegin() {
        return begin;
    }

    public void setBegin(DateModel begin) {
        this.begin = begin;
    }

    public DateModel getEnd() {
        return end;
    }

    public void setEnd(DateModel end) {
        this.end = end;
    }

    public String getSpecialist() {
        return specialist;
    }

    public void setSpecialist(String specialist) {
        this.specialist = specialist;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


}
