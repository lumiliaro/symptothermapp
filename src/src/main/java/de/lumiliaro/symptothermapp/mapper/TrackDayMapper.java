package de.lumiliaro.symptothermapp.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import de.lumiliaro.symptothermapp.dto.TrackDayDto;
import de.lumiliaro.symptothermapp.model.TrackDay;

@Mapper
public interface TrackDayMapper {

    TrackDayDto toDto(TrackDay trackDay);

    @Mapping(target = "id", ignore = true)
    TrackDay fromDto(TrackDayDto trackDayDto);

    List<TrackDayDto> toDtoList(List<TrackDay> trackDayList);

    @Mapping(target = "id", ignore = true)
    List<TrackDay> fromDtoList(List<TrackDayDto> trackDayList);
}
