package com.modyo.example.springliquibase.adapters.restclient;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class RestAdapterTest {

  @Test
  void testCreation(){
    assertDoesNotThrow(RestAdapter::new);
  }
}
