package com.nklmthr.crm.payroll.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nklmthr.crm.payroll.dto.Assignment;
import com.nklmthr.crm.payroll.dto.Report;
import com.nklmthr.crm.payroll.service.AssignmentService;

@Controller
@RequestMapping("/ui")
public class ReportUiController {

	private static final Logger logger = Logger.getLogger(ReportUiController.class);

	@Autowired
	AssignmentService assignmentService;

	@RequestMapping("/report")
	public String getReport(Model m) {
		logger.info("Report page accessed");
		List<Report> reports = new ArrayList<>();
		List<Assignment> assignments = assignmentService.getFunctionCapabilityAssignments();
		if (assignments == null || assignments.isEmpty()) {
			logger.info("No Operation Capability Assignments found");
			return "report/report";
		}
		Report report = new Report();
		for (Assignment assignment : assignments) {
			if (assignment.getDate().isEqual(LocalDate.now())) {
				report.setFunctionName(assignment.getFunctionCapability().getFunction().getName());
				report.setCapabilty(assignment.getFunctionCapability().getCapability());
				report.setDate(assignment.getDate());
				report.setAssignmentCount(report.getAssignmentCount() + 1);
				report.setTotalSalary(report.getTotalSalary().add(assignment.getEmployee().getEmployeeSalary().stream()
						.filter(s -> (s.getEndDate() == null)).findFirst().get().getSalary()).setScale(2));
				report.setTotalPf(report.getTotalPf().add(assignment.getEmployee().getEmployeeSalary().stream()
						.filter(s -> (s.getEndDate() == null)).findFirst().get().getSalary()
						.multiply(new java.math.BigDecimal(0.12))).setScale(2, java.math.RoundingMode.HALF_UP));
				report.setTotalTax(report.getTotalTax().add(assignment.getEmployee().getEmployeeSalary().stream()
						.filter(s -> (s.getEndDate() == null)).findFirst().get().getSalary()
						.multiply(new java.math.BigDecimal(0.1))).setScale(2, java.math.RoundingMode.HALF_UP));
				report.setTotalNetSalary(report.getTotalNetSalary().add(assignment.getEmployee().getEmployeeSalary()
						.stream().filter(s -> (s.getEndDate() == null)).findFirst().get().getSalary()
						.subtract(assignment.getEmployee().getEmployeeSalary().stream()
								.filter(s -> (s.getEndDate() == null)).findFirst().get().getSalary()
								.multiply(new java.math.BigDecimal(0.1)))
						.subtract(assignment.getEmployee().getEmployeeSalary().stream()
								.filter(s -> (s.getEndDate() == null)).findFirst().get().getSalary()
								.multiply(new java.math.BigDecimal(0.12)))).setScale(2, java.math.RoundingMode.HALF_UP));
				
				
			}
		}
		reports.add(report);
		m.addAttribute("reports", reports);
		return "report/report";
	}
}
