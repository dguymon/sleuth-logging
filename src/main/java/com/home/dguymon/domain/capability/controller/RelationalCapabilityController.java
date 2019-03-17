package com.home.dguymon.domain.capability.controller;

import com.home.dguymon.domain.capability.domain.dto.IdResponseDto;
import com.home.dguymon.domain.capability.domain.dto.MessageDto;
import com.home.dguymon.domain.capability.domain.dto.PersistentRelationalCapabilityDto;
import com.home.dguymon.domain.capability.domain.dto.RelationalCapabilityDto;
import com.home.dguymon.domain.capability.service.RelationalCapabilityService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

/**
 * CRUD controller for capability table in postgres 
 * database in PostGreSQL RDS
 * 
 * @author Danazn
 *
 */
@Slf4j
@Api(value = "Capability management API with PostGreSQL RDS")
@RestController
public class RelationalCapabilityController {
  
  @Autowired
  RelationalCapabilityService relationalCapabilityService;
  
  /**
   * Returns a List<PersistenRelationalCapabilityDto> from capability table 
   * in postgres database in PostGreSQL RDS.
   * 
   * @return
   */
  @ApiOperation(value = "Get all capabilities from PostGreSQL RDS", 
                response = PersistentRelationalCapabilityDto.class,
                responseContainer = "List")
  @GetMapping("/relational/capabilities")
  public List<PersistentRelationalCapabilityDto> getAllRelationalCapabilities() {
    
    return this.relationalCapabilityService.getAllRelationalCapabilities();
  }
  
  /**
   * Get a capability by name from capability table in 
   * postgres database in PostGreSQL RDS.
   * 
   * @param id Required primary key to lookup a capability with.
   * @return PersistentRelationalCapabilityDto The retrieved capability.
   */
  @ApiOperation(value = "Get a capability by name from PostGreSQL RDS", 
                response = PersistentRelationalCapabilityDto.class)
  @GetMapping("/relational/capabilities/{id}")
  public PersistentRelationalCapabilityDto getRelationalCapabilityById(
      @ApiParam(value = "Primary key id to retrieve",
                required = true) 
      @PathVariable Long id) {
    
    Optional<PersistentRelationalCapabilityDto> responseOptional = 
        this.relationalCapabilityService.getRelationalCapabilityById(id);
    
    if(responseOptional.isPresent()) {
    
      return responseOptional.get();
    } else {
      return new PersistentRelationalCapabilityDto();
    }
  }
  
  /**
   * Updates an existing capability in the capability table in 
   * postgres database in PostGreSQL RDS.  Provides defensive semantics 
   * against SQL injection.
   * 
   * @param id Primary key of the capability to update.
   * @param relationalCapabilityDto Incoming capability info.
   * @return IdResponseDto containing primary key of updated capability.
   */
  @ApiOperation(value = "Update a capability by id in PostGreSQL RDS", 
                response = IdResponseDto.class)
  @PutMapping("/relational/capabilities/{id}")
  public IdResponseDto updateRelationalCapability(
      @ApiParam(value = "Primary key id to update", 
                required = true)
      @PathVariable Long id,
      @ApiParam(value = "Capability info to update with", 
                required = true)
      @RequestBody RelationalCapabilityDto relationalCapabilityDto) {
    
    PersistentRelationalCapabilityDto persistentRelationalCapabilityDto = 
        new PersistentRelationalCapabilityDto(
            relationalCapabilityDto.getId(),
            relationalCapabilityDto.getName(),
            relationalCapabilityDto.getDescription());
    
    return new IdResponseDto(
        this.relationalCapabilityService.updateOrCreateRelationalCapability(
            persistentRelationalCapabilityDto));
  }
  
  /**
   * Creates a new capability in the capability table in 
   * postgres database in PostGreSQL RDS.  Provides defensive 
   * semantics against SQL injection.
   * 
   * @param relationalCapabilityDto Capability info to create with.
   * @return IdResponseDto containing primary key of created capability.
   */
  @ApiOperation(value = "Create new capability in PostGreSQL RDS", 
                response = IdResponseDto.class)
  @PostMapping("/relational/capabilities")
  public IdResponseDto createRelationalCapability(
      @ApiParam(value = "Capability info to create with",
                required = true)
      @RequestBody RelationalCapabilityDto relationalCapabilityDto) {
    
    PersistentRelationalCapabilityDto persistentRelationalCapabilityDto = 
        new PersistentRelationalCapabilityDto(
            null,
            relationalCapabilityDto.getName(),
            relationalCapabilityDto.getDescription());
    
    return new IdResponseDto(this.relationalCapabilityService
        .updateOrCreateRelationalCapability(persistentRelationalCapabilityDto));
  }
  
  /**
   * Delete an exsiting capability from capability table in 
   * postgres database in PostGreSQL RDS.
   * 
   * @param id Primary key of capability to delete.
   * @return MessageDto indicating success of deletion.
   */
  @ApiOperation(value = "Delete existing capability from PostGreSQL RDS", 
                response = MessageDto.class)
  @DeleteMapping("/relational/capabilities/{id}")
  public MessageDto deleteRelationalCapabilityService(
      @ApiParam(value = "Primary key id to delete",
                required = true)
      @PathVariable Long id) {
    
    return this.relationalCapabilityService.deleteRelationalCapabilityById(id);
  }
}