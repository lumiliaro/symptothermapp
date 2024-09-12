package de.lumiliaro.symptothermapp.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Repräsentiert eine Fehlermeldung")
public class ErrorDto {

  @Schema(description = "Fehlermeldung", example = "Ressource nicht gefunden")
  private String message;

  @Schema(description = "Fehlercode", example = "ERR_NOT_FOUND")
  private String code;

  @Schema(description = "Zeitstempel des Fehlers", example = "2023-04-15T10:30:00")
  private LocalDateTime timestamp;

  @Schema(description = "HTTP-Statuscode", example = "404")
  private int status;

  public ErrorDto(String message, String code, int status) {
    this.message = message;
    this.code = code;
    this.status = status;
    this.timestamp = LocalDateTime.now();
  }
}