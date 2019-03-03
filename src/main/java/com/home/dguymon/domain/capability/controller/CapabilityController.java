package com.home.dguymon.domain.capability.controller;

import com.home.dguymon.domain.capability.domain.dto.CapabilityDto;
import com.home.dguymon.domain.capability.impl.CapabilityServiceImpl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
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

@Slf4j
@RequestMapping("/capability")
@RestController
public class CapabilityController {
  
  @Autowired
  CapabilityServiceImpl capabilityServiceImpl;
  
  @GetMapping("/all")
  @ResponseBody
  public ArrayList<CapabilityDto> getAllCapabilities() {
    
    ArrayList<CapabilityDto> capabilityDtos = this.capabilityServiceImpl.getAllCapabilities();
    
    //ResponseEntity<String> responseEntity = new ResponseEntity<String>("All capabilities", HttpStatus.OK);
    return capabilityDtos;
  }
  
  @GetMapping("/retrieve/{name}")
  @ResponseBody
  public CapabilityDto getCapabilityByName(@PathVariable String name) {
    
    CapabilityDto capabilityDto = this.capabilityServiceImpl.getCapabilityByName(name);
    
    return capabilityDto;
  }
  
  @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public String createCapability(@RequestBody CapabilityDto capabilityDto) {
    
    String responseString = this.capabilityServiceImpl.createCapability(capabilityDto);
    
    //ResponseEntity<String> responseEntity = new ResponseEntity<String>("Capability created", HttpStatus.OK);
    return responseString;
  }
  
  @PutMapping("/update")
  public ResponseEntity<String> updateCapability(@RequestBody CapabilityDto capabilityDto) {
    
    ResponseEntity<String> responseEntity = new ResponseEntity<String>("Capability updated", HttpStatus.OK);
    return responseEntity;
  }
  
  @DeleteMapping("/delete")
  public ResponseEntity<String> deleteCapability(@RequestBody CapabilityDto capabilityDto) {
    
    ResponseEntity<String> responseEntity = new ResponseEntity<String>("Capability deleted", HttpStatus.OK);
    return responseEntity;
  }
  
}