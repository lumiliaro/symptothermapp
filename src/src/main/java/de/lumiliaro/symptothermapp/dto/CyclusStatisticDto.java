package de.lumiliaro.symptothermapp.dto;

import java.time.LocalDateTime;

import de.lumiliaro.symptothermapp.enums.BleedingEnum;
import de.lumiliaro.symptothermapp.enums.CervicalMucusEnum;
import de.lumiliaro.symptothermapp.enums.CyclusDotTypeEnum;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CyclusStatisticDto {
    @NotNull
    private String cyclusDay;
    @NotNull
    private String date;
    private Float temperature;
    private String cervicalMucus;
    private String bleeding;
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
