package com.busqueumlugar.controller;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.busqueumlugar.model.ServerLocation;
import com.busqueumlugar.service.ServerLocationBo;

@Controller("mapController")
@RequestMapping("/gmap")
public class MapController {

	@Autowired
	ServerLocationBo serverLocationBo;
	
	@RequestMapping(value = "/inicio", method = RequestMethod.GET)
	public ModelAndView getPages() {
		ServerLocation  serverlocation = new ServerLocation();
		ModelAndView model = new ModelAndView("/imovel/map", "command", serverlocation);
		return model;
	}

	//return back json string
	@RequestMapping(value = "/getLocationByIpAddress", method = RequestMethod.GET)
	public @ResponseBody
	String getDomainInJsonFormat(@RequestParam String ipAddress) {

		ObjectMapper mapper = new ObjectMapper();

		ServerLocation location = serverLocationBo.getLocation(ipAddress);

		String result = "";

		try {
			result = mapper.writeValueAsString(location);
		} catch (Exception e) {

			e.printStackTrace();
		}

		return result;

	}
}
