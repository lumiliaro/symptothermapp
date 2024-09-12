package de.lumiliaro.symptothermapp.enums;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Cervix / Gebärmutterhals Enum
 * OPEN -> offen
 * PARTIALLY_OPEN -> leicht
 * CLOSED -> geschlossen
 */
@Getter
@AllArgsConstructor
@Schema(description = "CervixOpeningState Enum", enumAsRef = true)
public enum CervixOpeningStateEnum implements ValueEnum {
    OPEN("offen"),
    PARTIALLY_OPEN("leicht geöffnet"),
    CLOSED("geschlossen");

    private final String value;

    @Override
    public String getValue() {
        return this.value;
    }
}
