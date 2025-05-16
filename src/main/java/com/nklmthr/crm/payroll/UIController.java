package com.nklmthr.crm.payroll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nklmthr.crm.payroll.api.RestAPIService;

@Controller
@RequestMapping("/ui")
public class UIController {
	
	@Autowired
	RestAPIService restAPIService;
	
	@GetMapping("/login")
	public String getLoginPage(Model m) {
		return "login";
	}
//	
//	@GetMapping("/logout")
//	public String checkLoginPage(Model m) {
//		return "/home";
//	}
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

	@GetMapping("/employee")
	public String getEmployeePage(Model m) {
		m.addAttribute("employees", restAPIService.getEmployees().getBody().getResult());
		return "employee/employee";
	}
	
	@GetMapping("/function")
	public String getFunctionPage(Model m) {
		m.addAttribute("functions", restAPIService.getFunctions().getBody().getResult());
		return "function/function";
	}
	
	@GetMapping("/function/{functionId}/function-capability")
	public String getfunctionCapabilityPage(Model m, @PathVariable("functionId") String functionId) {
		m.addAttribute("functionCapabilities", restAPIService.getFunctionCapabilities(functionId).getBody().getResult());
		return "functionCapability/functionCapability";
	}
	
	@GetMapping("/assignment")
	public String getAssignmentPage(Model m) {
		m.addAttribute("assignments", restAPIService.getFunctionCapabilityAssignments().getBody().getResult());
		return "assignment/assignment";
	}
	
	@GetMapping("/report")
	public String getReportPage(Model m) {
		return "report/report";
	}
}
