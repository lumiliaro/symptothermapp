package de.lumiliaro.symptothermapp.enums;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * Zervixschleim Enum
 * DRY (trocken)        -> t
 * NORMAL (normal)      -> Ø
 * MOIST (feucht)       -> f
 * CREAMY (cremig)      -> s
 * SPINNABLE (spinnbar) -> S+
 */
@Getter
@AllArgsConstructor
@Schema(description = "Cervical Mucus Enum", enumAsRef = true)
public enum CervicalMucusEnum {
    DRY("t"),
    NORMAL("Ø"),
    MOIST("f"),
    CREAMY("s"),
    SPINNABLE("S+");

    private final String value;
}
