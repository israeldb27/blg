package com.busqueumlugar.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import com.busqueumlugar.service.IntermediacaoService;

@Controller("intermediacaoController")
@RequestMapping("/intermediacao")
@SessionAttributes({"intermediacaoForm"})
public class IntermediacaoController {
	
	private static final Logger log = LoggerFactory.getLogger(IntermediacaoController.class);
	
	@Autowired
	private IntermediacaoService intermediacaoService;
	
	
	private static final String DIR_PATH_INTERMEDIACOES = "/imovel/intermediacoes/";

}
