package com.mq.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class res {

	@RequestMapping("/web")
	public String s(String name,HttpSession session) {
		System.out.println(name);
		session.setAttribute("name", name);
		return "login";	
	}
}
