package com.nklmthr.crm.payroll.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ui")
public class HomeUIController {

	@GetMapping("/login")
	public String getLoginPage(Model m) {
		return "login";
	}

	@GetMapping("/home")
	public String getHomePage(Model m) {
		return "home";
	}

	@GetMapping("/top")
	public String getTopBar(Model m) {
		return "top";
	}

	@GetMapping("/sidebar")
	public String getSideBar(Model m) {
		return "sidebar";
	}
}
