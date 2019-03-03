package com.home.dguymon.domain.capability.service;

import com.home.dguymon.domain.capability.domain.dto.CapabilityDto;

import java.util.ArrayList;

public interface CapabilityService {
  
  public ArrayList<CapabilityDto> getAllCapabilities();
  public CapabilityDto getCapabilityByName(String name);
  public String updateCapability(CapabilityDto capabilityDto);
  public String createCapability(CapabilityDto capabilityDto);
  public String deleteCapability(CapabilityDto capabilityDto);
}