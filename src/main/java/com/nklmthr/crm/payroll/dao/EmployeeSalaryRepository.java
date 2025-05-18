package com.nklmthr.crm.payroll.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nklmthr.crm.payroll.dto.EmployeeSalary;

public interface EmployeeSalaryRepository extends JpaRepository<EmployeeSalary, String> {

}
