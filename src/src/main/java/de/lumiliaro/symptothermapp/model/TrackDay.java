package de.lumiliaro.symptothermapp.model;

import de.lumiliaro.symptothermapp.enums.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "track_day")
@Data
public class TrackDay {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "temperature")
    @NotNull(message = "Die Temperatur muss gesetzt sein.")
    @Range(min = 33, max = 42)
    private float temperature;

    @Column(name = "track_day", unique = true)
    @NotNull(message = "Das Datum muss gesetzt sein.")
    @PastOrPresent(message = "Das Datum muss in der Vergangenheit oder Gegenwart liegen.")
    @Temporal(TemporalType.DATE)
    private Date trackDay;

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

    @Column(name = "hadSex")
    private Boolean hadSex = false;

    // mit Verhütungsmittel
    @Column(name = "withContraceptives")
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
