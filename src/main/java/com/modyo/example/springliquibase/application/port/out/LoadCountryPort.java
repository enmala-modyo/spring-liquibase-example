package com.modyo.example.springliquibase.application.port.out;

import com.modyo.example.springliquibase.domain.model.Country;
import java.util.List;

public interface LoadCountryPort {
  Country loadCountry(String code);
  List<Country> loadAllCountries();
}
