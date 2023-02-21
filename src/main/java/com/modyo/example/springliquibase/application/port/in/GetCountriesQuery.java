package com.modyo.example.springliquibase.application.port.in;

import com.modyo.example.springliquibase.domain.model.Country;
import java.util.List;

public interface GetCountriesQuery {
  Country getCountry(String code);
  List<Country> getAllCountries();
}
