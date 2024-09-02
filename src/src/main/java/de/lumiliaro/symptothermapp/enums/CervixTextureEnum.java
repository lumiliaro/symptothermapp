package de.lumiliaro.symptothermapp.enums;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * Cervix / Gebärmutterhals Texture Enum
 * HARD -> hart
 * SOFT -> weich
 */
@Getter
@AllArgsConstructor
@Schema(description = "CervixTexture Enum", enumAsRef = true)
public enum CervixTextureEnum {
    HARD("hart"),
    SOFT("weich");

    private final String value;
}
