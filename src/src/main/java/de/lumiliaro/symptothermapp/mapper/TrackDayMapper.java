package de.lumiliaro.symptothermapp.mapper;

import de.lumiliaro.symptothermapp.dto.TrackDayDto;
import de.lumiliaro.symptothermapp.model.TrackDay;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface TrackDayMapper {

    TrackDayDto toDto(TrackDay trackDay);

    @Mapping(target = "id", ignore = true)
    TrackDay fromDto(TrackDayDto trackDayDto);
}
