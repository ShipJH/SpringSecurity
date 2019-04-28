package com.securiry.study.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
	
	// #2 ����� Ŀ���� �α��� ������.
	@RequestMapping(value="/loginForm", method=RequestMethod.GET)
	public String goLoginForm(Model model, HttpServletRequest req) {
		
		
		if(req.getParameter("loggout") != null) {
			model.addAttribute("loggout", "loggout");
		}
		
		
		return "loginPage";
	}
	
	// #2 �α������Ŀ� ���Ѻ� �������� ������ ���� ����.
	@RequestMapping(value="/defaultPage", method=RequestMethod.GET)
	public String defaultPage(Model model, HttpServletRequest req) {
		
		if(req.isUserInRole("ADMIN")) {
			return "adminPage";
		}
		return "redirect:/";
	}
	
//	@RequestMapping(value="/logout", method=RequestMethod.GET)
//	public String defaultPage3(Model model, HttpServletRequest req , HttpSession ses) {
//
//		
//		
//		service.insertLogoutLog();
//		
//		ses.invalidate();
//		
//		
//		
//		return "redirect:/";
//	}
	
}
