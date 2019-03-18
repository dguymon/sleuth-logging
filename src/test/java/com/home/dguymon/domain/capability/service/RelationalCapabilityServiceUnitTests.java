package com.home.dguymon.domain.capability.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.home.dguymon.domain.capability.domain.dao.RelationalCapabilityDao;
import com.home.dguymon.domain.capability.domain.dto.PersistentRelationalCapabilityDto;
import com.home.dguymon.domain.capability.domain.dto.RelationalCapabilityDto;

import static org.mockito.Mockito.*;

/**
 * Unit tests for the RelationalCapabilityService.
 * 
 * @author Danazn
 *
 */
@RunWith(SpringRunner.class)
public class RelationalCapabilityServiceUnitTests {
  
  private static final Long PRIMARY_KEY = 1L;
  private static final Long CREATE_OR_DELETE_PRIMARY_KEY = 100L;
  
  @Mock
  RelationalCapabilityDao relationalCapabilityDao;
  
  @InjectMocks
  RelationalCapabilityService relationalCapabilityService = 
    new RelationalCapabilityService();
  
  private List<PersistentRelationalCapabilityDto> 
    persistentRelationalCapabilityDtos = 
      new ArrayList<>();
  
  private PersistentRelationalCapabilityDto resumeDto = 
      new PersistentRelationalCapabilityDto(PRIMARY_KEY, "resume", "Resume capability");
  
  private PersistentRelationalCapabilityDto uploadDto = 
      new PersistentRelationalCapabilityDto(2L, "upload", "Upload capability");
  
  private PersistentRelationalCapabilityDto downloadDto = 
      new PersistentRelationalCapabilityDto(3L, "download", "Download capability");
  
  ObjectMapper mapper = new ObjectMapper();
  
  /**
   * Called before each unit test
   */
  @Before
  public void setup() {
    
    MockitoAnnotations.initMocks(this);
    
    this.persistentRelationalCapabilityDtos.clear();
    
    this.persistentRelationalCapabilityDtos.add(resumeDto);
    this.persistentRelationalCapabilityDtos.add(uploadDto);
    this.persistentRelationalCapabilityDtos.add(downloadDto);
  }
  
  /**
   * Unit test for retrieving all capabilities 
   * from PostGreSQL RDS.
   */
  @Test
  public void getAllRelationalCapabilities() {
    
    when(relationalCapabilityDao.findAll())
        .thenReturn(persistentRelationalCapabilityDtos);
    
    List<PersistentRelationalCapabilityDto> 
      returnedPersistentCapabilityDtos = 
        this.relationalCapabilityDao.findAll();
    
    assert(returnedPersistentCapabilityDtos)
      .equals(persistentRelationalCapabilityDtos);
  }
  
  /**
   * Unit test for retrieving a capability by id 
   * from PostGreSQL RDS.
   */
  @Test
  public void getRelationalCapabilityById() {
    
    when(this.relationalCapabilityDao.getOne(PRIMARY_KEY))
      .thenReturn(resumeDto);
    
    Optional<PersistentRelationalCapabilityDto>
      returnedPersistentCapabilityDto = 
        this.relationalCapabilityService.getRelationalCapabilityById(PRIMARY_KEY);
    
    if(returnedPersistentCapabilityDto.isPresent()) {
      assert(returnedPersistentCapabilityDto.get()) 
        .equals(resumeDto);
    }
  }
  
  /**
   * Unit test for updating an existing capability in 
   * PostGreSQL RDS.
   */
  @Test
  public void updateRelationalCapability() {
    
    RelationalCapabilityDto toUpdateRelationalCapabilityDto = 
        new RelationalCapabilityDto(PRIMARY_KEY, "resume", "Updated description");
    
    PersistentRelationalCapabilityDto toUpdatePersistentCapabilityDto = 
        new PersistentRelationalCapabilityDto(PRIMARY_KEY,
            toUpdateRelationalCapabilityDto.getName(),
            toUpdateRelationalCapabilityDto.getDescription());
    
    when(this.relationalCapabilityDao.saveAndFlush(toUpdatePersistentCapabilityDto))
      .thenReturn(toUpdatePersistentCapabilityDto);
    
    Long updatedPrimaryKey = 
        this.relationalCapabilityService.updateOrCreateRelationalCapability(toUpdatePersistentCapabilityDto);
    
    assert(updatedPrimaryKey).equals(PRIMARY_KEY);
  }
  
  /**
   * Unit test for creating a capability in 
   * PostGreSQL RDS.
   */
  @Test
  public void createRelationalCapability() {
    
    PersistentRelationalCapabilityDto toCreatePersistentCapabilityDto = 
        new PersistentRelationalCapabilityDto(null, "travel", "Travel capability");
    
    PersistentRelationalCapabilityDto createdRelationalCapabilityDto = 
        new PersistentRelationalCapabilityDto(
            CREATE_OR_DELETE_PRIMARY_KEY,
            "travel",
            "Travel capability");
    
    when(this.relationalCapabilityDao.saveAndFlush(toCreatePersistentCapabilityDto))
    .thenReturn(createdRelationalCapabilityDto);
     
    this.relationalCapabilityService.updateOrCreateRelationalCapability(toCreatePersistentCapabilityDto);
    
    verify(this.relationalCapabilityDao, times(1)).saveAndFlush(toCreatePersistentCapabilityDto);
  }
  
  /**
   * Unit test for deleting capability from 
   * PostGreSQL RDS.
   * 
   * @throws Exception
   */
  @Test
  public void deleteRelationalCapability() {
    
    this.relationalCapabilityService.deleteRelationalCapabilityById(CREATE_OR_DELETE_PRIMARY_KEY);
    
    verify(this.relationalCapabilityDao, times(1)).deleteById(CREATE_OR_DELETE_PRIMARY_KEY);
  }
}