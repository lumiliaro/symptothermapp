package de.lumiliaro.symptothermapp.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CyclusStatisticDto {
    @NotNull
    private String cyclusDay;
    @NotNull
    private String date;
    private Float temperature;
    private String cervicalMucus;
    private String bleeding;
    private boolean fertile = false; // Möglicher fruchtbarer Tag
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
