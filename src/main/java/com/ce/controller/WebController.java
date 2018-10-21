package com.ce.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Controller
@EnableWebMvc

public class WebController {
	private static Logger logger = LoggerFactory.getLogger(WebController.class);
		//Get Home Page
			@GetMapping("/")
			public ModelAndView homePage() {
				logger.info("homePage() In::");
				return new ModelAndView("index");
			}
}
