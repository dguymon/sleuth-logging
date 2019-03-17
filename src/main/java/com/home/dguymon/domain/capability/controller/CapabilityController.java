package com.home.dguymon.domain.capability.controller;

import com.home.dguymon.domain.capability.domain.dto.CapabilityDto;
import com.home.dguymon.domain.capability.domain.dto.MessageDto;
import com.home.dguymon.domain.capability.domain.dto.NameResponseDto;

import com.home.dguymon.domain.capability.service.CapabilityService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Provides CRUD operations against capability table in DynamoDB.
 * 
 * @author Danazn
 *
 */
@Api(value = "Capability Management API with DynamoDB")
@RestController
public class CapabilityController {
  
  public static final String DYNAMO_DB_COMMS_ERROR_MESSAGE = "Error communicating with DynamoDB.";
  
  @Autowired
  private CapabilityService capabilityService;
  
  /**
   * Retrieves all capability items from capability table in DynamoDB.
   * 
   * @return JSON array of CapabilityDtos with HAL link to self.
   */
  @ApiOperation(value = "Get all capabilities from DynamoDB",
      response = List.class)
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
  @ApiOperation(value = "Get capability by name from DynamoDB",
      response = CapabilityDto.class)
  @GetMapping("/capabilities/{name}")
  public CapabilityDto getCapabilityByName(
      @ApiParam(value = "Primary key name of capability to retrieve",
                required = true) 
      @PathVariable String name) {
        
    return this.capabilityService.getCapabilityByName(name);
  }
  
  /**
   * Creates a new capability item in capability table in DynamoDB.
   * 
   * @param capabilityDto New capability item to add to DynamoDB.
   * @return A MessageDto indicating success or failure of capability creation.
   */
  @ApiOperation(value = "Create new capability in DynamoDB", 
      response = NameResponseDto.class)
  @PostMapping("/capabilities")
  public NameResponseDto createCapability(
      @ApiParam(value = "Capability information to create with",
                required = true)
      @RequestBody CapabilityDto capabilityDto) {
    
    String createdCapabilityName = this.capabilityService.createCapability(capabilityDto);
    
    return new NameResponseDto(createdCapabilityName);
  }
  
  /**
   * Updates an existing capability in capability table in DynamoDB.
   * 
   * @param capabilityDto Updated capability info.
   * @return MessageDto indicating update attempt status.
   */
  @ApiOperation(value = "Update capability by name in DynamoDB",
      response = NameResponseDto.class)
  @PutMapping("/capabilities/{name}")
  public NameResponseDto updateCapability(
      @ApiParam(value = "Capability info to update with", 
                required = true)
      @RequestBody CapabilityDto capabilityDto, 
      @ApiParam(value = "Primary key name to update", 
                required = true)
      @PathVariable String name) {
    
    String updatedCapabilityName = this.capabilityService.updateCapability(capabilityDto);
    
    return new NameResponseDto(updatedCapabilityName);
  }
  
  /**
   * Deletes a capability by name from capability table in DynamoDB.
   * 
   * @param name Primary key of item to delete.
   * @return MessageDto indicating deletion attempt status.
   */
  @ApiOperation(value = "Delete capability by name from DynamoDB", 
      response = MessageDto.class)
  @DeleteMapping("/capabilities/{name}")
  public MessageDto deleteCapability(
      @ApiParam(value = "Primary key name to delete", 
                required = true)
      @PathVariable String name) {
    
    MessageDto messageDto = this.capabilityService.deleteCapability(name);
    
    if (messageDto == null) {
      messageDto = new MessageDto();
      messageDto.setMessage(DYNAMO_DB_COMMS_ERROR_MESSAGE);
      messageDto.setError(true);
    }
    
    return messageDto;
  }
}