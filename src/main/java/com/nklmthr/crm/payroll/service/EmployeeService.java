package com.nklmthr.crm.payroll.service;

import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nklmthr.crm.payroll.dao.EmployeeRepository;
import com.nklmthr.crm.payroll.dao.EmployeeSalaryRepository;
import com.nklmthr.crm.payroll.dto.Employee;
import com.nklmthr.crm.payroll.dto.EmployeeSalary;

@Service
public class EmployeeService {

	private static final Logger logger = Logger.getLogger(EmployeeService.class);
	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private EmployeeSalaryRepository employeeSalaryRepository;

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
			logger.info("Employee found: " + employee.get());
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
	
	public EmployeeSalary updateEmployeeSalary(String employeeId, String employeeSalaryId, EmployeeSalary employeeSalary) {
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

}
