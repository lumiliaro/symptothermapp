package de.lumiliaro.symptothermapp.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TrackDayLineChartStatisticDto {
    @NotNull
    private String date;
    private Float temperature;
    private String cervicalMucus;
}
