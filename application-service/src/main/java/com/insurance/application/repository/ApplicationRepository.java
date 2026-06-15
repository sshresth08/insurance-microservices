package com.insurance.application.repository;

import com.insurance.application.model.InsuranceApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepository extends JpaRepository<InsuranceApplication, Long> {
}