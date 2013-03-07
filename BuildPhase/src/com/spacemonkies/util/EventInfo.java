package com.spacemonkies.util;

import java.util.Date;

import com.google.android.gms.maps.model.LatLng;

public class EventInfo {
	 private LatLng latLong;	 
	 private String name;	 
	 private Date someDate;	 
	 private String type;
 
	 public EventInfo(LatLng latLong, String name, Date someDate, String type) {
		 this.latLong = latLong;
		 this.name = name;
		 this.someDate = someDate;
		 this.type = type;
	 
	 }
	 
	 public LatLng getLatLong() {
	  return latLong;
	 }
	 
	 public void setLatLong(LatLng latLong) {
	  this.latLong = latLong;
	 }
	 
	 public String getName() {
	  return name;
	 }
	 
	 public void setName(String name) {
	  this.name = name;
	 }
	 
	 public Date getSomeDate() {
	  return someDate;
	 }
	 
	 public void setSomeDate(Date someDate) {
	  this.someDate = someDate;
	 }
	 
	 public String getType() {
	  return type;
	 }
	 
	 public void setType(String type) {
	  this.type = type;
	 }
	}
