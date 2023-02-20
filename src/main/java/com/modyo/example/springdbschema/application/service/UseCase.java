package com.modyo.example.springdbschema.application.service;

import com.modyo.example.springdbschema.application.port.in.InputPort;
import com.modyo.example.springdbschema.application.port.out.OutputPort;
import java.time.LocalDateTime;
import java.time.ZoneId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UseCase implements InputPort {

  private final OutputPort port;

  @Override
  public String getGreeting(String name) {
    var hour = LocalDateTime.now(ZoneId.of("America/Santiago")).getHour();
    return port.loadGreeting(hour) + " " + name + "!!!";
  }
}
