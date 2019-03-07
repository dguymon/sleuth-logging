package com.home.dguymon.domain.subdomain.controller;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.client.RestTemplate;

@Slf4j
@RestController
@RequestMapping("/logs")
public class LoggingController {
  
  @Autowired
  RestTemplate restTemplate;
  
  /**
   * Generates sample logs.
   * 
   * @return String
   */
  @GetMapping("/sample")
  public String generateSampleLogs() {
    
    log.debug("Sample DEBUG log");
    log.trace("Sample TRACE log");
    log.info("Sample INFO log");
    log.warn("Sample WARN log");
    log.error("Sample ERROR log");

    return "Hello";
  }
  
  @GetMapping("/propagate")
  public String propagateSleuth() {
    
    log.debug("Propagate sleuth");
        
    String responseString = 
        restTemplate.getForObject("http://localhost:8089/downstream/logs/sample", String.class);
    
    return responseString;
  }
}