package com.home.dguymon.capability.controller;

import com.home.dguymon.domain.capability.domain.dto.CapabilityDto;

import java.util.ArrayList;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Spins up 
 * @author Danazn
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CapabilityControllerIntegrationTests {
  
  @Autowired
  private TestRestTemplate testRestTemplate;
  
  /**
   * Runs an integration test against 
   * the /${server.servlet.context}/${custom.api.version}/capabilities URI endpoint.
   * 
   * Retrieves back all the capabilities from the capability DynamoDB table.
   */
  @Test
  public void getCapabilities() {
    List<CapabilityDto> capabilityDtos = new ArrayList<>();
    
    CapabilityDto resumeDto = new CapabilityDto("resume",
        "Update resume parsing capabilities");
    
    CapabilityDto travelDto = new CapabilityDto("travel",
        "Travel capabilities");
    
    CapabilityDto uploadDto = new CapabilityDto("upload", 
        "Update upload capability");
    
    capabilityDtos.add(resumeDto);
    capabilityDtos.add(travelDto);
    capabilityDtos.add(uploadDto);
    
    ResponseEntity<List<CapabilityDto>> responseCapabilityDtos = this.testRestTemplate.exchange("/v1/capabilities", 
        HttpMethod.GET,
        null,
        new ParameterizedTypeReference<List<CapabilityDto>>() {
        });
    
    assertThat(responseCapabilityDtos.getBody()).isEqualTo(capabilityDtos);
  }
  
  /**
   * Integration test against
   * the /${server.servlet.contextPath}/${custom.api.version}/capabilities/resume endpoint.
   * 
   * Note: ${server.servelt.contextPath} already gets picked up at Tomcat server startup; no
   * need to reference it in the URI path
   * 
   * Verifies that the returned CapabilityDto matches the reference one.
   */
  @Test
  public void getCapabilityByName() {
    
    CapabilityDto capabilityDto = new CapabilityDto();
    capabilityDto.setName("resume");
    capabilityDto.setDescription("Update resume parsing capabilities");
    
    CapabilityDto responseCapabilityDto = this.testRestTemplate
        .getForObject("/v1/capabilities/" + capabilityDto.getName(), CapabilityDto.class);
      
    
    assertThat(responseCapabilityDto).isEqualTo(capabilityDto);
  }
  
  /**
   * Updates a capability in the capability table in DynamoDB.
   * 
   * Asserts that the update persisted to the table.
   * 
   * Resets the mutate capability back to its original state
   */
  @Test
  public void updateCapability() {
    
    final String primaryKey = "resume";
    
    CapabilityDto originalCapabilityDto = this.testRestTemplate.getForObject(
        "/v1/capabilities/" + primaryKey, CapabilityDto.class);
    
    CapabilityDto toUpdateCapabilityDto = new CapabilityDto();
    toUpdateCapabilityDto.setName("resume");
    toUpdateCapabilityDto.setDescription("Newly updated resume parsing capabilities");
    
    this.testRestTemplate.put("/v1/capabilities/" + primaryKey, 
        toUpdateCapabilityDto);
    
    CapabilityDto updatedCapabilityDto = this.testRestTemplate.getForObject(
        "/v1/capabilities/" + primaryKey, CapabilityDto.class);
    
    this.testRestTemplate.put("/v1/capabilities/" + primaryKey, originalCapabilityDto);
    
    assertThat(updatedCapabilityDto).isEqualTo(toUpdateCapabilityDto);
  }
}
