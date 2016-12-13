package com.busqueumlugar.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.busqueumlugar.service.ImovelcompartilhadoService;
import com.busqueumlugar.service.ParceriaService;

@Controller("parceriaController")
@RequestMapping("/parceria")
@SessionAttributes({"parceriaForm"})
public class ParceriaController {
	
private static final Logger log = LoggerFactory.getLogger(ParceriaController.class);
	
	@Autowired
	private ParceriaService parceriaService;
	
	private static final String DIR_PATH_PARCERIA = "/imovel/parceria/";
	

}
