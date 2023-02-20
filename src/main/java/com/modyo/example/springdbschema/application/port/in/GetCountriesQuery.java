package com.modyo.example.springdbschema.application.port.in;

import com.modyo.example.springdbschema.domain.model.Country;
import java.util.List;

public interface GetCountriesQuery {
  Country getCountry(String code);
  List<Country> getAllCountries();
}
