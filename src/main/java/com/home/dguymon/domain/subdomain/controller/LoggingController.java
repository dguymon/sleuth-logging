package com.home.dguymon.domain.subdomain.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
@RequestMapping("/logs")
public class LoggingController {
  
  @Autowired
  RestTemplate restTemplate;
  
  private static final Logger logger = LogManager.getLogger(LoggingController.class);
  
  /**
   * Generates sample logs.
   * 
   * @return String
   */
  @RequestMapping(value = "/sample", method = RequestMethod.GET)
  public ResponseEntity<String> generateSampleLogs() {
    
    //Span span = tracer.newTrace().name("generateSampleLogs").start();
    
    logger.debug("Sample DEBUG log");
    logger.trace("Sample TRACE log");
    logger.info("Sample INFO log");
    logger.warn("Sample WARN log");
    logger.error("Sample ERROR log");

    return new ResponseEntity<>("Hello", HttpStatus.OK);
  }
  
  @RequestMapping(value = "/propagate", method = RequestMethod.GET)
  public ResponseEntity<String> propagateSleuth() {
    
    logger.debug("Propagate sleuth");
        
    String responseString = 
        restTemplate.getForObject("http://localhost:8089/downstream/logs/sample", String.class);
    
    return new ResponseEntity<String>(responseString, HttpStatus.OK);
  }
}