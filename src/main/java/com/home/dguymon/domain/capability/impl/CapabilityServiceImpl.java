package com.home.dguymon.domain.capability.impl;

import com.home.dguymon.domain.capability.domain.dao.CapabilityDao;
import com.home.dguymon.domain.capability.domain.dto.CapabilityDto;
import com.home.dguymon.domain.capability.service.CapabilityService;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CapabilityServiceImpl implements CapabilityService {
  
  @Autowired
  CapabilityDao capabilityDao;
  
  public ArrayList<CapabilityDto> getAllCapabilities() {
    ArrayList<CapabilityDto> capabilities = new ArrayList<CapabilityDto>();
    capabilities = this.capabilityDao.getAllCapabilities();
    
    return capabilities;
  }
  
  public CapabilityDto getCapabilityByName(String name) {
    CapabilityDto capabilityDto = new CapabilityDto();
    
    capabilityDto = this.capabilityDao.getCapabilityByName(name);
    return capabilityDto;
  }
  
  public String updateCapability(CapabilityDto capabilityDto) {
    return "success";
  }
  
  public String createCapability(CapabilityDto capabilityDto) {
    
    String responseString = this.capabilityDao.createCapability(capabilityDto);
    
    return responseString;
  }
  
  public String deleteCapability(CapabilityDto capabilityDto) {
    return "success";
  }
}