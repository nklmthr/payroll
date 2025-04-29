package com.nklmthr.crm.payroll.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nklmthr.crm.payroll.dto.ReportingPillar;


@Repository
public interface ReportingPillarRepository extends JpaRepository<ReportingPillar, String> {
	
}