package com.home.dguymon.domain.capability.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.home.dguymon.domain.capability.domain.dao.RelationalCapabilityDao;
import com.home.dguymon.domain.capability.domain.dto.MessageDto;
import com.home.dguymon.domain.capability.domain.dto.RelationalCapabilityDto;

@Service
public class RelationalCapabilityService {
  
  @Autowired
  RelationalCapabilityDao relationalCapabilityDao;
  
  public List<RelationalCapabilityDto> getAllRelationalCapabilities() {
    
    return this.relationalCapabilityDao.findAll();
  }
  
  public Optional<RelationalCapabilityDto> getRelationalCapabilityById(Long id) {
    
    return this.relationalCapabilityDao.findById(id);
  }
  
  public Long updateRelationalCapability(RelationalCapabilityDto relationalCapabilityDto) {
    this.relationalCapabilityDao.saveAndFlush(relationalCapabilityDto);
    
    return relationalCapabilityDto.getId();
  }
  
  public Long createRelationalCapability(RelationalCapabilityDto relationalCapabilityDto) {
    this.relationalCapabilityDao.saveAndFlush(relationalCapabilityDto);
    
    return relationalCapabilityDto.getId();
  }
  
  public MessageDto deleteRelationalCapabilityById(Long id) {
    
    this.relationalCapabilityDao.deleteById(id);
    this.relationalCapabilityDao.flush();
    
    return new MessageDto("Successfully deleted item with id = " + id + "from table capability", false);
  }
  
}