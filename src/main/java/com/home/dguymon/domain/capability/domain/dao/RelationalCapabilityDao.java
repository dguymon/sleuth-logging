package com.home.dguymon.domain.capability.domain.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.home.dguymon.domain.capability.domain.dto.RelationalCapabilityDto;

@Repository
public interface RelationalCapabilityDao extends JpaRepository<RelationalCapabilityDto, Long> {
  
}