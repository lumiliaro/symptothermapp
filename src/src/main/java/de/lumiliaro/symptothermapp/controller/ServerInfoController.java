package de.lumiliaro.symptothermapp.controller;

import org.springframework.boot.info.BuildProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.lumiliaro.symptothermapp.dto.ServerInfoDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@Tag(name = "Server-Infor", description = "Get server informations")
@RequestMapping("/api/info")
public class ServerInfoController {

  private final BuildProperties buildProperties;

  @GetMapping()
  public ResponseEntity<ServerInfoDto> getServerInfo() {
    return ResponseEntity.ok(new ServerInfoDto(buildProperties.getVersion()));
  }
}
