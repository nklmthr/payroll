package com.nklmthr.crm.payroll.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nklmthr.crm.payroll.dto.EmployeePayment;

public interface EmployeePaymentRepository extends JpaRepository<EmployeePayment, String> {

}
