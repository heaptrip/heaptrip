package com.heaptrip.domain.entity.content;

/**
 * Google map
 */
public class Map {
    // points of route
    private Point[] points;

    // markers  of route
    private Marker[] markers;

    public Map() {
    }

    public Map(Point[] points, Marker[] markers) {
        this.points = points;
        this.markers = markers;
    }

    public Marker[] getMarkers() {
        return markers;
    }

    public void setMarkers(Marker[] markers) {
        this.markers = markers;
    }

    public Point[] getPoints() {
        return points;
    }

    public void setPoints(Point[] points) {
        this.points = points;
    }
}
