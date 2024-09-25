package de.lumiliaro.symptothermapp.dto;

import java.time.LocalDateTime;

import de.lumiliaro.symptothermapp.enums.BleedingEnum;
import de.lumiliaro.symptothermapp.enums.CervicalMucusEnum;
import de.lumiliaro.symptothermapp.enums.CyclusDotTypeEnum;
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
    @NotNull(message = "Das Datum muss gesetzt sein.")
    private String cyclusDay;

    @NotNull(message = "Das Datum muss gesetzt sein.")
    private String date;

    @Schema(nullable = true)
    private Float temperature;

    @Schema(nullable = true)
    private String cervicalMucus;

    @Schema(nullable = true)
    private String bleeding;

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
}
