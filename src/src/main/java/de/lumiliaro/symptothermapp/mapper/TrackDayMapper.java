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
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    TrackDay fromDto(TrackDayDto trackDayDto);

    List<TrackDayDto> toDtoList(List<TrackDay> trackDayList);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "created_at", ignore = true)
    @Mapping(target = "updated_at", ignore = true)
    List<TrackDay> fromDtoList(List<TrackDayDto> trackDayList);
}
