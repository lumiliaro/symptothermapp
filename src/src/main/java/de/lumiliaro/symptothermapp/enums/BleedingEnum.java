package de.lumiliaro.symptothermapp.enums;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Blutung Enum
 * STRONG (STARK)                    -> stark
 * MEDIUM (MITTEL)                   -> mittel
 * WEAK (SCHWACH)                    -> schwach
 * SPOTTING_BLEEDING (SCHMIERBLUTUNG) -> Schmierblutung
 */
@Getter
@AllArgsConstructor
@Schema(description = "Bleeding Enum", enumAsRef = true)
public enum BleedingEnum {
    STRONG("stark"),
    MEDIUM("mittel"),
    WEAK("schwach"),
    SPOTTING_BLEEDING("Schmierblutung");

    private final String value;

}
