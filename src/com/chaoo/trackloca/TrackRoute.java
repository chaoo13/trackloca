package com.chaoo.trackloca;

import java.util.Date;
import java.util.List;

public class TrackRoute {
	private Date fromDate;
	private Date endDate;
	private String userId;
	private int count;
	private List<TrackLocation> trackLocations;
	
	public TrackRoute(Date fromDate, Date endDate, String userId,
			List<TrackLocation> trackLocations) {
		super();
		this.fromDate = fromDate;
		this.endDate = endDate;
		this.userId = userId;
		this.trackLocations = trackLocations;
		if(trackLocations != null){
			setCount(trackLocations.size());
		}
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<TrackLocation> getTrackLocations() {
		return trackLocations;
	}

	public void setTrackLocations(List<TrackLocation> trackLocations) {
		this.trackLocations = trackLocations;
		if(!trackLocations.isEmpty()){
			setCount(trackLocations.size());
		}
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
}
