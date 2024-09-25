package de.lumiliaro.symptothermapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Schema(description = "OptionDto")
@Data
@AllArgsConstructor
public class OptionDto {
    @NotNull(message = "Das Label muss gesetzt sein.")
    private String label;

    @NotNull(message = "Der Wert muss gesetzt sein.")
    private String value;

    private boolean disabled = false;
}
