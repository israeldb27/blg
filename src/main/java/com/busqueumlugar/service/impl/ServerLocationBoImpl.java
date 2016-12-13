package com.busqueumlugar.service.impl;

import org.springframework.stereotype.Component;

import com.busqueumlugar.model.ServerLocation;
import com.busqueumlugar.service.ServerLocationBo;

import java.io.IOException;
import java.net.URL;
import com.maxmind.geoip.Location;
import com.maxmind.geoip.LookupService;
import com.maxmind.geoip.regionName;


@Component
public class ServerLocationBoImpl implements ServerLocationBo {

	@Override
	public ServerLocation getLocation(String ipAddress) {

		String dataFile = "location/GeoLiteCity.dat";
		return getLocation(ipAddress, dataFile);

	}

	private ServerLocation getLocation(String ipAddress, String locationDataFile) {

	  ServerLocation serverLocation = null;

	  URL url = getClass().getClassLoader().getResource(locationDataFile);

	  if (url == null) {
		System.err.println("location database is not found - "
			+ locationDataFile);
	  } else {

	  try {

		serverLocation = new ServerLocation();

		LookupService lookup = new LookupService(url.getPath(),
				LookupService.GEOIP_MEMORY_CACHE);
		Location locationServices = lookup.getLocation(ipAddress);

		serverLocation.setCountryCode(locationServices.countryCode);
		serverLocation.setCountryName(locationServices.countryName);
		serverLocation.setRegion(locationServices.region);
		serverLocation.setRegionName(regionName.regionNameByCode(
			locationServices.countryCode, locationServices.region));
		serverLocation.setCity(locationServices.city);
		serverLocation.setPostalCode(locationServices.postalCode);
		serverLocation.setLatitude(
                                String.valueOf(locationServices.latitude));
		serverLocation.setLongitude(
                                String.valueOf(locationServices.longitude));

	  } catch (IOException e) {

		System.err.println(e.getMessage());

	  }

	 }

	 return serverLocation;

	}

}
