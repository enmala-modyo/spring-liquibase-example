package com.modyo.example.springdbschema.adapters.persistence;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.modyo.example.springdbschema.domain.model.Country;
import com.modyo.ms.commons.core.exceptions.NotFoundException;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CountryJpaEntityAdapterTest {

  CountryJpaRepository repository = mock(CountryJpaRepository.class);
  CountryMapper mapper = mock(CountryMapper.class);
  CountryPersistenceAdapter adapterUnderTest;

  Country expectedCountry = Country.builder().code("CL").name("Chile").build();

  @BeforeEach
  void setUp() {
    when(mapper.toEntity(any())).thenReturn(expectedCountry);
    adapterUnderTest = new CountryPersistenceAdapter(repository, mapper);
  }

  @Test
  void testCountryFound() {
    when(repository.findById(any())).thenReturn(Optional.of(new CountryJpaEntity()));
    assertEquals(expectedCountry, adapterUnderTest.loadCountry("CL"));
  }

  @Test
  void testCountryNotFound() {
    when(repository.findById(any())).thenReturn(Optional.empty());
    assertThrows(NotFoundException.class,()-> adapterUnderTest.loadCountry("CI"));
  }

  @Test
  void testLoadAll() {
    when(repository.findAll()).thenReturn(List.of(new CountryJpaEntity(),new CountryJpaEntity()));
    var response = adapterUnderTest.loadAllCountries();
    assertEquals(2, response.size());
    assertEquals(expectedCountry,response.get(0));
  }
}
