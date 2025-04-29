package com.nklmthr.crm.payroll.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nklmthr.crm.payroll.dto.Function;


@Repository
public interface FunctionRepository extends JpaRepository<Function, String> {

	
	
}