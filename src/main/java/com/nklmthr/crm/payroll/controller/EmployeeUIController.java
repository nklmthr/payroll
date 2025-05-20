package com.nklmthr.crm.payroll.controller;

import java.time.LocalDate;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nklmthr.crm.payroll.dto.Employee;
import com.nklmthr.crm.payroll.dto.EmployeePayment;
import com.nklmthr.crm.payroll.dto.EmployeeSalary;
import com.nklmthr.crm.payroll.service.EmployeeService;

@Controller
@RequestMapping("/ui")
public class EmployeeUIController {
	private static final Logger logger = Logger.getLogger(EmployeeUIController.class);

	@Autowired
	EmployeeService employeeService;

	@GetMapping("/employee")
	public String getEmployeee(Model m) {
		m.addAttribute("employees", employeeService.getEmployees());
		logger.info("Employee list fetched successfully");
		return "employee/employee";
	}

	@GetMapping("/employee/add")
	public String addEmployee(Model m) {
		m.addAttribute("employee", new Employee());
		logger.info("Employee object created");
		return "employee/addEmployee";
	}

	@PostMapping("/employee/save")
	public String saveEmployee(Model m, Employee employee) {
		logger.info("Saving employee: " + employee);
		employeeService.saveEmployee(employee);
		m.addAttribute("employees", employeeService.getEmployees());
		logger.info("Employee saved successfully");
		return "employee/employee";
	}

	@GetMapping("/employee/edit")
	public String editEmployee(Model m, @RequestParam("employeeId") String employeeId) {
		Employee employee = employeeService.getEmployeesById(employeeId);
		m.addAttribute("employee", employee);
		logger.info("Employee fetched successfully");
		return "employee/editEmployee";
	}

	@PostMapping("/employee/update")
	public String updateEmployee(Model m, @RequestParam("employeeId") String employeeId, Employee employee) {
		logger.info("Updating employee: " + employee);
		employeeService.updateEmployee(employeeId, employee);
		logger.info("Employee updated successfully");
		m.addAttribute("employees", employeeService.getEmployees());
		return "employee/employee";
	}

	@GetMapping("/employee/delete")
	public String deleteEmployee(Model m, @RequestParam("employeeId") String employeeId) {
		employeeService.deleteEmployee(employeeId);
		logger.info("Employee deleted successfully"+employeeId);
		m.addAttribute("employees", employeeService.getEmployees());
		return "employee/employee";
	}

	@GetMapping("/employee/salary")
	public String getEmployeeSalaries(Model m, @RequestParam("employeeId") String employeeId) {
		m.addAttribute("employeeSalaries", employeeService.getEmployeeSalaries(employeeId));
		m.addAttribute("employee", employeeService.getEmployeesById(employeeId));
		logger.info("Employee Salary list fetched successfully");
		return "employee/employeeSalary";
	}

	@GetMapping("/employee/salary/add")
	public String addEmployeeSalaryPage(Model m, @RequestParam("employeeId") String employeeId) {
		EmployeeSalary employeeSalary = new EmployeeSalary();
		employeeSalary.setStartDate(LocalDate.now().withDayOfMonth(1));
		m.addAttribute("employeeSalary", employeeSalary);
		m.addAttribute("employee", employeeService.getEmployeesById(employeeId));
		logger.info("Employee Salary object created");
		return "employee/addEmployeeSalary";
	}

	@PostMapping("/employee/salary/save")
	public String saveEmployeeSalary(Model m, @RequestParam("employeeId") String employeeId,
			EmployeeSalary employeeSalary) {
		logger.info("Saving employee salary: " + employeeSalary);
		
		employeeService.saveEmployeeSalary(employeeId, employeeSalary);
		logger.info("Employee Salary saved successfully");
		m.addAttribute("employeeSalaries", employeeService.getEmployeeSalaries(employeeId));
		m.addAttribute("employee", employeeService.getEmployeesById(employeeId));
		return "employee/employeeSalary";
	}

	@GetMapping("/employee/salary/edit")
	public String editEmployeeSalary(Model m, @RequestParam("employeeId") String employeeId,
			@RequestParam("employeeSalaryId") String employeeSalaryId) {
		Optional<EmployeeSalary> employeeSalOpt = employeeService.getEmployeeSalaries(employeeId).stream()
				.filter(salary -> salary.getId().equals(employeeSalaryId)).findFirst();
		if (employeeSalOpt.isPresent()) {
			EmployeeSalary employeeSalary = employeeSalOpt.get();
			m.addAttribute("employeeSalary", employeeSalary);
			m.addAttribute("employee", employeeService.getEmployeesById(employeeId));
			logger.info("Employee Salary fetched successfully");
		} else {
			logger.error(
					"Employee Salary not found for Employee ID: " + employeeId + " and Salary ID: " + employeeSalaryId);
			return "error";
		}

		return "employee/editEmployeeSalary";
	}

	@PostMapping("/employee/salary/update")
	public String updateEmployeeSalary(Model m, @RequestParam("employeeId") String employeeId,
			@RequestParam("employeeSalaryId") String employeeSalaryId, EmployeeSalary employeeSalary) {
		logger.info("Updating employee salary: " + employeeSalary);
		employeeService.updateEmployeeSalary(employeeId, employeeSalaryId, employeeSalary);
		logger.info("Employee Salary updated successfully");
		m.addAttribute("employeeSalaries", employeeService.getEmployeeSalaries(employeeId));
		m.addAttribute("employee", employeeService.getEmployeesById(employeeId));
		return "employee/employeeSalary";
	}

	@GetMapping("/employee/salary/delete")
	public String deleteEmployeeSalary(Model m, @RequestParam("employeeId") String employeeId,
			@RequestParam("employeeSalaryId") String employeeSalaryId) {
		employeeService.deleteEmployeeSalary(employeeId, employeeSalaryId);
		logger.info("Employee Salary deleted successfully"+employeeSalaryId);
		m.addAttribute("employeeSalaries", employeeService.getEmployeeSalaries(employeeId));
		m.addAttribute("employee", employeeService.getEmployeesById(employeeId));
		return "employee/employeeSalary";
	}
	
	@GetMapping("/employee/payment")
	public String getEmployeePayments(Model m, @RequestParam("employeeId") String employeeId) {
		m.addAttribute("employeePayments", employeeService.getEmployeePayments(employeeId));
		m.addAttribute("employee", employeeService.getEmployeesById(employeeId));
		logger.info("Employee Payment list fetched successfully");
		return "employee/employeePayment";
	}
	
	@GetMapping("/employee/payment/add")
	public String addEmployeePaymentPage(Model m, @RequestParam("employeeId") String employeeId) {
		EmployeePayment employeePayment = new EmployeePayment();
		employeePayment.setPaymentDate(LocalDate.now());
		m.addAttribute("employeePayment", employeePayment);
		m.addAttribute("employee", employeeService.getEmployeesById(employeeId));
		logger.info("Employee Payment object created");
		return "employee/addEmployeePayment";
	}
	
	@PostMapping("/employee/payment/save")
	public String saveEmployeePayment(Model m, @RequestParam("employeeId") String employeeId,
			EmployeePayment employeePayment) {
		logger.info("Saving employee payment: " + employeePayment);
		employeeService.updateRegulatoryDeductions(employeePayment);
		employeeService.saveEmployeePayment(employeeId, employeePayment);
		logger.info("Employee Payment saved successfully");
		m.addAttribute("employeePayments", employeeService.getEmployeePayments(employeeId));
		m.addAttribute("employee", employeeService.getEmployeesById(employeeId));
		return "employee/employeePayment";
	}
	
	@GetMapping("/employee/payment/edit")
	public String editEmployeePayment(Model m, @RequestParam("employeeId") String employeeId,
			@RequestParam("employeePaymentId") String employeePaymentId) {
		Optional<EmployeePayment> employeePayOpt = employeeService.getEmployeePayments(employeeId).stream()
				.filter(payment -> payment.getId().equals(employeePaymentId)).findFirst();
		if (employeePayOpt.isPresent()) {
			EmployeePayment employeePayment = employeePayOpt.get();
			m.addAttribute("employeePayment", employeePayment);
			m.addAttribute("employee", employeeService.getEmployeesById(employeeId));
			logger.info("Employee Payment fetched successfully");
		} else {
			logger.error(
					"Employee Payment not found for Employee ID: " + employeeId + " and Payment ID: " + employeePaymentId);
			return "error";
		}

		return "employee/editEmployeePayment";
	}
	
	@PostMapping("/employee/payment/update")
	public String updateEmployeePayment(Model m, @RequestParam("employeeId") String employeeId,
			@RequestParam("employeePaymentId") String employeePaymentId, EmployeePayment employeePayment) {
		logger.info("Updating employee payment: " + employeePayment);
		employeeService.updateRegulatoryDeductions(employeePayment);
		employeeService.updateEmployeePayment(employeeId, employeePaymentId, employeePayment);
		logger.info("Employee Payment updated successfully");
		m.addAttribute("employeePayments", employeeService.getEmployeePayments(employeeId));
		m.addAttribute("employee", employeeService.getEmployeesById(employeeId));
		return "employee/employeePayment";
	}
	
	@GetMapping("/employee/payment/delete")
	public String deleteEmployeePayment(Model m, @RequestParam("employeeId") String employeeId,
			@RequestParam("employeePaymentId") String employeePaymentId) {
		employeeService.deleteEmployeePayment(employeeId, employeePaymentId);
		logger.info("Employee Payment deleted successfully"+employeePaymentId);
		m.addAttribute("employeePayments", employeeService.getEmployeePayments(employeeId));
		m.addAttribute("employee", employeeService.getEmployeesById(employeeId));
		return "employee/employeePayment";
	}

}
