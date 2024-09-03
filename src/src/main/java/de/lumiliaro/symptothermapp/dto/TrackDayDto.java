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
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TrackDayDto {
    private Float temperature;

    @NotNull(message = "Das Datum muss gesetzt sein.")
    @PastOrPresent(message = "Das Datum muss in der Vergangenheit oder Gegenwart liegen.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date day;

    private BleedingEnum bleeding = null;
    private CervicalMucusEnum cervicalMucus = null;
    private CervixOpeningStateEnum cervixOpeningState = null;
    private CervixHeightPositionEnum cervixHeightPosition = null;
    private CervixTextureEnum cervixTexture = null;
    @NotNull
    private final Boolean hadSex = false;
    @NotNull
    private final Boolean withContraceptives = false;
    private List<DisturbanceEnum> disturbances = null;

    @Size(max = 1000, message = "Die Sonstigen Störungen dürfen nicht länger als 1000 Zeichen lang sein.")
    private String otherDisturbanceNotes = null;

    @Size(max = 1000, message = "Die Notizen dürfen nicht länger als 1000 Zeichen lang sein.")
    private String notes = null;

    @Hidden
    @AssertTrue(message = "\"mit Verhütungsmittel\" darf nur aktiviert sein, wenn \"sex gehabt\" aktiviert ist.")
    public boolean isContraceptiveUsageValid() {
        return hadSex ? withContraceptives || !withContraceptives : !withContraceptives;
    }
}
