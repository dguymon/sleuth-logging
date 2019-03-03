package com.home.dguymon.domain.capability.domain.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Value;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonDeserialize(builder = CapabilityDto.CapabilityDtoBuilder.class)
public class CapabilityDto {
  
  @Getter
  @Setter
  String name;
  
  @Getter
  @Setter
  String description;
  
  @JsonPOJOBuilder(withPrefix = "")
  public static class CapabilityDtoBuilder {

  }
}