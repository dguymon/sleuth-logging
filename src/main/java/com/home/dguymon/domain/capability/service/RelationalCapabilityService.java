package com.home.dguymon.domain.capability.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.home.dguymon.domain.capability.domain.dao.RelationalCapabilityDao;
import com.home.dguymon.domain.capability.domain.dto.MessageDto;
import com.home.dguymon.domain.capability.domain.dto.PersistentRelationalCapabilityDto;

/**
 * Service to provide CRUD operations against 
 * capability table in postgres database in 
 * PostGreSQL RDS.
 * 
 * @author Danazn
 *
 */
@Service
public class RelationalCapabilityService {
  
  @Autowired
  RelationalCapabilityDao relationalCapabilityDao;
  
  /**
   * Retrieves all capabilities from capability table in 
   * postgres table in PostGreSQL RDS.
   * 
   * @return List<PersistentRelationalCapabilityDto>
   */
  public List<PersistentRelationalCapabilityDto> getAllRelationalCapabilities() {
    
    return this.relationalCapabilityDao.findAll();
  }
  
  /**
   * Returns an Optional-wrapped capability from the capability table in 
   * postgres database in PostGreSQL RDS.
   * 
   * @param id Primary key of the capability to retrieve.
   * @return Optional<PersistentRelationalCapabilityDto> the retrieved capability.
   */
  public Optional<PersistentRelationalCapabilityDto> getRelationalCapabilityById(Long id) {
    
    return this.relationalCapabilityDao.findById(id);
  }
  
  /**
   * Updates or creates a capability in capability table in 
   * postgres database in PostGreSQL RDS.
   * 
   * @param persistentRelationalCapabilityDto The capability data to create or 
   * update with.
   * @return Long the primary key id of the capability that was updated or created.
   */
  public Long updateOrCreateRelationalCapability(PersistentRelationalCapabilityDto persistentRelationalCapabilityDto) {
    this.relationalCapabilityDao.saveAndFlush(persistentRelationalCapabilityDto);
    
    return persistentRelationalCapabilityDto.getId();
  }
  
  public MessageDto deleteRelationalCapabilityById(Long id) {
    
    this.relationalCapabilityDao.deleteById(id);
    this.relationalCapabilityDao.flush();
    
    return new MessageDto("Successfully deleted item with id = " + id + " from table capability", false);
  }
  
}