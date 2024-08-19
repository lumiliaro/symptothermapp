package de.lumiliaro.symptothermapp.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OptionDto {
    @NotNull
    private String label;
    @NotNull
    private String value;
    private boolean disabled = false;
}
