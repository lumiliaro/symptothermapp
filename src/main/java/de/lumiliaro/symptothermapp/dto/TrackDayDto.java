package de.lumiliaro.symptothermapp.dto;

import de.lumiliaro.symptothermapp.enums.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import java.util.Date;
import java.util.List;

@Data
public class TrackDayDto {
    @NotNull(message = "Die Temperatur muss gesetzt sein.")
    @Range(min = 33, max = 42)
    private float temperature;

    @NotNull(message = "Das Datum muss gesetzt sein.")
    @PastOrPresent(message = "Das Datum muss in der Vergangenheit oder Gegenwart liegen.")
    private Date trackDay;

    private BleedingEnum bleeding = null;
    private CervicalMucusEnum cervicalMucus = null;
    private CervixOpeningStateEnum cervixOpeningState = null;
    private CervixHeightPositionEnum cervixHeightPosition = null;
    private CervixTextureEnum cervixTexture = null;
    private Boolean hadSex = false;
    private Boolean withContraceptives = false;
    private List<DisturbanceEnum> disturbances = null;

    @Size(max = 1000, message = "Die Sonstigen Störungen dürfen nicht länger als 1000 Zeichen lang sein.")
    private String otherDisturbanceNotes = null;

    @Size(max = 1000, message = "Die Notizen dürfen nicht länger als 1000 Zeichen lang sein.")
    private String notes = null;
}
