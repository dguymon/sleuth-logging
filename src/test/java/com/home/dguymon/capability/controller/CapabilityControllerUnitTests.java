package com.home.dguymon.capability.controller;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class CapabilityControllerUnitTests {
  
  @Autowired
  private MockMvc mvc;
  
  @Test
  public void getAllCapabilities() throws Exception {
    this.mvc.perform(get("/v1/capabilities"))
      .andExpect(status().isOk());
  }
  
  @Test
  public void getCapabilityByName() throws Exception {
    this.mvc.perform(get("/v1/capabilities/resume"))
      .andExpect(status().isOk());
  }
}
