package com.nklmthr.crm.payroll.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nklmthr.crm.payroll.dao.EmployeePaymentRepository;
import com.nklmthr.crm.payroll.dao.EmployeeRepository;
import com.nklmthr.crm.payroll.dao.EmployeeSalaryRepository;
import com.nklmthr.crm.payroll.dto.Employee;
import com.nklmthr.crm.payroll.dto.EmployeePayment;
import com.nklmthr.crm.payroll.dto.EmployeeSalary;

@Service
public class EmployeeService {

	private static final Logger logger = Logger.getLogger(EmployeeService.class);
	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private EmployeeSalaryRepository employeeSalaryRepository;

	@Autowired
	private EmployeePaymentRepository employeePaymentRepository;

	public List<Employee> getEmployees() {
		List<Employee> employees = employeeRepository.findAll();
		logger.info("Employees: " + employees.size());
		return employees;
	}

	public void deleteEmployee(String employeeId) {
		logger.info("Deleting employee with ID: " + employeeId);
		employeeRepository.deleteById(employeeId);
	}

	public Employee getEmployeesById(String employeeId) {
		Optional<Employee> employee = employeeRepository.findById(employeeId);
		if (employee.isPresent()) {
			logger.info("Employee found: " + employee.get().getId());
			return employee.get();
		}
		return null;
	}

	public Employee updateEmployee(String employeeId, Employee employee) {
		Optional<Employee> emp = employeeRepository.findById(employeeId);
		if (emp.isPresent()) {
			logger.info("Updating employee with ID: " + employeeId);
			Employee emp1 = emp.get();
			emp1.update(employee);
			employeeRepository.save(emp1);
			return emp1;
		}
		return null;
	}

	public Employee saveEmployee(Employee employee) {
		logger.info("Saving employee: " + employee.getId());
		Employee emp = employeeRepository.save(employee);
		return emp;
	}

	public List<EmployeeSalary> getEmployeeSalaries(String employeeId) {
		Optional<Employee> employee = employeeRepository.findById(employeeId);
		if (employee.isPresent()) {
			List<EmployeeSalary> employeeSalaries = employee.get().getEmployeeSalary();
			logger.info("Employee salaries found: " + employeeSalaries.size());
			return employeeSalaries;
		}
		return null;
	}

	public EmployeeSalary saveEmployeeSalary(String employeeId, EmployeeSalary employeeSalary) {
		Optional<Employee> employee = employeeRepository.findById(employeeId);
		if (employee.isPresent()) {
			Employee emp = employee.get();
			employeeSalary.setEmployee(emp);
			emp.getEmployeeSalary().add(employeeSalary);
			logger.info("Saving employee salary: " + employeeSalary.getId());
			employeeSalaryRepository.save(employeeSalary);
			return employeeSalary;
		}
		return null;
	}

	public EmployeeSalary updateEmployeeSalary(String employeeId, String employeeSalaryId,
			EmployeeSalary employeeSalary) {
		Optional<Employee> employee = employeeRepository.findById(employeeId);
		if (employee.isPresent()) {
			Optional<EmployeeSalary> empSal = employeeSalaryRepository.findById(employeeSalaryId);
			if (empSal.isPresent()) {
				Employee emp = employee.get();
				EmployeeSalary empSal1 = empSal.get();
				empSal1.update(employeeSalary);
				empSal1.setEmployee(emp);
				logger.info("Updating employee salary: " + empSal1.getId());
				employeeSalaryRepository.save(empSal1);
				return empSal1;
			}
		}
		return null;
	}

	public void deleteEmployeeSalary(String employeeId, String employeeSalaryId) {
		Optional<Employee> employee = employeeRepository.findById(employeeId);
		if (employee.isPresent()) {
			Optional<EmployeeSalary> empSal = employeeSalaryRepository.findById(employeeSalaryId);
			if (empSal.isPresent()) {
				logger.info("Deleting employee salary: " + empSal.get().getId());
				employeeSalaryRepository.delete(empSal.get());
			}
		}
	}

	public List<EmployeePayment> getEmployeePayments(String employeeId) {
		Optional<Employee> employee = employeeRepository.findById(employeeId);
		if (employee.isPresent()) {
			List<EmployeePayment> employeePayments = employee.get().getEmployeePayments();
			logger.info("Employee payments found: " + employeePayments.size());
			return employeePayments;
		}
		return null;
	}

	public void saveEmployeePayment(String employeeId, EmployeePayment employeePayment) {
		Optional<Employee> employee = employeeRepository.findById(employeeId);
		if (employee.isPresent()) {
			Employee emp = employee.get();
			employeePayment.setEmployee(emp);
			emp.getEmployeePayments().add(employeePayment);
			logger.info("Saving employee payment: " + employeePayment.getId());
			employeePaymentRepository.save(employeePayment);
		}
	}

	public void updateEmployeePayment(String employeeId, String employeePaymentId, EmployeePayment employeePayment) {
		Optional<Employee> employee = employeeRepository.findById(employeeId);
		if (employee.isPresent()) {
			Optional<EmployeePayment> empPay = employeePaymentRepository.findById(employeePaymentId);
			if (empPay.isPresent()) {
				Employee emp = employee.get();
				EmployeePayment empPay1 = empPay.get();
				empPay1.update(employeePayment);
				empPay1.setEmployee(emp);
				logger.info("Updating employee payment: " + empPay1.getId());
				employeePaymentRepository.save(empPay1);
			}
		}
	}

	public void deleteEmployeePayment(String employeeId, String employeePaymentId) {
		Optional<Employee> employee = employeeRepository.findById(employeeId);
		if (employee.isPresent()) {
			Optional<EmployeePayment> empPay = employeePaymentRepository.findById(employeePaymentId);
			if (empPay.isPresent()) {
				logger.info("Deleting employee payment: " + empPay.get().getId());
				employeePaymentRepository.delete(empPay.get());
			}
		}
	}

	public void updateRegulatoryDeductions(EmployeePayment employeePayment) {
		// TODO Auto-generated method stub
		logger.info("Updating regulatory deductions for employee salary: " + employeePayment.getId());
		employeePayment.setPfEmployee(
				employeePayment.getAmount().multiply(new BigDecimal(0.12)).setScale(2, RoundingMode.HALF_UP));
		employeePayment.setPfEmployer(
				employeePayment.getAmount().multiply(new BigDecimal(0.12)).setScale(2, RoundingMode.HALF_UP));
		employeePayment.setTotalPf(
				employeePayment.getPfEmployee().add(employeePayment.getPfEmployer()).setScale(2, RoundingMode.HALF_UP));
		employeePayment
				.setTax(employeePayment.getAmount().multiply(new BigDecimal(0.1)).setScale(2, RoundingMode.HALF_UP));
		employeePayment.setNetSalary(employeePayment.getAmount().subtract(employeePayment.getTax())
				.subtract(employeePayment.getTotalPf()).setScale(2, RoundingMode.HALF_UP));
		logger.info("Regulatory deductions updated for employee salary: " + employeePayment.getId());

	}

}
