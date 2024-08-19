package de.lumiliaro.symptothermapp.service;

import de.lumiliaro.symptothermapp.dto.TrackDayDto;
import de.lumiliaro.symptothermapp.model.TrackDay;
import de.lumiliaro.symptothermapp.repository.TrackDayRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Data
@Service
@AllArgsConstructor
public class TrackDayService {
    public final TrackDayRepository repository;

    public List<TrackDay> findAllByMonth(int month) {
        return repository.findAllByMonth(++month);
    }

    public TrackDay save(TrackDayDto trackDayDto) {
        Date trackDayDate = trackDayDto.getTrackDay();

        // Überprüfen, ob ein TrackDay mit diesem Datum bereits existiert
        if (repository.existsByTrackDay(trackDayDate)) {
            throw new IllegalArgumentException("Ein Datensatz mit diesem Datum existiert bereits.");
        }

        TrackDay trackDay = this.mapDtoToTrackDay(trackDayDto);
        return repository.save(trackDay);
    }

    public boolean update(Long id, TrackDayDto trackDayDto) {
        Optional<TrackDay> foundTrackDay = repository.findById(id);

        if (foundTrackDay.isEmpty()) {
            return false;
        }

        TrackDay trackDay = this.mapDtoToTrackDay(trackDayDto);
        trackDay.setId(id);
        repository.save(trackDay);

        return true;
    }

    public boolean delete(Long id) {
        Optional<TrackDay> trackDay = repository.findById(id);

        if (trackDay.isEmpty()) {
            return false;
        }

        repository.deleteById(id);
        return true;
    }

    public TrackDay mapDtoToTrackDay(TrackDayDto dto) {
        TrackDay trackDay = new TrackDay();
        trackDay.setTemperature(dto.getTemperature());
        trackDay.setTrackDay(dto.getTrackDay());
        trackDay.setBleeding(dto.getBleeding());
        trackDay.setCervicalMucus(dto.getCervicalMucus());
        trackDay.setCervixOpeningState(dto.getCervixOpeningState());
        trackDay.setCervixHeightPosition(dto.getCervixHeightPosition());
        trackDay.setCervixTexture(dto.getCervixTexture());
        trackDay.setHadSex(dto.getHadSex());
        trackDay.setWithContraceptives(dto.getWithContraceptives());
        trackDay.setDisturbances(dto.getDisturbances());
        trackDay.setOtherDisturbanceNotes(dto.getOtherDisturbanceNotes());
        trackDay.setNotes(dto.getNotes());
        return trackDay;
    }
}
