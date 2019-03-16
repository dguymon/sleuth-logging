package com.home.dguymon.domain.capability.controller;

import com.home.dguymon.domain.capability.domain.dto.IdResponseDto;
import com.home.dguymon.domain.capability.domain.dto.MessageDto;
import com.home.dguymon.domain.capability.domain.dto.RelationalCapabilityDto;
import com.home.dguymon.domain.capability.service.RelationalCapabilityService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class RelationalCapabilityController {
  
  
  @Autowired
  RelationalCapabilityService relationalCapabilityService;
  
  @GetMapping("/relational/capabilities")
  public List<RelationalCapabilityDto> getAllRelationalCapabilities() {
    
    return this.relationalCapabilityService.getAllRelationalCapabilities();
  }
  
  @GetMapping("/relational/capabilities/{id}")
  public RelationalCapabilityDto getRelationalCapabilityById(@PathVariable Long id) {
    return this.relationalCapabilityService.getRelationalCapabilityById(id).get();
  }
  
  @PutMapping("/relational/capabilities/{id}")
  public Long updateRelationalCapability(@PathVariable Long id,
      @RequestBody RelationalCapabilityDto relationalCapabilityDto) {
    return this.relationalCapabilityService.updateRelationalCapability(relationalCapabilityDto);
  }
  
  @PostMapping("/relational/capabilities")
  public Long createRelationalCapability(@RequestBody RelationalCapabilityDto relationalCapabilityDto) {
    return this.relationalCapabilityService.createRelationalCapability(relationalCapabilityDto);
  }
  
  @DeleteMapping("/relational/capabilities/{id}")
  public MessageDto deleteRelationalCapabilityService(@PathVariable Long id) {
    return this.relationalCapabilityService.deleteRelationalCapabilityById(id);
  }
}