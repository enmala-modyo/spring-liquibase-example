package com.modyo.example.springdbschema.adapters.web;

import com.modyo.example.springdbschema.application.port.in.GetCountriesQuery;
import com.modyo.example.springdbschema.domain.model.Country;
import io.swagger.annotations.Api;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"example"})
@RestController
@RequiredArgsConstructor
public class GetCountryController {

  private final GetCountriesQuery query;

  @GetMapping(
      path = "/countries/{code}", produces = "application/json"
  )
  ResponseEntity<Country> getOneCountry(
      @Valid
      @PathVariable("code") @Size(min = 2, max = 2) String code
  ) {
    return ResponseEntity.ok(query.getCountry(code));
  }

  @GetMapping(
      path = "/countries", produces = "application/json"
  )
  ResponseEntity<List<Country>> getAllCountries(
  ) {
    return ResponseEntity.ok(query.getAllCountries());
  }
}
