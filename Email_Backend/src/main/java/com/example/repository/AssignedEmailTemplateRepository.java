package com.example.repository;

import com.example.entity.AssignedEmailTemplate;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssignedEmailTemplateRepository extends JpaRepository<AssignedEmailTemplate, Long> {
	 List<AssignedEmailTemplate> findByFlag(Integer flag);
	 boolean existsByDataEmailAndTemplateId(String string, Long templateId);
}
