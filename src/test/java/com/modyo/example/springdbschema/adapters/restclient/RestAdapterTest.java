package com.modyo.example.springdbschema.adapters.restclient;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class RestAdapterTest {

  @Test
  void testCreation(){
    assertDoesNotThrow(RestAdapter::new);
  }
}
