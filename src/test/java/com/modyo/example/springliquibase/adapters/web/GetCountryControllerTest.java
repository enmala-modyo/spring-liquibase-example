package com.modyo.example.springliquibase.adapters.web;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.modyo.example.springliquibase.application.port.in.GetCountriesQuery;
import com.modyo.example.springliquibase.domain.model.Country;
import com.modyo.ms.commons.core.exceptions.NotFoundException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GetCountryControllerTest {

  GetCountriesQuery query = mock(GetCountriesQuery.class);
  GetCountryController controllerUnderTest = new GetCountryController(query);
  Country expectedCountry = Country.builder().code("CL").name("Chile").build();

  @BeforeEach
  void setUp() {
  }

  @Test
  void getOneCountry() {
    when(query.getCountry(anyString())).thenReturn(expectedCountry);
    var response = controllerUnderTest.getOneCountry("CL");
    assertTrue(response.getStatusCode().is2xxSuccessful());
    assertEquals(expectedCountry, response.getBody());
  }

  @Test
  void getOneCountryNotFound() {
    when(query.getCountry(anyString())).thenThrow(NotFoundException.class);
    assertThrows(NotFoundException.class, ()-> controllerUnderTest.getOneCountry("CL"));
  }

  @Test
  void getAllCountries() {
    when(query.getAllCountries()).thenReturn(List.of(expectedCountry));
    var response = controllerUnderTest.getAllCountries();
    assertTrue(response.getStatusCode().is2xxSuccessful());
    assertTrue(response.hasBody());
    assertTrue(response.getBody().size()>0);
    assertEquals(expectedCountry, response.getBody().get(0));
  }
}
