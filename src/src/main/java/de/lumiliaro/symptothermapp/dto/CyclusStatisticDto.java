package de.lumiliaro.symptothermapp.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CyclusStatisticDto {
    @NotNull
    private String date;
    private Float temperature;
    private String cervicalMucus;
    private boolean hasBleeding;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
