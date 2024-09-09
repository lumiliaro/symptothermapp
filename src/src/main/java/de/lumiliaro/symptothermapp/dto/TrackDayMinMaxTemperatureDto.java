package de.lumiliaro.symptothermapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TrackDayMinMaxTemperatureDto {
  private Float minTemperature;
  private Float maxTemperature;
}
