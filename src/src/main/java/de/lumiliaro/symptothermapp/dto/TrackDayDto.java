package de.lumiliaro.symptothermapp.dto;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import de.lumiliaro.symptothermapp.enums.BleedingEnum;
import de.lumiliaro.symptothermapp.enums.CervicalMucusEnum;
import de.lumiliaro.symptothermapp.enums.CervixHeightPositionEnum;
import de.lumiliaro.symptothermapp.enums.CervixOpeningStateEnum;
import de.lumiliaro.symptothermapp.enums.CervixTextureEnum;
import de.lumiliaro.symptothermapp.enums.DisturbanceEnum;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Schema(description = "TrackDayDto")
@Getter
@Setter
@RequiredArgsConstructor
public class TrackDayDto {

    @Schema(nullable = true)
    private final Float temperature;

    @NotNull(message = "Das Datum muss gesetzt sein.")
    @PastOrPresent(message = "Das Datum muss in der Vergangenheit oder Gegenwart liegen.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private final Date day;

    @Schema(nullable = true)
    private BleedingEnum bleeding;

    @Schema(nullable = true)
    private CervicalMucusEnum cervicalMucus;

    @Schema(nullable = true)
    private CervixOpeningStateEnum cervixOpeningState;

    @Schema(nullable = true)
    private CervixHeightPositionEnum cervixHeightPosition;

    @Schema(nullable = true)
    private CervixTextureEnum cervixTexture;

    @NotNull
    private Boolean hadSex = false;

    @NotNull
    private Boolean withContraceptives = false;

    private List<DisturbanceEnum> disturbances;

    @Schema(nullable = true)
    @Size(max = 1000, message = "Die Sonstigen Störungen dürfen nicht länger als 1000 Zeichen lang sein.")
    private String otherDisturbanceNotes;

    @Schema(nullable = true)
    @Size(max = 1000, message = "Die Notizen dürfen nicht länger als 1000 Zeichen lang sein.")
    private String notes;

    @Hidden
    @AssertTrue(message = "\"mit Verhütungsmittel\" darf nur aktiviert sein, wenn \"sex gehabt\" aktiviert ist.")
    public boolean isContraceptiveUsageValid() {
        return hadSex ? withContraceptives || !withContraceptives : !withContraceptives;
    }
}
