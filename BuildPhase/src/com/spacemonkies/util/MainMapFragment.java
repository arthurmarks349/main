package com.spacemonkies.util;

import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class MainMapFragment extends MapFragment {
	 
	 public Marker placeMarker(EventInfo eventInfo) {
	  Marker m  = getMap().addMarker(new MarkerOptions().title(eventInfo.getName()));
	
	  return m;
	 
	 }
}