package com.home.dguymon;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.builder.SpringApplicationBuilder;

import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import org.springframework.context.annotation.Bean;

import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import org.springframework.web.client.RestTemplate;

@EnableEurekaClient
@SpringBootApplication
@EnableJpaAuditing
public class Application extends SpringBootServletInitializer {

  @Bean
  public RestTemplate restTemplate() {
      return new RestTemplate();
  }
  
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
    return builder.sources(Application.class);
  }
}