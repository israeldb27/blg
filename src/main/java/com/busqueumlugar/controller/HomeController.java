package com.busqueumlugar.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.busqueumlugar.form.UsuarioForm;

@Controller
public class HomeController {
	
	private static final Logger log = LoggerFactory.getLogger(HomeController.class);

	@RequestMapping(value="/")
	public String test(HttpServletResponse response, ModelMap map) throws IOException{		
		map.addAttribute("usuarioForm", new UsuarioForm());
		return "home";
	}
}
