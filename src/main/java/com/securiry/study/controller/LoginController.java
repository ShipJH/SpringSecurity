package com.securiry.study.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {

	@RequestMapping(value="/", method=RequestMethod.GET)
	public String welcome(Model model) {
		
		model.addAttribute("msg", "Hi~~!~!~");
		return "home";
	}
	
	@RequestMapping(value="/test", method=RequestMethod.GET)
	public String test(Model model) {
		
		model.addAttribute("msg", "Hi~~!~!~");
		return "NewFile";
	}
	
	@RequestMapping(value="/test2", method=RequestMethod.GET)
	public @ResponseBody String test2(Model model) {
		
		return "home";
	}
}
