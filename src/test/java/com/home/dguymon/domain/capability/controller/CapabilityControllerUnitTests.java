package com.home.dguymon.domain.capability.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.home.dguymon.domain.capability.domain.dto.CapabilityDto;
import com.home.dguymon.domain.capability.domain.dto.NameResponseDto;
import com.home.dguymon.domain.capability.domain.dto.MessageDto;
import com.home.dguymon.domain.capability.service.CapabilityService;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Classical unit tests for the CapabilityController.
 * 
 * @author Danazn
 *
 */
@RunWith(SpringRunner.class)
@WebAppConfiguration
public class CapabilityControllerUnitTests {
  
  private static final String PRIMARY_KEY = "resume";
  
  @Mock
  CapabilityService capabilityService;
  
  @InjectMocks
  CapabilityController capabilityController = new CapabilityController();
  
  private MockMvc mockMvc;
    
  private List<CapabilityDto> capabilityDtos = 
      new ArrayList<>();
  
  private CapabilityDto resumeDto = 
      new CapabilityDto(PRIMARY_KEY, "Resume capability");
  
  private CapabilityDto uploadDto = 
      new CapabilityDto("upload", "Upload capability");
  
  private CapabilityDto downloadDto = 
      new CapabilityDto("download", "Download capability");
  
  private MessageDto messageDto = 
      new MessageDto("Capability successfully deleted from table capability", false);
  
  ObjectMapper mapper = new ObjectMapper();
  
  /**
   * Setup run before each test.
   */
  @Before
  public void setup() {    
    MockitoAnnotations.initMocks(this);
    
    this.capabilityDtos.clear();
    this.capabilityDtos.add(resumeDto);
    this.capabilityDtos.add(uploadDto);
    this.capabilityDtos.add(downloadDto);
    
    this.mockMvc = 
        MockMvcBuilders.standaloneSetup(
            capabilityController
        ).alwaysExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
        .build();
  }
  
  /**
   * Successful unit test against CapabilityController.getAllCapabilities().
   * 
   * @throws Exception
   */
  @Test
  public void getAllCapabilities() throws Exception {
        
    when(capabilityService.getAllCapabilities()).thenReturn(capabilityDtos);
    
    MvcResult result = this.mockMvc.perform(get("/capabilities")
        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
      .andExpect(status().isOk())
      .andReturn();
    
    assert(result.getResponse()
        .getContentAsString()).equals(mapper.writeValueAsString(capabilityDtos));
  }
  
  /**
   * Successful unit test against CapabilityController.getCapabilityByName().
   * 
   * @throws Exception
   */
  @Test
  public void getCapabilityByName() throws Exception {
    
    when(capabilityService.getCapabilityByName(PRIMARY_KEY)).thenReturn(resumeDto);
    
    MvcResult result = 
        this.mockMvc.perform(get("/capabilities/{name}", PRIMARY_KEY)
            .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
      .andExpect(status().isOk())
      .andReturn();
    
    assert(result.getResponse()
        .getContentAsString()).equals(mapper.writeValueAsString(resumeDto));
  }
  
  /**
   * Successful unit tests against CapabilityController.updateCapability()
   * 
   * @throws Exception
   */
  @Test
  public void updateCapability() throws Exception {
    
    CapabilityDto toUpdateCapabilityDto = new CapabilityDto(PRIMARY_KEY, "Updated details");
    NameResponseDto nameResponseDto = new NameResponseDto(PRIMARY_KEY);
    
    when(capabilityService.updateCapability(toUpdateCapabilityDto)).thenReturn(PRIMARY_KEY);
    
    MvcResult result = 
        this.mockMvc.perform(put("/capabilities/{name}", PRIMARY_KEY)
            .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
            .content(mapper.writeValueAsString(toUpdateCapabilityDto)))
     .andExpect(status().isOk())
     .andReturn();
    
    assert(result.getResponse()
        .getContentAsString()).equals(mapper.writeValueAsString(nameResponseDto));
  }
  
  /**
   * Successful unit test against CapabilityController.createCapability().
   * 
   * @throws Exception
   */
  @Test
  public void createCapability() throws Exception {
    
    String createPrimaryKey = "travel";
    
    CapabilityDto toCreateCapabilityDto = new CapabilityDto(createPrimaryKey, "Travel capabilities");
    NameResponseDto nameResponseDto = new NameResponseDto(createPrimaryKey);
    
    when(capabilityService.createCapability(toCreateCapabilityDto)).thenReturn(createPrimaryKey);
    
    MvcResult result = 
        this.mockMvc.perform(post("/capabilities")
            .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
            .content(mapper.writeValueAsString(toCreateCapabilityDto)))
      .andExpect(status().isOk())
      .andReturn();
    
    assert(result.getResponse()
        .getContentAsString()).equals(mapper.writeValueAsString(nameResponseDto));
  }
  
  /**
   * Successful unit test against CapabilityController.deleteCapabilityByName()
   * 
   * @throws Exception
   */
  @Test
  public void deleteCapabilityByName() throws Exception {
    
    String deletePrimaryKey = "travel";
    
    when(capabilityService.deleteCapability(deletePrimaryKey)).thenReturn(messageDto);
    
    MvcResult result = 
        this.mockMvc.perform(delete("/capabilities/{name}", deletePrimaryKey)
            .contentType(MediaType.APPLICATION_JSON_UTF8))
      .andExpect(status().isOk())
      .andReturn();

    assert(result.getResponse()
        .getContentAsString()).equals(mapper.writeValueAsString(messageDto));
  }
}
