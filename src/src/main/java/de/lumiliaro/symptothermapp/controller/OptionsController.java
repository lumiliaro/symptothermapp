package de.lumiliaro.symptothermapp.controller;

import de.lumiliaro.symptothermapp.dto.OptionDto;
import de.lumiliaro.symptothermapp.enums.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/options")
@Tag(name = "options", description = "Operations related for Select options")
public class OptionsController {

    @GetMapping("/bleeding")
    public List<OptionDto> getBleedingOptions() {
        return Arrays.stream(BleedingEnum.values())
                .map(enumValue -> new OptionDto(enumValue.getValue(), enumValue.name(), false))
                .collect(Collectors.toList());
    }

    @GetMapping("/cervical-mucus")
    public List<OptionDto> getCervicalMucusOptions() {
        return Arrays.stream(CervicalMucusEnum.values())
                .map(enumValue -> new OptionDto(enumValue.getValue(), enumValue.name(), false))
                .collect(Collectors.toList());
    }

    @GetMapping("/cervix/height-position")
    public List<OptionDto> getCervixHeightPositionOptions() {
        return Arrays.stream(CervixHeightPositionEnum.values())
                .map(enumValue -> new OptionDto(enumValue.getValue(), enumValue.name(), false))
                .collect(Collectors.toList());
    }

    @GetMapping("/cervix/opening-state")
    public List<OptionDto> getCervixOpeningStateOptions() {
        return Arrays.stream(CervixOpeningStateEnum.values())
                .map(enumValue -> new OptionDto(enumValue.getValue(), enumValue.name(), false))
                .collect(Collectors.toList());
    }

    @GetMapping("/cervix/texture")
    public List<OptionDto> getCervixTextureOptions() {
        return Arrays.stream(CervixTextureEnum.values())
                .map(enumValue -> new OptionDto(enumValue.getValue(), enumValue.name(), false))
                .collect(Collectors.toList());
    }

    @GetMapping("/disturbance")
    public List<OptionDto> getDisturbanceOptions() {
        return Arrays.stream(DisturbanceEnum.values())
                .map(enumValue -> new OptionDto(enumValue.getValue(), enumValue.name(), false))
                .collect(Collectors.toList());
    }
}
