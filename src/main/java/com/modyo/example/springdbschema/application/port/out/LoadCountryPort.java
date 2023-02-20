package com.modyo.example.springdbschema.application.port.out;

import com.modyo.example.springdbschema.domain.model.Country;
import java.util.List;

public interface LoadCountryPort {
  Country loadCountry(String code);
  List<Country> loadAllCountries();
}
