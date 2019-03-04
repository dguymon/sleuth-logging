package com.home.dguymon.domain.capability.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import com.home.dguymon.domain.capability.domain.dto.CapabilityDto;
import com.home.dguymon.domain.capability.domain.dto.MessageDto;
import com.home.dguymon.domain.capability.impl.CapabilityServiceImpl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

/**
 * Provides CRUD operations against capability table in DynamoDB.
 * 
 * @author Danazn
 *
 */
@Slf4j
@RequestMapping("/capability")
@RestController
public class CapabilityController {
  
  @Autowired
  CapabilityServiceImpl capabilityServiceImpl;
  
  /**
   * Retrieves all capability items from capability table in DynamoDB.
   * 
   * @return JSON array of CapabilityDtos with HAL link to self.
   */
  @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @ResponseBody
  public HttpEntity<ArrayList<CapabilityDto>> getAllCapabilities() {
    
    ArrayList<CapabilityDto> capabilityDtos = this.capabilityServiceImpl.getAllCapabilities();
    
    for (CapabilityDto capabilityDto : capabilityDtos) {
      capabilityDto.add(linkTo(methodOn(CapabilityController.class).getCapabilityByName(capabilityDto.getName()))
          .withSelfRel());
    }
    
    return new ResponseEntity<>(capabilityDtos, HttpStatus.OK);
  }
  
  /**
   * Retrieves a single capability by name from the capability table in DynamoDB.
   * 
   * @param name String primary key of the item to retrieve.
   * @return Capability with primary key that matches provided name.
   */
  @GetMapping(value = "/retrieve/{name}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @ResponseBody
  public HttpEntity<CapabilityDto> getCapabilityByName(@PathVariable String name) {
    
    CapabilityDto capabilityDto = this.capabilityServiceImpl.getCapabilityByName(name);
    
    capabilityDto.add(linkTo(methodOn(CapabilityController.class).getCapabilityByName(name))
        .withSelfRel());
    
    return new ResponseEntity<>(capabilityDto, HttpStatus.OK);
  }
  
  /**
   * Creates a new capability item in capability table in DynamoDB.
   * 
   * @param capabilityDto New capability item to add to DynamoDB.
   * @return A MessageDto indicating success or failure of capability creation.
   */
  @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @ResponseBody
  public HttpEntity<MessageDto> createCapability(@RequestBody CapabilityDto capabilityDto) {
    
    MessageDto messageDto = this.capabilityServiceImpl.createCapability(capabilityDto);
    
    if (messageDto == null) {
      messageDto = new MessageDto();
      messageDto.setMessage("Error communicating with DynamoDB.");
      messageDto.setError(true);
    }
    
    messageDto.add(linkTo(methodOn(CapabilityController.class).createCapability(capabilityDto))
        .withSelfRel());
    
    if (messageDto.getError()) {
      return new ResponseEntity<>(messageDto, HttpStatus.INTERNAL_SERVER_ERROR);
    } else {
      return new ResponseEntity<>(messageDto, HttpStatus.OK);
    }
  }
  
  /**
   * Updates an existing capability in capability table in DynamoDB.
   * 
   * @param capabilityDto Updated capability info.
   * @return MessageDto indicating update attempt status.
   */
  @PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @ResponseBody
  public HttpEntity<MessageDto> updateCapability(@RequestBody CapabilityDto capabilityDto) {
    
    MessageDto messageDto = this.capabilityServiceImpl.updateCapability(capabilityDto);
    
    if (messageDto == null) {
      messageDto = new MessageDto();
      messageDto.setMessage("Error communicating with DynamoDB.");
      messageDto.setError(true);
    }
    
    messageDto.add(linkTo(methodOn(CapabilityController.class).updateCapability(capabilityDto))
        .withSelfRel());
    
    if (messageDto.getError()) {
      return new ResponseEntity<>(messageDto, HttpStatus.INTERNAL_SERVER_ERROR);
    } else {
      return new ResponseEntity<>(messageDto, HttpStatus.OK);
    }
  }
  
  /**
   * Deletes a capability by name from capability table in DynamoDB.
   * 
   * @param name Primary key of item to delete.
   * @return MessageDto indicating deletion attempt status.
   */
  @DeleteMapping(value = "/delete/{name}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @ResponseBody
  public HttpEntity<MessageDto> deleteCapability(@PathVariable String name) {
    
    MessageDto messageDto = this.capabilityServiceImpl.deleteCapability(name);
    
    if (messageDto == null) {
      messageDto = new MessageDto();
      messageDto.setMessage("Error communicating with DynamoDB.");
      messageDto.setError(true);
    }
    
    messageDto.add(linkTo(methodOn(CapabilityController.class).deleteCapability(name))
        .withSelfRel());
    
    if (messageDto.getError()) {
      return new ResponseEntity<>(messageDto, HttpStatus.INTERNAL_SERVER_ERROR);
    } else {
      return new ResponseEntity<>(messageDto, HttpStatus.OK);
    }
  }
}