package com.home.dguymon.domain.capability.impl;

import com.home.dguymon.domain.capability.domain.dao.CapabilityDao;
import com.home.dguymon.domain.capability.domain.dto.CapabilityDto;
import com.home.dguymon.domain.capability.domain.dto.MessageDto;
import com.home.dguymon.domain.capability.service.CapabilityService;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Implementation of CRUD service operations against capability table in DynamoDB.
 * 
 * @author Danazn
 *
 */
@Component
public class CapabilityServiceImpl implements CapabilityService {
  
  @Autowired
  CapabilityDao capabilityDao;
  
  /**
   * Retrieves all capability items from capability table in DynamoDB
   * 
   * @return ArrayList of CapabilityDto representing all capabilities.
   */
  public ArrayList<CapabilityDto> getAllCapabilities() {
    ArrayList<CapabilityDto> capabilities = new ArrayList<CapabilityDto>();
    capabilities = this.capabilityDao.getAllCapabilities();
    
    return capabilities;
  }
  
  /**
   * Retrieves a capability whose name matches the provided.
   * 
   * @return CapabilityDto representing the retrieved capability item.
   */
  public CapabilityDto getCapabilityByName(String name) {
    CapabilityDto capabilityDto = new CapabilityDto();
    
    capabilityDto = this.capabilityDao.getCapabilityByName(name);
    return capabilityDto;
  }
  
  /**
   * Updates an existing capability in capability table of DynamoDB.
   * 
   * @return MessageDto indicating update attempt status.
   */
  public MessageDto updateCapability(CapabilityDto capabilityDto) {
    
    MessageDto messageDto = this.capabilityDao.updateCapability(capabilityDto);
    
    return messageDto;
  }
  
  /**
   * Creates a new capability in capability table in DynamoDB.
   * 
   * @return MessageDto indicating creation attempt status.
   */
  public MessageDto createCapability(CapabilityDto capabilityDto) {
    
    MessageDto messageDto = this.capabilityDao.createCapability(capabilityDto);
    
    return messageDto;
  }
  
  /**
   * Deletes an existing capability in capability table in DynamoDB.
   * 
   * @return MessageDto indicating deletion attempt status.
   */
  public MessageDto deleteCapability(String name) {
    
    MessageDto messageDto = this.capabilityDao.deleteCapability(name);
    
    return messageDto;
  }
}