package com.modyo.example.springdbschema.application.service;

import com.modyo.example.springdbschema.application.port.in.GetCountriesQuery;
import com.modyo.example.springdbschema.application.port.out.LoadCountryPort;
import com.modyo.example.springdbschema.domain.model.Country;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetCountriesService implements GetCountriesQuery {

  private final LoadCountryPort port;
  @Override
  public Country getCountry(String code) {
    return port.loadCountry(code);
  }

  @Override
  public List<Country> getAllCountries() {
    return port.loadAllCountries();
  }
}
