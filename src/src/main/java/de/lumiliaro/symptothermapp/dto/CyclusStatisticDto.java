package de.lumiliaro.symptothermapp.dto;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import de.lumiliaro.symptothermapp.enums.BleedingEnum;
import de.lumiliaro.symptothermapp.enums.CervicalMucusEnum;
import de.lumiliaro.symptothermapp.enums.CervixHeightPositionEnum;
import de.lumiliaro.symptothermapp.enums.CervixOpeningStateEnum;
import de.lumiliaro.symptothermapp.enums.CervixTextureEnum;
import de.lumiliaro.symptothermapp.enums.CyclusDotTypeEnum;
import de.lumiliaro.symptothermapp.enums.DisturbanceEnum;
import de.lumiliaro.symptothermapp.model.TrackDay;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "CyclusStatisticDto")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CyclusStatisticDto {

    public CyclusStatisticDto(TrackDay trackDay, int day, SimpleDateFormat dateFormatterResponse,
            boolean isFertileSet) {
        this.setTemperature(trackDay.getTemperature());
        this.setCyclusDay(String.valueOf(day + 1));
        this.setDate(dateFormatterResponse.format(trackDay.getDay()));
        this.setBleeding(trackDay.getBleeding());
        this.setCervicalMucus(trackDay.getCervicalMucus());
        this.setCervixOpeningState(trackDay.getCervixOpeningState());
        this.setCervixHeightPosition(trackDay.getCervixHeightPosition());
        this.setCervixTexture(trackDay.getCervixTexture());
        this.setHadSex(trackDay.getHadSex());
        this.setWithContraceptives(trackDay.getWithContraceptives());
        this.setDisturbances(trackDay.getDisturbances());
        this.setOtherDisturbanceNotes(trackDay.getOtherDisturbanceNotes());
        this.setNotes(trackDay.getNotes());
        this.setCreatedAt(trackDay.getCreatedAt());
        this.setUpdatedAt(trackDay.getUpdatedAt());

        if (isFertileSet) {
            // if fertile is set, set all days after to infertile
            this.setCyclusDotType(CyclusDotTypeEnum.INFERTILE);
        }
    }

    @Schema(nullable = true)
    private Float temperature;

    @NotNull(message = "Der Zyklustag muss gesetzt sein.")
    private String cyclusDay;

    @NotNull(message = "Das Datum muss gesetzt sein.")
    private String date;

    @Schema(nullable = true)
    private String bleeding;

    @Schema(nullable = true)
    private String cervicalMucus;

    @Schema(nullable = true)
    private String cervixOpeningState;

    @Schema(nullable = true)
    private String cervixHeightPosition;

    @Schema(nullable = true)
    private String cervixTexture;

    @NotNull
    private Boolean hadSex = false;

    @NotNull
    private Boolean withContraceptives = false;

    @Schema(nullable = true)
    private List<String> disturbances;

    @Schema(nullable = true)
    private String otherDisturbanceNotes;

    @Schema(nullable = true)
    private String notes;

    @Schema(nullable = true)
    private CyclusDotTypeEnum cyclusDotType;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public void setBleeding(BleedingEnum bleeding) {
        if (bleeding == null) {
            this.cyclusDotType = null;
            return;
        }

        this.bleeding = bleeding.getValue();
        this.cyclusDotType = CyclusDotTypeEnum.BLEEDING;
    }

    public void setCervicalMucus(CervicalMucusEnum cervicalMucus) {
        if (cervicalMucus == null) {
            this.cyclusDotType = null;
            return;
        }

        this.cervicalMucus = cervicalMucus.getValue();
    }

    public void setCervixOpeningState(CervixOpeningStateEnum cervixOpeningState) {
        if (cervixOpeningState == null) {
            return;
        }

        this.cervixOpeningState = cervixOpeningState.getValue();
    }

    public void setCervixHeightPosition(CervixHeightPositionEnum cervixHeightPosition) {
        if (cervixHeightPosition == null) {
            return;
        }
        this.cervixHeightPosition = cervixHeightPosition.getValue();
    }

    public void setCervixTexture(CervixTextureEnum cervixTexture) {
        if (cervixTexture == null) {
            return;
        }

        this.cervixTexture = cervixTexture.getValue();
    }

    public void setDisturbances(List<DisturbanceEnum> disturbances) {
        if (disturbances == null || disturbances.isEmpty()) {
            return;
        }

        this.disturbances = new ArrayList<String>();

        for (DisturbanceEnum disturbance : disturbances) {
            this.disturbances.add(disturbance.getValue());
        }
    }
}
