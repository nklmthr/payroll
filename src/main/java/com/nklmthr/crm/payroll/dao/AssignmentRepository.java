package com.nklmthr.crm.payroll.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nklmthr.crm.payroll.dto.Assignment;


@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, String> {
	
}