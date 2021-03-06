package com.heaptrip.web.model.travel;

public class TripInfoModel extends TripModel {

	private String description;
	private RouteModel route;
	

	// travel schedule
	private ScheduleModel[] schedule;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ScheduleModel[] getSchedule() {
		return schedule;
	}

	public void setSchedule(ScheduleModel[] schedule) {
		this.schedule = schedule;
	}
	
	

	public RouteModel getRoute() {
		return route;
	}

	public void setRoute(RouteModel route) {
		this.route = route;
	}

}
