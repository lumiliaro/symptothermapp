package de.lumiliaro.symptothermapp.enums;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * Zervixschleim Enum
 * TROCKEN  -> t
 * NORMAL   -> O
 * FEUCHT   -> f
 * CREMIG   -> s
 * SPINNBAR -> S
 */
@Getter
@AllArgsConstructor
@Schema(description = "Cervical Mucus Enum", enumAsRef = true)
public enum CervicalMucusEnum {
    TROCKEN("t"),
    NORMAL("Ø"),
    FEUCHT("f"),
    CREMIG("s"),
    SPINNBAR("S+");

    private final String value;
}
