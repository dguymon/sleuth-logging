package com.home.dguymon.domain.capability.controller;

import com.home.dguymon.domain.capability.domain.dto.CapabilityDto;
import com.home.dguymon.domain.capability.domain.dto.MessageDto;

import com.home.dguymon.domain.capability.service.CapabilityService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Provides CRUD operations against capability table in DynamoDB.
 * 
 * @author Danazn
 *
 */
@RestController
public class CapabilityController {
  
  public static final String DYNAMO_DB_COMMS_ERROR_MESSAGE = "Error communicating with DynamoDB.";
  
  @Autowired
  CapabilityService capabilityService;
  
  /**
   * Retrieves all capability items from capability table in DynamoDB.
   * 
   * @return JSON array of CapabilityDtos with HAL link to self.
   */
  @GetMapping("/capabilities")
  public List<CapabilityDto> getAllCapabilities() {
    
    return this.capabilityService.getAllCapabilities();
  }
  
  /**
   * Retrieves a single capability by name from the capability table in DynamoDB.
   * 
   * @param name String primary key of the item to retrieve.
   * @return Capability with primary key that matches provided name.
   */
  @GetMapping("/capability/{name}")
  public CapabilityDto getCapabilityByName(@PathVariable String name) {
        
    return this.capabilityService.getCapabilityByName(name);
  }
  
  /**
   * Creates a new capability item in capability table in DynamoDB.
   * 
   * @param capabilityDto New capability item to add to DynamoDB.
   * @return A MessageDto indicating success or failure of capability creation.
   */
  @PostMapping("/capability")
  public MessageDto createCapability(@RequestBody CapabilityDto capabilityDto) {
    
    MessageDto messageDto = this.capabilityService.createCapability(capabilityDto);
    
    if (messageDto == null) {
      messageDto = new MessageDto();
      messageDto.setMessage(DYNAMO_DB_COMMS_ERROR_MESSAGE);
      messageDto.setError(true);
    }
    
    return messageDto;
  }
  
  /**
   * Updates an existing capability in capability table in DynamoDB.
   * 
   * @param capabilityDto Updated capability info.
   * @return MessageDto indicating update attempt status.
   */
  @PutMapping("/capability")
  public MessageDto updateCapability(@RequestBody CapabilityDto capabilityDto) {
    
    MessageDto messageDto = this.capabilityService.updateCapability(capabilityDto);
    
    if (messageDto == null) {
      messageDto = new MessageDto();
      messageDto.setMessage(DYNAMO_DB_COMMS_ERROR_MESSAGE);
      messageDto.setError(true);
    }
    
    return messageDto;
  }
  
  /**
   * Deletes a capability by name from capability table in DynamoDB.
   * 
   * @param name Primary key of item to delete.
   * @return MessageDto indicating deletion attempt status.
   */
  @DeleteMapping("/capability")
  public MessageDto deleteCapability(@RequestBody CapabilityDto capabilityDto) {
    
    MessageDto messageDto = this.capabilityService.deleteCapability(capabilityDto.getName());
    
    if (messageDto == null) {
      messageDto = new MessageDto();
      messageDto.setMessage(DYNAMO_DB_COMMS_ERROR_MESSAGE);
      messageDto.setError(true);
    }
    
    return messageDto;
  }
}