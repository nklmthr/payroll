package com.nklmthr.crm.payroll.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nklmthr.crm.payroll.dto.EmployeePayment;
import com.nklmthr.crm.payroll.dto.Report;
import com.nklmthr.crm.payroll.service.EmployeeService;

@Controller
@RequestMapping("/ui")
public class ReportUiController {

	private static final Logger logger = Logger.getLogger(ReportUiController.class);

	@Autowired
	EmployeeService employeeService;

	@RequestMapping("/report")
	public String getReport(Model m) {
		logger.info("Report page accessed");
		List<EmployeePayment> empPayments = employeeService.getEmployeePayments();
		logger.info("Total Employee Payments: " + empPayments.size());
		List<Report> reports = new ArrayList<>();
		for (EmployeePayment empPayment : empPayments) {
			logger.info("Processing Employee Payment: " + empPayment.getId());
			Report report = null;
			String operationCapability = "";
			if (empPayment.getAssignment() != null) {
				operationCapability = empPayment.getAssignment().getOperationProficiency().getOperation().getName()
						+ " - " + empPayment.getAssignment().getOperationProficiency().getCapability();
			} else {
				operationCapability = "No Operation - No Capability";
			}
			logger.info("Operation Capability: " + operationCapability);
			String operationCapabilityFinal = operationCapability.concat("");
			logger.info("Final Operation Capability: " + operationCapabilityFinal);
			boolean isReportExist = true;
			report = reports.stream().filter(s -> s.getOperationCapability().equals(operationCapabilityFinal))
					.filter(s -> s.getDate().equals(empPayment.getPaymentDate())).findFirst().orElse(null);
			if (report == null) {
				report = new Report();
				isReportExist = false;
			}
			report.setOperationCapability(operationCapabilityFinal);
			report.setDate(empPayment.getPaymentDate());
			report.setAssignmentCount(report.getAssignmentCount() + 1);
			report.setSalaryWithOutOperationCapabilityBalance(
					report.getSalaryWithOutOperationCapabilityBalance().add(empPayment.getEmployeeSalary().getSalary()));
			report.setGrossSalary(report.getGrossSalary().add(empPayment.getAmount()));
			report.setTotalPf(report.getTotalPf().add(empPayment.getTotalPf()));
			report.setTotalTax(report.getTotalTax().add(empPayment.getTax()));
			report.setNetSalary(report.getNetSalary().add(empPayment.getNetSalary()));
			logger.info("Adding report for No Operation - No Capability: " + report);
			if (!isReportExist) {
				reports.add(report);
			}

		}

		m.addAttribute("reports", reports);
		return "report/report";
	}
}
