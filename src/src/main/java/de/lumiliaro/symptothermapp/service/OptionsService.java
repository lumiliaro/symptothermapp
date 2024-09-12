package de.lumiliaro.symptothermapp.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import de.lumiliaro.symptothermapp.dto.OptionDto;
import de.lumiliaro.symptothermapp.enums.ValueEnum;

@Service
public class OptionsService {

  public <T extends Enum<T> & ValueEnum> List<OptionDto> getEnumOptions(Class<T> enumClass) {
    return Arrays.stream(enumClass.getEnumConstants())
        .map(e -> new OptionDto(e.getValue(), e.name(), false))
        .collect(Collectors.toList());
  }
}