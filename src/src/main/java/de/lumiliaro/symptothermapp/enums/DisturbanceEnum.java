package de.lumiliaro.symptothermapp.enums;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "Disturbance Enum", enumAsRef = true)
public enum DisturbanceEnum implements ValueEnum {
    GOING_TO_BED_UNUSUALLY_LATE("Ungewohnt spätes Zubettgehen"),
    UNUSUAL_ALCOHOL_CONSUMPTION("Ungewohnter Alkoholgenuss"),
    STRESS("Stress"),
    MENTAL_STRESS("Seelische Belastung"),
    TIME_CHANGE("Zeitumstellung"),
    TIME_SHIFT("Zeitverschiebung"),
    SHORT_OR_SHORT_NIGHT_REST("Kurze oder gestörte Nachtruhe"),
    EATING_LATE_EVENINGS("Essen spätabends"),
    TRAVEL_AND_CHANGE_OF_CLIMATE("Reisen und/oder Klimawechsel"),
    ILLNESS_AND_UNPLEASANT_FEVER("Erkrankung und Unpässlichkeiten/Fieber"),
    OTHER_ILLNESSES("Andere Krankheiten"),
    MEDICINE("Medikament"),
    THERMOMETER_CHANGE_IN_THE_CYCLE("Thermometerwechsel im Zyklus"),
    EXCITEMENT("Aufregung"),
    CELEBRATION_EVENINGS("Feiern spätabends"),
    SHIFT_WORK("Schichtarbeit"),
    OTHER("Sonstiges");

    private final String value;

    @Override
    public String getValue() {
        return this.value;
    }
}
