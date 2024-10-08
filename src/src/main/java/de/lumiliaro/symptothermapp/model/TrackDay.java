package de.lumiliaro.symptothermapp.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import de.lumiliaro.symptothermapp.enums.BleedingEnum;
import de.lumiliaro.symptothermapp.enums.CervicalMucusEnum;
import de.lumiliaro.symptothermapp.enums.CervixHeightPositionEnum;
import de.lumiliaro.symptothermapp.enums.CervixOpeningStateEnum;
import de.lumiliaro.symptothermapp.enums.CervixTextureEnum;
import de.lumiliaro.symptothermapp.enums.DisturbanceEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Schema(description = "Ein Tag der Menstruationsverfolgung.")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "track_day")
@Entity
public class TrackDay extends BaseEntity {

    @Column(name = "day", nullable = false, unique = true)
    @NotNull(message = "Das Datum muss gesetzt sein.")
    @PastOrPresent(message = "Das Datum muss in der Vergangenheit oder Gegenwart liegen.")
    @Temporal(TemporalType.DATE)
    private Date day;

    @Schema(nullable = true, minimum = "33", maximum = "42")
    @Column(name = "temperature")
    @Min(value = 33, message = "Die Temperatur muss mindestens 33 °C betragen.")
    @Max(value = 42, message = "Die Temperatur muss höchstens 42 °C betragen.")
    @Nullable
    private Float temperature;

    @Schema(nullable = true)
    @Column(name = "bleeding")
    @Enumerated(EnumType.STRING)
    private BleedingEnum bleeding;

    @Schema(nullable = true)
    @Column(name = "cervical_mucus")
    @Enumerated(EnumType.STRING)
    private CervicalMucusEnum cervicalMucus;

    @Schema(nullable = true)
    @Enumerated(EnumType.STRING)
    @Column(name = "cervix_opening_state")
    private CervixOpeningStateEnum cervixOpeningState;

    @Schema(nullable = true)
    @Enumerated(EnumType.STRING)
    @Column(name = "cervix_height_position")
    private CervixHeightPositionEnum cervixHeightPosition;

    @Schema(nullable = true)
    @Enumerated(EnumType.STRING)
    @Column(name = "cervix_texture")
    private CervixTextureEnum cervixTexture;

    @Column(name = "had_sex", nullable = false)
    private Boolean hadSex = false;

    // mit Verhütungsmittel
    @Column(name = "with_contraceptives", nullable = false)
    private Boolean withContraceptives = false;

    @Column(name = "disturbances")
    @Enumerated(EnumType.STRING)
    private List<DisturbanceEnum> disturbances;

    @Column(name = "other_disturbance_notes", length = 1000)
    @Size(max = 1000, message = "Die Sonstigen Störungen dürfen nicht länger als 1000 Zeichen lang sein.")
    private String otherDisturbanceNotes;

    @Schema(nullable = true)
    @Column(name = "notes", length = 1000)
    @Size(max = 1000, message = "Die Notizen dürfen nicht länger als 1000 Zeichen lang sein.")
    private String notes;

    public String getDayIsoDate() {
        return new SimpleDateFormat("yyyy-MM-dd").format(getDay());
    }
}
