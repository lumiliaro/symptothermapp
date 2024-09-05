package de.lumiliaro.symptothermapp.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CyclusStatisticDto {
    @NotNull
    private String cyclusDay;
    @NotNull
    private String date;
    private Float temperature;
    private String cervicalMucus;
    private String bleeding;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
