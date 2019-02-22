package com.home.dguymon.domain.subdomain.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/logs")
public class LoggingController {

  //private final Tracer tracer;
  
  private static final Logger logger = LogManager.getLogger(LoggingController.class);

  //LoggingController(Tracer tracer) {
  //  
  // this.tracer = tracer;
  //}
  
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
}