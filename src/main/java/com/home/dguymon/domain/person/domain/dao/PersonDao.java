package com.home.dguymon.domain.person.domain.dao;

import com.home.dguymon.domain.capability.domain.dto.MessageDto;

import com.home.dguymon.domain.person.domain.dto.PersonDto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class PersonDao {
  
  public List<PersonDto> getAllPersons() {

    return new ArrayList<>();
  }
  
  public PersonDto getPersonById(Long id) {
    
    return new PersonDto();
  }
  
  public MessageDto updatePerson(PersonDto person) {
    
    return new MessageDto();
  }
  
  public MessageDto createPerson(PersonDto person) {
    
    return new MessageDto();
  }
  
  public MessageDto deletePerson(Long id) {
    
    return new MessageDto();
  }
}
