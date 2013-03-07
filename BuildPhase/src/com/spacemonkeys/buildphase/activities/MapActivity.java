package com.spacemonkeys.buildphase.activities;

import java.util.Date;
import java.util.HashMap;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.spacemonkeys.buildphase.R;
import com.spacemonkies.util.EventInfo;
import com.spacemonkies.util.MainMapFragment;

public class MapActivity extends Activity {

	 private MainMapFragment mapFragment;
	 private HashMap<Marker, EventInfo> eventMarkerMap;

	 @Override
	 protected void onCreate(final Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 setContentView(R.layout.map_activity);
		 
		 mapFragment = new MainMapFragment();
		 final android.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
		 ft.add(R.id.map, mapFragment);
		 ft.commit();
	 }

	 @Override
	 protected void onStart() {
		 //LatLng current = new LatLng(50.154, 4.35);
		 super.onStart();
		 //setUpEventSpots();
	 }

	 private void setUpEventSpots() {
		 final String comment="Click here for more Comments";

		final LatLng current1 = new LatLng(50.154, 4.35);
		final LatLng current2 = new LatLng(51.154, 4.35);
		final LatLng current3 = new LatLng(52.154, 4.35);
		final LatLng current4 = new LatLng(50.154, 3.35);
		final LatLng current5 = new LatLng(49.154, 3.35);
		final LatLng current6 = new LatLng(49.154, 4.35);
		final LatLng current7 = new LatLng(49.054, 3.35);
		final LatLng current8 = new LatLng(51.154, 5.35);
		final LatLng current9 = new LatLng(52.154, 5.35);
		final LatLng current10 = new LatLng(50.154, 5.35);
		final LatLng current11= new LatLng(51.154, 6.35);
		final LatLng current12= new LatLng(52.154, 6.35);
		final LatLng current13 = new LatLng(50.154, 6.35);
		final LatLng current14 = new LatLng(50.154, 4.35);
		final LatLng current15 = new LatLng(50.154, 4.35);

		final EventInfo c1EventInfo = new EventInfo(current1, "POTHOLE", new Date(), comment);
		final EventInfo c2EventInfo = new EventInfo(current2, "DAMAGED GUARDRAIL", new Date(1032, 5, 25), comment);
		final EventInfo c3EventInfo = new EventInfo(current3, "CONSTRUCTION", new Date(1032, 5, 25), comment);
		final EventInfo c4EventInfo = new EventInfo(current4, "POTHOLE", new Date(), comment);
		final EventInfo c5EventInfo = new EventInfo(current5, "Future Event", new Date(1032, 5, 25), comment);
		final EventInfo c6EventInfo = new EventInfo(current6, "POTHOLE", new Date(1032, 5, 25), comment);
		final EventInfo c7EventInfo = new EventInfo(current7, "CONSTRUCTION", new Date(), comment);
		final EventInfo c8EventInfo = new EventInfo(current8, "DAMAGED GUARDRAIL", new Date(1032, 5, 25), comment);
		final EventInfo c9EventInfo = new EventInfo(current9, "CONSTRUCTION", new Date(1032, 5, 25), comment);
		final EventInfo c10EventInfo = new EventInfo(current10, "CONSTRUCTION", new Date(), comment);
		final EventInfo c11EventInfo = new EventInfo(current11, "DAMAGED SIDEWALK", new Date(1032, 5, 25), comment);
		final EventInfo c12EventInfo = new EventInfo(current12, "POTHOLE", new Date(1032, 5, 25), comment);
		final EventInfo c13EventInfo = new EventInfo(current13, "POTHOLE", new Date(), comment);
		final EventInfo c14EventInfo = new EventInfo(current14, "Future Event", new Date(1032, 5, 25), comment);
		final EventInfo c15EventInfo = new EventInfo(current15, "CONSTRUCTION", new Date(1032, 5, 25), comment);

		mapFragment.getMap().setInfoWindowAdapter(new InfoWindowAdapter() {

		private final View contents = getLayoutInflater().inflate(R.layout.content, null);

		@Override
		public View getInfoContents(final Marker marker) {
			final EventInfo eventInfo = eventMarkerMap.get(marker);
			final String title = marker.getTitle();
			final TextView txtTitle = (TextView) contents.findViewById(R.id.txtInfoWindowTitle);
			if (title != null) {
			    // Spannable string allows us to edit the formatting of the text.
			    final SpannableString titleText = new SpannableString(title);
			    titleText.setSpan(new ForegroundColorSpan(Color.RED), 0, titleText.length(), 0);
			    txtTitle.setText(titleText);

			} else {
			    txtTitle.setText("");
			}

			    final TextView txtType = (TextView) contents.findViewById(R.id.txtInfoWindowEventType);
			    txtType.setText(eventInfo.getType());
			    return contents;
			 }

			@Override
			public View getInfoWindow(final Marker marker) {
			//Only changing the content for this tutorial
			//if you return null, it will just use the default window
			return null;

			}

		});

		 	final Marker c1Marker = mapFragment.placeMarker(c1EventInfo);
		 	final Marker c2Marker = mapFragment.placeMarker(c2EventInfo);
		 	final Marker c3Marker = mapFragment.placeMarker(c3EventInfo);
		 	final Marker c4Marker = mapFragment.placeMarker(c4EventInfo);
		 	final Marker c5Marker = mapFragment.placeMarker(c5EventInfo);
		 	final Marker c6Marker = mapFragment.placeMarker(c6EventInfo);
		 	final Marker c7Marker = mapFragment.placeMarker(c7EventInfo);
		 	final Marker c8Marker = mapFragment.placeMarker(c8EventInfo);
		 	final Marker c9Marker = mapFragment.placeMarker(c9EventInfo);
		 	final Marker c10Marker = mapFragment.placeMarker(c10EventInfo);
		 	final Marker c11Marker = mapFragment.placeMarker(c11EventInfo);
		 	final Marker c12Marker = mapFragment.placeMarker(c12EventInfo);
		 	final Marker c13Marker = mapFragment.placeMarker(c13EventInfo);
		 	final Marker c14Marker = mapFragment.placeMarker(c14EventInfo);
		 	final Marker c15Marker = mapFragment.placeMarker(c15EventInfo);

		 	eventMarkerMap = new HashMap<Marker, EventInfo>();
		 	eventMarkerMap.put(c1Marker, c1EventInfo);
		 	eventMarkerMap.put(c2Marker, c2EventInfo);
		 	eventMarkerMap.put(c3Marker, c3EventInfo);
		 	eventMarkerMap.put(c4Marker, c4EventInfo);
		 	eventMarkerMap.put(c5Marker, c5EventInfo);
		 	eventMarkerMap.put(c6Marker, c6EventInfo);
		 	eventMarkerMap.put(c7Marker, c7EventInfo);
		 	eventMarkerMap.put(c8Marker, c8EventInfo);
		 	eventMarkerMap.put(c9Marker, c9EventInfo);
		 	eventMarkerMap.put(c10Marker, c10EventInfo);
		 	eventMarkerMap.put(c11Marker, c11EventInfo);
		 	eventMarkerMap.put(c12Marker, c12EventInfo);
		 	eventMarkerMap.put(c13Marker, c13EventInfo);
		 	eventMarkerMap.put(c14Marker, c14EventInfo);
		 	eventMarkerMap.put(c15Marker, c15EventInfo);
		 	//add the onClickInfoWindowListener
		 	mapFragment.getMap().setOnInfoWindowClickListener(new OnInfoWindowClickListener() {

		 		@Override
		 		public void onInfoWindowClick(final Marker marker) {
		 			eventMarkerMap.get(marker);
		 			Toast.makeText(getBaseContext(),
		 			"Come again!",
		 			Toast.LENGTH_LONG).show();
		 		}

		 	});
	 }

}
