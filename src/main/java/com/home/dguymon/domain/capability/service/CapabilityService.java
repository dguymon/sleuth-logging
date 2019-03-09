package com.home.dguymon.domain.capability.service;

import com.home.dguymon.domain.capability.domain.dao.CapabilityDao;
import com.home.dguymon.domain.capability.domain.dto.CapabilityDto;
import com.home.dguymon.domain.capability.domain.dto.MessageDto;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

/**
 * Implementation of CRUD service operations against capability table in DynamoDB.
 * 
 * @author Danazn
 *
 */
@Service
public class CapabilityService {
  
  @Autowired
  CapabilityDao capabilityDao;
  
  /**
   * Retrieves all capability items from capability table in DynamoDB
   * 
   * @return ArrayList of CapabilityDto representing all capabilities.
   */
  public List<CapabilityDto> getAllCapabilities() {
    
    return this.capabilityDao.getAllCapabilities();
  }
  
  /**
   * Retrieves a capability whose name matches the provided.
   * 
   * @return CapabilityDto representing the retrieved capability item.
   */
  public CapabilityDto getCapabilityByName(String name) {

    return this.capabilityDao.getCapabilityByName(name);
  }
  
  /**
   * Updates an existing capability in capability table of DynamoDB.
   * 
   * @return MessageDto indicating update attempt status.
   */
  public String updateCapability(CapabilityDto capabilityDto) {
        
    return this.capabilityDao.updateCapability(capabilityDto);
  }
  
  /**
   * Creates a new capability in capability table in DynamoDB.
   * 
   * @return MessageDto indicating creation attempt status.
   */
  public String createCapability(CapabilityDto capabilityDto) {
        
    return this.capabilityDao.createCapability(capabilityDto);
  }
  
  /**
   * Deletes an existing capability in capability table in DynamoDB.
   * 
   * @return MessageDto indicating deletion attempt status.
   */
  public MessageDto deleteCapability(String name) {
        
    return this.capabilityDao.deleteCapability(name);
  }
}