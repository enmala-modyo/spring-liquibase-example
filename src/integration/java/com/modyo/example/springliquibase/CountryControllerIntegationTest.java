package com.modyo.example.springliquibase;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.modyo.example.springliquibase.domain.model.Country;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class CountryControllerIntegationTest {
  @Autowired
  private MockMvc mockMvc;

  private final Country expectedCountry = Country.builder().code("CL").name("Chile").build();
  ObjectMapper objectMapper = new ObjectMapper();

  @Test
  void givenOneCountryCode_whenSendReq_thenVerifyFound() throws Exception {
    mockMvc.perform(
        get("/countries/CL"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().json(objectMapper.writeValueAsString(expectedCountry)));
  }

  @Test
  void givenOneCountryCode_whenSendReq_thenVerifyNotFound() throws Exception {
    mockMvc.perform(
            get("/countries/CI"))
        .andDo(print())
        .andExpect(status().is(404));
  }

  @Test
  void givenFourCountriesInDb_whenSendReq_ThenVerifyAllReturned() throws Exception {
    mockMvc.perform(
            get("/countries"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$[?(@.code == 'CL' && @.name == 'Chile')]").exists());
  }
}
