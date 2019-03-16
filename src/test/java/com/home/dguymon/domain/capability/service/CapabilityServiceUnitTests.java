package com.home.dguymon.domain.capability.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.home.dguymon.domain.capability.domain.dao.CapabilityDao;
import com.home.dguymon.domain.capability.domain.dto.CapabilityDto;
import com.home.dguymon.domain.capability.domain.dto.MessageDto;

import static org.mockito.Mockito.*;

/**
 * Classical unit tests for CapabilityService.
 * 
 * @author Danazn
 *
 */
@RunWith(SpringRunner.class)
public class CapabilityServiceUnitTests {
  
  private static final String PRIMARY_KEY = "resume";
  
  @Mock
  CapabilityDao capabilityDao;
  
  @InjectMocks
  CapabilityService capabilityService = new CapabilityService();
  
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
   * Setup run before each unit test.
   * 
   */
  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    
    this.capabilityDtos.clear();
    
    this.capabilityDtos.add(resumeDto);
    this.capabilityDtos.add(uploadDto);
    this.capabilityDtos.add(downloadDto);
  }
  
  /**
   * Unit test for CapabilityService.getAllCapabilities().
   * 
   * @throws Exception
   */
  @Test
  public void getAllCapabilities() throws Exception {
    
    when(capabilityDao.getAllCapabilities()).thenReturn(capabilityDtos);
    
    List<CapabilityDto> returnedCapabilityDtos = 
        this.capabilityService.getAllCapabilities();
    
    assert(returnedCapabilityDtos)
      .equals(capabilityDtos);
  }
  
  /**
   * Unit test for CapabilityService.getCapabilityByName().
   * 
   * @throws Exception
   */
  @Test
  public void getCapabilityByName() throws Exception {
    
    when(capabilityDao.getCapabilityByName(PRIMARY_KEY))
      .thenReturn(resumeDto);
    
    CapabilityDto returnedCapabilityDto = 
        this.capabilityService.getCapabilityByName(PRIMARY_KEY);
    
    assert(returnedCapabilityDto)
      .equals(resumeDto);
  }
  
  
  /**
   * Unit test for CapabilityService.updateCapability().
   * 
   * @throws Exception
   */
  @Test
  public void updateCapability() throws Exception {
    
    CapabilityDto toUpdateCapabilityDto = 
        new CapabilityDto(PRIMARY_KEY, "Updated description");
    
    when(capabilityService.updateCapability(toUpdateCapabilityDto))
      .thenReturn(PRIMARY_KEY);
    
    String updatedPrimaryKey = this.capabilityService.updateCapability(toUpdateCapabilityDto);
    
    assert(updatedPrimaryKey).equals(PRIMARY_KEY);
  }
  
  /**
   * Unit test for CapabilityService.createCapability().
   * 
   * @throws Exception
   */
  @Test
  public void createCapability() throws Exception {
    
    CapabilityDto toCreateCapabilityDto = 
        new CapabilityDto("travel", "Travel capability");
    
    when(capabilityService.createCapability(toCreateCapabilityDto))
      .thenReturn("travel");
    
    String createdPrimaryKey = this.capabilityService.createCapability(toCreateCapabilityDto);
    
    assert(createdPrimaryKey).equals(toCreateCapabilityDto.getName());
  }
  
  /**
   * Unit test for CapabilityService.deleteCapability().
   * 
   * @throws Exception
   */
  @Test
  public void deleteCapability() throws Exception {
    
    String toDeletePrimaryKey = "travel";
    
    when(capabilityService.deleteCapability(toDeletePrimaryKey))
      .thenReturn(messageDto);
    
    MessageDto returnedMessageDto = 
        this.capabilityService.deleteCapability(toDeletePrimaryKey);
    
    assert(returnedMessageDto).equals(messageDto);
  }
}