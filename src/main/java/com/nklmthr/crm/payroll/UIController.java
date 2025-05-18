package com.nklmthr.crm.payroll;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nklmthr.crm.payroll.api.RestAPIService;
import com.nklmthr.crm.payroll.dto.Employee;
import com.nklmthr.crm.payroll.dto.Function;
import com.nklmthr.crm.payroll.dto.FunctionCapability;

@Controller
@RequestMapping("/ui")
public class UIController {
	private static Logger logger = Logger.getLogger(UIController.class);

	@Autowired
	RestAPIService restAPIService;

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

	@GetMapping("/employee")
	public String getEmployeePage(Model m) {
		m.addAttribute("employees", restAPIService.getEmployees().getBody().getResult());
		return "employee/employee";
	}

	@GetMapping("/employee/add")
	public String getAddEmployeePage(Model m) {
		m.addAttribute("employee", new Employee());
		return "employee/addEmployee";
	}

	@PostMapping("/employee/save")
	public String getSaveEmployeePage(Model m, Employee employee) {
		restAPIService.saveEmployee(List.of(employee));
		m.addAttribute("employees", restAPIService.getEmployees().getBody().getResult());
		logger.info("Employee saved successfully");
		return "employee/employee";
	}

	@PostMapping("/employee/{employeeId}/update")
	public String getUpdateEmployeePage(Model m, @PathVariable("employeeId") String employeeId, Employee employee) {
		try {
			restAPIService.updateEmployee(employeeId, employee);
			m.addAttribute("employees", restAPIService.getEmployees().getBody().getResult());
			logger.info("Employee updated successfully");
		} catch (Exception e) {
			logger.error("Error updating employee: " + e.getMessage(), e);
			m.addAttribute("error", "Error updating employee: " + e.getMessage());
			return "redirect:edit";
		}
		return "employee/employee";
	}

	@GetMapping("/employee/{employeeId}/edit")
	public String getEditEmployeePage(Model m, @PathVariable("employeeId") String employeeId) {
		Employee employee = (Employee) restAPIService.getEmployeesById(employeeId).getBody().getResult().get(0);
		m.addAttribute("employee", employee);
		logger.info("Employee fetched successfully");

		return "employee/editEmployee";
	}

	@GetMapping("/employee/{employeeId}/delete")
	public String getDeleteEmployeePage(Model m, @PathVariable("employeeId") String employeeId) {
		restAPIService.deleteEmployee(employeeId);
		m.addAttribute("employees", restAPIService.getEmployees().getBody().getResult());
		logger.info("Employee deleted successfully");
		return "employee/employee";
	}

	@GetMapping("/function")
	public String getFunctionPage(Model m) {
		m.addAttribute("functions", restAPIService.getFunctions().getBody().getResult());
		return "function/function";
	}
	
	@GetMapping("/function/add")
	public String getAddFunctionPage(Model m) {
		m.addAttribute("function", new Function());
		return "function/addFunction";
	}
	
	@PostMapping("/function/save")
	public String getSaveFunctionPage(Model m, Function function) {
		restAPIService.saveFunction(List.of(function));
		m.addAttribute("functions", restAPIService.getFunctions().getBody().getResult());
		logger.info("Function saved successfully");
		return "function/function";
	}

	@GetMapping("/function/function-capability")
	public String getfunctionCapabilityPage(Model m, @RequestParam("functionId") String functionId) {
		m.addAttribute("functionCapabilities",
				restAPIService.getFunctionCapabilities(functionId).getBody().getResult());
		return "functionCapability/functionCapability";
	}

	@GetMapping("/function/function-capability/add")
	public String getAddFunctionCapabilityPage(Model m, @RequestParam("functionId") String functionId) {
		m.addAttribute("functionCapability", new FunctionCapability());
		m.addAttribute("functionId", functionId);
		return "functionCapability/addFunctionCapability";
	}
	@PostMapping("/function/function-capability/save")
	public String getSaveFunctionCapabilityPage(Model m, @RequestParam("functionId") String functionId,
			FunctionCapability functionCapability) {
		restAPIService.saveFunctionCapability(functionId, functionCapability);
		m.addAttribute("functionCapabilities",
				restAPIService.getFunctionCapabilities(functionId).getBody().getResult());
		logger.info("Function Capability saved successfully");
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
