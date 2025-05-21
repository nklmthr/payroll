package com.nklmthr.crm.payroll.controller;

import java.util.ArrayList;
import java.util.List;

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
		List<Report> reports = new ArrayList<>();
		List<EmployeePayment> empPayments = employeeService.getEmployeePayments();

		for (EmployeePayment empPayment : empPayments) {
			if (empPayment.getAssignment() == null) {
				Report report = reports.stream().filter(s -> s.getOperationName().equals("Assignment deleted"))
						.findFirst().orElse(null);
				logger.info("Assignment deleted: " + empPayment.getAssignment());
				logger.info(reports);
				logger.info("Report: " + report);
				if (report == null) {
					report = new Report();
					report.setOperationName("Assignment deleted");
				}
				report.setCapabilty("Assignment deleted");
				report.setDate(empPayment.getPaymentDate());
				report.setAssignmentCount(report.getAssignmentCount() + 1);
				report.setGrossSalary(report.getGrossSalary().add(empPayment.getAmount()));
				report.setTotalPf(report.getTotalPf().add(empPayment.getTotalPf()));
				report.setTotalTax(report.getTotalTax().add(empPayment.getTax()));
				report.setNetSalary(report.getNetSalary().add(empPayment.getNetSalary()));
				reports.add(report);

			} else {
				Report report = new Report();
				report.setDate(empPayment.getPaymentDate());
				report.setOperationName(empPayment.getAssignment().getOperationProficiency().getOperation().getName());
				report.setCapabilty(empPayment.getAssignment().getOperationProficiency().getCapability());
				report.setDate(empPayment.getPaymentDate());
				report.setAssignmentCount(report.getAssignmentCount() + 1);
				report.setGrossSalary(report.getGrossSalary().add(empPayment.getAmount()));
				report.setTotalPf(report.getTotalPf().add(empPayment.getTotalPf()));
				report.setTotalTax(report.getTotalTax().add(empPayment.getTax()));
				report.setNetSalary(report.getNetSalary().add(empPayment.getNetSalary()));
				reports.add(report);
			}

		}

		m.addAttribute("reports", reports);
		return "report/report";
	}
}
