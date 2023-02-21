package com.modyo.example.springliquibase.application.service;

import com.modyo.example.springliquibase.application.port.in.GetCountriesQuery;
import com.modyo.example.springliquibase.application.port.out.LoadCountryPort;
import com.modyo.example.springliquibase.domain.model.Country;
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
