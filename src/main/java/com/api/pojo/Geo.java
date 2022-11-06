package com.api.pojo;

public class Geo {

	private String lat;
	private String lng;
	
	public String getLat() {
		return lat;
	}
	@Override
	public String toString() {
		return "[lat=" + lat + ", lng=" + lng + "]";
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getLng() {
		return lng;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}

}
