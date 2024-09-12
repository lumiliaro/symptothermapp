package de.lumiliaro.symptothermapp.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.lumiliaro.symptothermapp.dto.OptionDto;
import de.lumiliaro.symptothermapp.enums.BleedingEnum;
import de.lumiliaro.symptothermapp.enums.CervicalMucusEnum;
import de.lumiliaro.symptothermapp.enums.CervixHeightPositionEnum;
import de.lumiliaro.symptothermapp.enums.CervixOpeningStateEnum;
import de.lumiliaro.symptothermapp.enums.CervixTextureEnum;
import de.lumiliaro.symptothermapp.enums.DisturbanceEnum;
import de.lumiliaro.symptothermapp.service.OptionsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/options")
@Tag(name = "Options", description = "Operationen für Auswahloptionen")
@RequiredArgsConstructor
public class OptionsController {

    private final OptionsService optionsService;

    @GetMapping("/bleeding")
    @Operation(summary = "Blutungsoptionen abrufen")
    public List<OptionDto> getBleedingOptions() {
        return optionsService.getEnumOptions(BleedingEnum.class);
    }

    @GetMapping("/cervical-mucus")
    @Operation(summary = "Zervixschleimoptionen abrufen")
    public List<OptionDto> getCervicalMucusOptions() {
        return optionsService.getEnumOptions(CervicalMucusEnum.class);
    }

    @GetMapping("/cervix/height-position")
    @Operation(summary = "Zervixhöhe-/Positionsoptionen abrufen")
    public List<OptionDto> getCervixHeightPositionOptions() {
        return optionsService.getEnumOptions(CervixHeightPositionEnum.class);
    }

    @GetMapping("/cervix/opening-state")
    @Operation(summary = "Zervixöffnungszustandsoptionen abrufen")
    public List<OptionDto> getCervixOpeningStateOptions() {
        return optionsService.getEnumOptions(CervixOpeningStateEnum.class);
    }

    @GetMapping("/cervix/texture")
    @Operation(summary = "Zervixbeschaffenheitsoptionen abrufen")
    public List<OptionDto> getCervixTextureOptions() {
        return optionsService.getEnumOptions(CervixTextureEnum.class);
    }

    @GetMapping("/disturbance")
    @Operation(summary = "Störungsoptionen abrufen")
    public List<OptionDto> getDisturbanceOptions() {
        return optionsService.getEnumOptions(DisturbanceEnum.class);
    }
}
