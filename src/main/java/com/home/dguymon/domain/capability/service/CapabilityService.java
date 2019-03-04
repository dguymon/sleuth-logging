package com.home.dguymon.domain.capability.service;

import com.home.dguymon.domain.capability.domain.dto.CapabilityDto;
import com.home.dguymon.domain.capability.domain.dto.MessageDto;

import java.util.ArrayList;

/**
 * Interface of CRUD service operations against capability table in DynamoDB.
 * 
 * @author Danazn
 *
 */
public interface CapabilityService {
  
  public ArrayList<CapabilityDto> getAllCapabilities();
  public CapabilityDto getCapabilityByName(String name);
  public MessageDto updateCapability(CapabilityDto capabilityDto);
  public MessageDto createCapability(CapabilityDto capabilityDto);
  public MessageDto deleteCapability(String name);
}