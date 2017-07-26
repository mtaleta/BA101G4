package com;

import java.util.ArrayList;
import java.util.List;

import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.GeocodeResponse;
import com.google.code.geocoder.model.GeocoderRequest;
import com.google.code.geocoder.model.GeocoderResult;

public class Main {

	public static void main(String[] args) {
		String address = "臺中市西區台中市西區民權路99號";
		List<Double> location = new ArrayList<Double>();
		final Geocoder geocoder = new Geocoder();
		GeocoderRequest geocoderRequest = new GeocoderRequestBuilder().setAddress(address).getGeocoderRequest();
		GeocodeResponse geocodeResponse = geocoder.geocode(geocoderRequest);

		List<GeocoderResult> results = geocodeResponse.getResults();
		if (results != null && results.size() > 0) {
			location.add(Double.parseDouble(results.get(0).getGeometry().getLocation().getLat().toString()));
			location.add(Double.parseDouble(results.get(0).getGeometry().getLocation().getLng().toString()));
			System.out.println(location);
		} else {
			System.out.println("Geocoder cannot get Lat and Lng by this address : " + address);
		}
	}

}
