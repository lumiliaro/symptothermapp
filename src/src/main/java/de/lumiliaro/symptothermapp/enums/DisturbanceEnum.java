package de.lumiliaro.symptothermapp.enums;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "Disturbance Enum", enumAsRef = true)
public enum DisturbanceEnum {
    UNGEWOHNT_SPAETES_ZUBETTGEHEN("Ungewohnt spätes Zubettgehen"),
    UNGEWOHNTER_ALKOHOLGENUSS("Ungewohnter Alkoholgenuss"),
    STRESS("Stress"),
    SEELISCHE_BELASTUNG("Seelische Belastung"),
    ZEITUMSTELLUNG("Zeitumstellung"),
    ZEITVERSCHIEBUNG("Zeitverschiebung"),
    KURZE_ODER_GESTOERTE_NACHTRUHE("Kurze oder gestörte Nachtruhe"),
    ESSEN_SPAETABENDS("Essen spätabends"),
    REISEN_UND_ODER_KLIMAWECHSEL("Reisen und/oder Klimawechsel"),
    ERKRANKUNG_UND_UNPAESSLICHKEITEN_FIEBER("Erkrankung und Unpässlichkeiten/Fieber"),
    ANDERE_KRANKHEITEN("Andere Krankheiten"),
    MEDIKAMENT("Medikament"),
    THERMOMETERWECHSEL_IM_ZYKLUS("Thermometerwechsel im Zyklus"),
    AUFREGUNG("Aufregung"),
    FEIERN_SPAETABENDS("Feiern spätabends"),
    SCHICHTARBEIT("Schichtarbeit"),
    SONSTIGES("Sonstiges");

    private final String value;
}


















