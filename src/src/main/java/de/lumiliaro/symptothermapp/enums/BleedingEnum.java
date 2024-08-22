package de.lumiliaro.symptothermapp.enums;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Blutung Enum
 * STARK          -> stark
 * MITTEL         -> mittel
 * SCHWACH        -> schwach
 * SCHMIERBLUTUNG -> Schmierblutung
 */
@Getter
@AllArgsConstructor
@Schema(description = "Bleeding Enum", enumAsRef = true)
public enum BleedingEnum {

    STARK("stark"),
    MITTEL("mittel"),
    SCHWACH("schwach"),
    SCHMIERBLUTUNG("Schmierblutung");

    private final String value;

}
