package de.lumiliaro.symptothermapp.model;

import java.util.Date;
import java.util.List;

import de.lumiliaro.symptothermapp.enums.BleedingEnum;
import de.lumiliaro.symptothermapp.enums.CervicalMucusEnum;
import de.lumiliaro.symptothermapp.enums.CervixHeightPositionEnum;
import de.lumiliaro.symptothermapp.enums.CervixOpeningStateEnum;
import de.lumiliaro.symptothermapp.enums.CervixTextureEnum;
import de.lumiliaro.symptothermapp.enums.DisturbanceEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "track_day")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrackDay {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "day", unique = true)
    @NotNull(message = "Das Datum muss gesetzt sein.")
    @PastOrPresent(message = "Das Datum muss in der Vergangenheit oder Gegenwart liegen.")
    @Temporal(TemporalType.DATE)
    private Date day;

    @Column(name = "temperature")
    private Float temperature;

    @Column(name = "bleeding")
    @Enumerated(EnumType.STRING)
    private BleedingEnum bleeding;

    @Column(name = "cervical_mucus")
    @Enumerated(EnumType.STRING)
    private CervicalMucusEnum cervicalMucus;

    @Enumerated(EnumType.STRING)
    @Column(name = "cervix_opening_state")
    private CervixOpeningStateEnum cervixOpeningState;

    @Enumerated(EnumType.STRING)
    @Column(name = "cervix_height_position")
    private CervixHeightPositionEnum cervixHeightPosition;

    @Enumerated(EnumType.STRING)
    @Column(name = "cervix_texture")
    private CervixTextureEnum cervixTexture;

    @Column(name = "had_sex")
    private Boolean hadSex = false;

    // mit Verhütungsmittel
    @Column(name = "with_contraceptives")
    private Boolean withContraceptives = false;

    @Column(name = "disturbances")
    @Enumerated(EnumType.STRING)
    private List<DisturbanceEnum> disturbances;

    @Column(name = "other_disturbance_notes")
    @Size(max = 1000, message = "Die Sonstigen Störungen dürfen nicht länger als 1000 Zeichen lang sein.")
    private String otherDisturbanceNotes;

    @Column(name = "notes")
    @Size(max = 1000, message = "Die Notizen dürfen nicht länger als 1000 Zeichen lang sein.")
    private String notes;
}
