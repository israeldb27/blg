package com.busqueumlugar.service;

import com.busqueumlugar.model.ServerLocation;

public interface ServerLocationBo {
	
	ServerLocation getLocation(String ipAddress);

}
