package com.nklmthr.crm.payroll.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nklmthr.crm.payroll.dto.Employee;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
	// Custom query methods can be defined here if needed
	// For example, you can add methods to find employees by different criteria
	// using Spring Data JPA's query derivation mechanism.
}