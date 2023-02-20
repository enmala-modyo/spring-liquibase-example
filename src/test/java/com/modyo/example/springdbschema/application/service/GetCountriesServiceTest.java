package com.modyo.example.springdbschema.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.modyo.example.springdbschema.application.port.out.LoadCountryPort;
import com.modyo.example.springdbschema.domain.model.Country;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GetCountriesServiceTest {

  GetCountriesService serviceUnderTest;
  LoadCountryPort port = mock(LoadCountryPort.class);

  Country expectedCountry = Country.builder().code("CL").name("Chile").build();

  @BeforeEach
  void setUp() {
    serviceUnderTest = new GetCountriesService(port);
  }

  @Test
  void getCountry() {
    when(port.loadCountry(anyString())).thenReturn(expectedCountry);
    assertEquals(expectedCountry, serviceUnderTest.getCountry("CL"));
  }

  @Test
  void getAllCountries() {
    when(port.loadAllCountries()).thenReturn(List.of(expectedCountry));
    var response = serviceUnderTest.getAllCountries();
    assertEquals(1, response.size());
    assertEquals(expectedCountry, response.get(0));
  }
}
