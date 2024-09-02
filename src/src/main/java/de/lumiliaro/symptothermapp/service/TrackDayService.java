package de.lumiliaro.symptothermapp.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import de.lumiliaro.symptothermapp.dto.TrackDayDto;
import de.lumiliaro.symptothermapp.dto.TrackDayLineChartStatisticDto;
import de.lumiliaro.symptothermapp.exception.ItemAlreadyExistsException;
import de.lumiliaro.symptothermapp.exception.ItemNotFoundException;
import de.lumiliaro.symptothermapp.mapper.TrackDayMapperImpl;
import de.lumiliaro.symptothermapp.model.TrackDay;
import de.lumiliaro.symptothermapp.repository.TrackDayRepository;
import lombok.Data;

@Service
@Data
public class TrackDayService {
    private final TrackDayRepository repository;
    private final String resource = "TrackDay";

    public Page<TrackDay> findAllPageable(Pageable pageable) {
        Page<TrackDay> page = repository.findAll(PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                pageable.getSortOr(Sort.by(Sort.Direction.DESC, "day"))));

        return page;
    }

    public List<TrackDay> findAllByMonth(int month, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        Date startDate = calendar.getTime();
        calendar.set(Calendar.DAY_OF_MONTH, maxDay);
        Date endDate = calendar.getTime();

        return repository.findAllByMonth(startDate, endDate);
    }

    public TrackDay findOne(Long id) throws ItemNotFoundException {
        Optional<TrackDay> trackDay = repository.findById(id);

        if (trackDay.isEmpty()) {
            throw new ItemNotFoundException(id, resource);
        }

        return trackDay.get();
    }

    public TrackDay save(TrackDayDto trackDayDto) throws ItemAlreadyExistsException {
        Date trackDayDate = trackDayDto.getDay();

        if (repository.existsByDay(trackDayDate)) {
            throw new ItemAlreadyExistsException("Ein Datensatz mit diesem Datum existiert bereits.");
        }

        TrackDay trackDay = new TrackDayMapperImpl().fromDto(trackDayDto);

        if (!trackDay.getHadSex()) {
            trackDay.setWithContraceptives(false);
        }

        return repository.save(trackDay);
    }

    public void update(Long id, TrackDayDto trackDayDto) throws ItemNotFoundException, ItemAlreadyExistsException {
        Optional<TrackDay> foundTrackDay = repository.findById(id);

        if (foundTrackDay.isEmpty()) {
            throw new ItemNotFoundException(id, resource);
        }

        TrackDay trackDayWithSameDay = repository.findByDayWithOtherId(trackDayDto.getDay(),
                foundTrackDay.get().getId());

        if (trackDayWithSameDay != null) {
            throw new ItemAlreadyExistsException("Ein Datensatz mit diesem Datum existiert bereits.");
        }

        TrackDay trackDay = new TrackDayMapperImpl().fromDto(trackDayDto);
        trackDay.setId(id);

        if (!trackDay.getHadSex()) {
            trackDay.setWithContraceptives(false);
        }

        repository.save(trackDay);
    }

    public void delete(Long id) throws ItemNotFoundException {
        if (repository.findById(id).isEmpty()) {
            throw new ItemNotFoundException(id, resource);
        }

        repository.deleteById(id);
    }

    public List<TrackDayLineChartStatisticDto> getTrackDaysForMonthStatistic(int month, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        Date startDate = calendar.getTime();
        calendar.set(Calendar.DAY_OF_MONTH, maxDay);
        Date endDate = calendar.getTime();

        List<TrackDay> trackDays = repository.findAllByMonth(startDate, endDate);

        List<TrackDayLineChartStatisticDto> response = new ArrayList<>();

        SimpleDateFormat dateFormatterTrackDay = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dateFormatterResponse = new SimpleDateFormat("dd");

        for (int day = 1; day <= maxDay; day++) {
            calendar.set(Calendar.DAY_OF_MONTH, day);
            Date currentDate = calendar.getTime();

            Optional<TrackDay> trackDayOpt = trackDays.stream().filter(
                    trackDay -> dateFormatterTrackDay.format(trackDay.getDay())
                            .equals(dateFormatterTrackDay.format(currentDate)))
                    .findFirst();

            if (trackDayOpt.isPresent()) {
                TrackDay trackDay = trackDayOpt.get();
                response.add(new TrackDayLineChartStatisticDto(dateFormatterResponse.format(
                        trackDay.getDay())
                        + (trackDay.getCervicalMucus() != null ? " " + trackDay.getCervicalMucus().getValue() : ""),
                        trackDay.getTemperature(),
                        trackDay.getCervicalMucus() != null ? trackDay.getCervicalMucus().getValue() : null));
            } else {
                response.add(new TrackDayLineChartStatisticDto(dateFormatterResponse.format(currentDate), null, null));
            }
        }

        return response;
    }

    // public TrackDay mapDtoToTrackDay(TrackDayDto dto) {
    // TrackDay trackDay = new TrackDay();
    // trackDay.setTemperature(dto.getTemperature());
    // trackDay.setDay(dto.getDay());
    // trackDay.setBleeding(dto.getBleeding());
    // trackDay.setCervicalMucus(dto.getCervicalMucus());
    // trackDay.setCervixOpeningState(dto.getCervixOpeningState());
    // trackDay.setCervixHeightPosition(dto.getCervixHeightPosition());
    // trackDay.setCervixTexture(dto.getCervixTexture());
    // trackDay.setHadSex(dto.getHadSex());
    // if (dto.getHadSex()) {
    // trackDay.setWithContraceptives(dto.getWithContraceptives());
    // }
    // trackDay.setDisturbances(dto.getDisturbances());
    // trackDay.setOtherDisturbanceNotes(dto.getOtherDisturbanceNotes());
    // trackDay.setNotes(dto.getNotes());
    // return trackDay;
    // }
}
