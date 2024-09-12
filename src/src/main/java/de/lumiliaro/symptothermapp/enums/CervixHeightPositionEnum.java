package de.lumiliaro.symptothermapp.enums;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Cervix / Gebärmutterhals Enum
 * HIGH -> hochstehend
 * LOW -> tiefstehend
 */
@Getter
@AllArgsConstructor
@Schema(description = "CervixHeightPosition Enum", enumAsRef = true)
public enum CervixHeightPositionEnum implements ValueEnum {
    HIGH("hochstehend"),
    LOW("tiefstehend");

    private final String value;

    @Override
    public String getValue() {
        return this.value;
    }
}
