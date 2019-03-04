package com.home.dguymon.domain.capability.domain.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.hateoas.ResourceSupport;

/**
 * HAL-compatible message object
 * 
 * @author Danazn
 *
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonDeserialize(builder = MessageDto.MessageDtoBuilder.class)
public class MessageDto extends ResourceSupport {
  
  @Getter
  @Setter
  String message;
  
  @Getter
  @Setter
  Boolean error;
  
  @JsonPOJOBuilder(withPrefix = "")
  public static class MessageDtoBuilder {

  }
}