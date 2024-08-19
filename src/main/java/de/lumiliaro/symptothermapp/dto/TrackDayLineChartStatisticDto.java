package de.lumiliaro.symptothermapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TrackDayLineChartStatisticDto {
    private String date;
    private Float temperature;
}
