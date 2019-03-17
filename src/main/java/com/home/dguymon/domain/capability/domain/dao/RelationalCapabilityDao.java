package com.home.dguymon.domain.capability.domain.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.home.dguymon.domain.capability.domain.dto.PersistentRelationalCapabilityDto;

/**
 * A JpaRepository-based CRUD interface
 * 
 * @author Danazn
 *
 */
@Repository
public interface RelationalCapabilityDao extends JpaRepository<PersistentRelationalCapabilityDto, Long> {
  
}