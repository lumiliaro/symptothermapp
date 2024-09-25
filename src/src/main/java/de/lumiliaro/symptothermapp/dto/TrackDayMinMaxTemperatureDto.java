package de.lumiliaro.symptothermapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Schema(description = "TrackDayMinMaxTemperatureDto")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TrackDayMinMaxTemperatureDto {
  @Schema(nullable = true)
  private Float minTemperature = null;

  @Schema(nullable = true)
  private Float maxTemperature = null;
}
