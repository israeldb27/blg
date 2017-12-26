package com.busqueumlugar.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller("ajudaController")
@RequestMapping("/ajuda")
public class AjudaController {
	
	private static final Logger log = LoggerFactory.getLogger(AjudaController.class);
	
	private static final String DIR_PATH  				= "/ajuda/";
	
	
	@RequestMapping(value = "/conhecendoFuncionalidades", method = RequestMethod.GET)
	public String conhecendoFuncionalidades(HttpSession session){
		return DIR_PATH + "conhecendoFuncionalidades";
	}
	
	@RequestMapping(value = "/faq", method = RequestMethod.GET)
	public String faq(HttpSession session){
		return DIR_PATH + "faq";
	}
	
	@RequestMapping(value = "/conhecendoFuncionalidadesOffline", method = RequestMethod.GET)
	public String conhecendoFuncionalidadesOffline(HttpSession session){
		return DIR_PATH + "conhecendoFuncionalidades-offline";
	}
	
	@RequestMapping(value = "/faqOffline", method = RequestMethod.GET)
	public String faqOffline(HttpSession session){
		return DIR_PATH + "faq-offline";
	}
	
	@RequestMapping(value = "/sobre", method = RequestMethod.GET)
	public String sobre(HttpSession session){
		return DIR_PATH + "sobre";
	}

}
