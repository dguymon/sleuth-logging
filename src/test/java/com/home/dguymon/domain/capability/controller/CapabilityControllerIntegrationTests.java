package com.home.dguymon.domain.capability.controller;

import com.home.dguymon.domain.capability.domain.dto.CapabilityDto;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
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
  
  public static final String PRIMARY_KEY = "resume";
  
  List<CapabilityDto> capabilityDtos = new ArrayList<>();
  List<CapabilityDto> referenceCapabilitiesDtos = new ArrayList<>();
  
  CapabilityDto resumeDto = new CapabilityDto(PRIMARY_KEY,
      "Update resume parsing capabilities");
  
  CapabilityDto travelDto = new CapabilityDto("travel",
      "Travel capabilities");
  
  CapabilityDto uploadDto = new CapabilityDto("upload", 
      "Update upload capability");
  
  @Before
  public void setup() {
    this.capabilityDtos.clear();
    
    this.capabilityDtos.add(resumeDto);
    this.capabilityDtos.add(travelDto);
    this.capabilityDtos.add(uploadDto);
  }
  
  /**
   * Runs an integration test against 
   * the /${server.servlet.context}/${custom.api.version}/capabilities URI endpoint.
   * 
   * Retrieves back all the capabilities from the capability DynamoDB table.
   */
  @Test
  public void getCapabilities() {
    
    ResponseEntity<List<CapabilityDto>> responseCapabilityDtos = this.testRestTemplate.exchange("/capabilities", 
        HttpMethod.GET,
        null,
        new ParameterizedTypeReference<List<CapabilityDto>>() {
        });
    
    this.referenceCapabilitiesDtos = responseCapabilityDtos.getBody();
    
    assertThat(this.referenceCapabilitiesDtos).isNotEmpty();
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
    capabilityDto.setName(PRIMARY_KEY);
    capabilityDto.setDescription("Update resume parsing capabilities");
    
    CapabilityDto responseCapabilityDto = this.testRestTemplate
        .getForObject("/capabilities/" + capabilityDto.getName(), CapabilityDto.class);
      
    
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
    
    CapabilityDto originalCapabilityDto = this.testRestTemplate.getForObject(
        "/capabilities/" + PRIMARY_KEY, CapabilityDto.class);
    
    CapabilityDto toUpdateCapabilityDto = new CapabilityDto();
    toUpdateCapabilityDto.setName(PRIMARY_KEY);
    toUpdateCapabilityDto.setDescription("Newly updated resume parsing capabilities");
    
    this.testRestTemplate.put("/capabilities/" + PRIMARY_KEY, 
        toUpdateCapabilityDto);
    
    CapabilityDto updatedCapabilityDto = this.testRestTemplate.getForObject(
        "/capabilities/" + PRIMARY_KEY, CapabilityDto.class);
    
    this.testRestTemplate.put("/capabilities/" + PRIMARY_KEY, originalCapabilityDto);
    
    assertThat(updatedCapabilityDto).isEqualTo(toUpdateCapabilityDto);
  }
}
