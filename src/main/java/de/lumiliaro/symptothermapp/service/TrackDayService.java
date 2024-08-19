package de.lumiliaro.symptothermapp.service;

import de.lumiliaro.symptothermapp.dto.TrackDayDto;
import de.lumiliaro.symptothermapp.dto.TrackDayLineChartStatisticDto;
import de.lumiliaro.symptothermapp.model.TrackDay;
import de.lumiliaro.symptothermapp.repository.TrackDayRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Data
@Service
@AllArgsConstructor
public class TrackDayService {
    public final TrackDayRepository repository;

    public List<TrackDay> findAllByMonth(int month, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        Date startDate = calendar.getTime();
        calendar.set(Calendar.DAY_OF_MONTH, maxDay);
        Date endDate = calendar.getTime();

        return repository.findAllByMonth(startDate, endDate);
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

    public List<TrackDayLineChartStatisticDto> getTrackDaysForMonthStatistic(int month, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        Date startDate = calendar.getTime();
        calendar.set(Calendar.DAY_OF_MONTH, maxDay);
        Date endDate = calendar.getTime();

        List<TrackDay> trackDays = repository.findAllByMonth(startDate, endDate);

        List<TrackDayLineChartStatisticDto> response = new ArrayList<>();

        SimpleDateFormat dateFormatterTrackDay = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dateFormatterResponse = new SimpleDateFormat("dd.MM.");

        for (int day = 1; day <= maxDay; day++) {
            calendar.set(Calendar.DAY_OF_MONTH, day);
            Date currentDate = calendar.getTime();

            Optional<TrackDay> trackDayOpt = trackDays.stream()
                    .filter(trackDay -> dateFormatterTrackDay.format(trackDay.getTrackDay()).equals(dateFormatterTrackDay.format(currentDate)))
                    .findFirst();

            if (trackDayOpt.isPresent()) {
                TrackDay trackDay = trackDayOpt.get();
                response.add(new TrackDayLineChartStatisticDto(dateFormatterResponse.format(trackDay.getTrackDay()), trackDay.getTemperature()));
            } else {
                response.add(new TrackDayLineChartStatisticDto(dateFormatterResponse.format(currentDate), null));
            }
        }

        return response;
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
