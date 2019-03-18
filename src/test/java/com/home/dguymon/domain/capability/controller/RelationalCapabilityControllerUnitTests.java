package com.home.dguymon.domain.capability.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.home.dguymon.domain.capability.domain.dto.IdResponseDto;
import com.home.dguymon.domain.capability.domain.dto.MessageDto;
import com.home.dguymon.domain.capability.domain.dto.PersistentRelationalCapabilityDto;
import com.home.dguymon.domain.capability.domain.dto.RelationalCapabilityDto;
import com.home.dguymon.domain.capability.service.RelationalCapabilityService;

import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebAppConfiguration
public class RelationalCapabilityControllerUnitTests {
  
  private static final Long PRIMARY_KEY = 1L;
  private static final Long CREATE_AND_DELETE_PRIMARY_KEY = 100L;
  
  @Mock
  RelationalCapabilityService relationalCapabilityService;
  
  @InjectMocks
  RelationalCapabilityController relationalCapabilityController = 
    new RelationalCapabilityController();
  
  private MockMvc mockMvc;
  
  private List<PersistentRelationalCapabilityDto> persistentRelationalCapabilityDtos = 
      new ArrayList<>();
  
  private PersistentRelationalCapabilityDto resumeDto = 
      new PersistentRelationalCapabilityDto(PRIMARY_KEY, "resume", "Resume capability");
  
  private Optional<PersistentRelationalCapabilityDto> optionalResumeDto = 
      Optional.of(resumeDto);
  
  private PersistentRelationalCapabilityDto uploadDto = 
      new PersistentRelationalCapabilityDto(2L, "upload", "Upload capability");
  
  private PersistentRelationalCapabilityDto downloadDto = 
      new PersistentRelationalCapabilityDto(3L, "download", "Download capability");
  
  private MessageDto messageDto = 
      new MessageDto("Capability successfully deleted from table capability", false);
  
  ObjectMapper mapper = new ObjectMapper();
  
  @Before
  public void setup() {
    
    MockitoAnnotations.initMocks(this);
    
    this.persistentRelationalCapabilityDtos.clear();
    this.persistentRelationalCapabilityDtos.add(resumeDto);
    this.persistentRelationalCapabilityDtos.add(uploadDto);
    this.persistentRelationalCapabilityDtos.add(downloadDto);
    
    this.mockMvc = 
        MockMvcBuilders.standaloneSetup(
            relationalCapabilityController
        ).alwaysExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
        .build();
  }
  
  @Test
  public void getAllRelationalCapabilities() throws Exception {
    
    when(relationalCapabilityService.getAllRelationalCapabilities())
        .thenReturn(persistentRelationalCapabilityDtos);
    
    MvcResult result = this.mockMvc.perform(get("/relational/capabilities")
        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
      .andExpect(status().isOk())
      .andReturn();
    
    assert(result.getResponse()
        .getContentAsString()).equals(
            mapper.writeValueAsString(persistentRelationalCapabilityDtos));
    
  }
  
  @Test
  public void getRelationalCapabilityById() throws Exception {
    
    when(relationalCapabilityService.getRelationalCapabilityById(PRIMARY_KEY))
        .thenReturn(optionalResumeDto);
    
    MvcResult result = 
        this.mockMvc.perform(get("/relational/capabilities/{id}", PRIMARY_KEY)
            .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
      .andExpect(status().isOk())
      .andReturn();
    
    if(optionalResumeDto.isPresent()) {
    
      assert(result.getResponse()
          .getContentAsString()).equals(mapper.writeValueAsString(optionalResumeDto.get()));
    }
  }
  
  @Test
  public void updateCapability() throws Exception {
    
    RelationalCapabilityDto toUpdateCapabilityDto = 
        new RelationalCapabilityDto(PRIMARY_KEY, "resume", "Updated details");
    
    PersistentRelationalCapabilityDto toUpdatePersistentCapabilityDto = 
        new PersistentRelationalCapabilityDto(PRIMARY_KEY, "resume", "Updated details");
    
    IdResponseDto idResponseDto = 
        new IdResponseDto(PRIMARY_KEY);
    
    when(relationalCapabilityService.updateOrCreateRelationalCapability(
        toUpdatePersistentCapabilityDto))
            .thenReturn(PRIMARY_KEY);
    
    MvcResult result = 
        this.mockMvc.perform(put("/relational/capabilities/{id}", PRIMARY_KEY)
            .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
            .content(mapper.writeValueAsString(toUpdateCapabilityDto)))
      .andExpect(status().isOk())
      .andReturn();
    
    assert(result.getResponse()
        .getContentAsString()).equals(mapper.writeValueAsString(idResponseDto));
  }
  
  @Test
  public void createCapability() throws Exception {
    
    RelationalCapabilityDto toCreateCapabilityDto = 
        new RelationalCapabilityDto(null, "travel", "Travel capabilities");
    
    PersistentRelationalCapabilityDto toCreatePersistentCapabilityDto = 
        new PersistentRelationalCapabilityDto(null, 
            toCreateCapabilityDto.getName(),
            toCreateCapabilityDto.getDescription());
    
    IdResponseDto idResponseDto = new IdResponseDto(CREATE_AND_DELETE_PRIMARY_KEY);
    
    when(relationalCapabilityService
        .updateOrCreateRelationalCapability(
            toCreatePersistentCapabilityDto))
      .thenReturn(CREATE_AND_DELETE_PRIMARY_KEY);
    
    MvcResult result = 
        this.mockMvc.perform(post("/relational/capabilities")
            .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
            .content(mapper.writeValueAsString(toCreatePersistentCapabilityDto)))
      .andExpect(status().isOk())
      .andReturn();
    
    assert(result.getResponse()
        .getContentAsString()).equals(
            mapper.writeValueAsString(idResponseDto)); 
  }
  
  @Test
  public void deleteCapabilityById() throws Exception {
    
    when(relationalCapabilityService
        .deleteRelationalCapabilityById(CREATE_AND_DELETE_PRIMARY_KEY))
      .thenReturn(messageDto);
    
    MvcResult result = 
        this.mockMvc.perform(delete("/relational/capabilities/{id}", 
           CREATE_AND_DELETE_PRIMARY_KEY)
              .contentType(MediaType.APPLICATION_JSON_UTF8))
      .andExpect(status().isOk())
      .andReturn();
    
    assert(result.getResponse()
        .getContentAsString()).equals(
            mapper.writeValueAsString(messageDto));
  }
  
}