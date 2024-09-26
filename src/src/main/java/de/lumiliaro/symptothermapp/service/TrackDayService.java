package de.lumiliaro.symptothermapp.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import de.lumiliaro.symptothermapp.dto.CyclusStatisticDto;
import de.lumiliaro.symptothermapp.dto.TrackDayDto;
import de.lumiliaro.symptothermapp.dto.TrackDayMinMaxTemperatureDto;
import de.lumiliaro.symptothermapp.exception.ItemAlreadyExistsException;
import de.lumiliaro.symptothermapp.exception.ItemNotFoundException;
import de.lumiliaro.symptothermapp.helper.CalenderHelper;
import de.lumiliaro.symptothermapp.mapper.TrackDayMapperImpl;
import de.lumiliaro.symptothermapp.model.Cyclus;
import de.lumiliaro.symptothermapp.model.TrackDay;
import de.lumiliaro.symptothermapp.repository.TrackDayRepository;
import lombok.Data;

@Service
@Data
public class TrackDayService {
    private final TrackDayRepository trackDayRepository;
    private final CyclusService cyclusService;
    private final CyclusStatisticService cyclusStatisticService;
    private final String RESOURCE = "TrackDay";

    public Page<TrackDay> findAllPageable(Pageable pageable) {
        Page<TrackDay> page = trackDayRepository.findAll(PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                pageable.getSortOr(Sort.by(Sort.Direction.DESC, "day"))));

        return page;
    }

    public List<TrackDay> findAllByMonth(int month, int year) {
        CalenderHelper calender = new CalenderHelper(month, year);
        return trackDayRepository.findByDayBetween(calender.getStartDate(), calender.getEndDate());
    }

    public TrackDay findOne(Long id) throws ItemNotFoundException {
        Optional<TrackDay> trackDay = trackDayRepository.findById(id);

        if (trackDay.isEmpty()) {
            throw new ItemNotFoundException(id, RESOURCE);
        }

        return trackDay.get();
    }

    public TrackDay findByDay(Date day) {
        return trackDayRepository.findByDay(day);
    }

    public TrackDay save(TrackDayDto trackDayDto) throws ItemAlreadyExistsException {
        Date trackDayDate = trackDayDto.getDay();

        if (trackDayRepository.existsByDay(trackDayDate)) {
            throw new ItemAlreadyExistsException("Ein Datensatz mit diesem Datum existiert bereits.");
        }

        TrackDay trackDay = new TrackDayMapperImpl().fromDto(trackDayDto);

        if (!trackDay.getHadSex()) {
            trackDay.setWithContraceptives(false);
        }

        if (trackDay.getBleeding() != null) {
            cyclusService.save(new Cyclus(trackDay.getDay()));
        }

        return trackDayRepository.save(trackDay);
    }

    public void update(Long id, TrackDayDto trackDayDto) throws ItemNotFoundException, ItemAlreadyExistsException {
        Optional<TrackDay> foundTrackDay = trackDayRepository.findById(id);

        if (foundTrackDay.isEmpty()) {
            throw new ItemNotFoundException(id, RESOURCE);
        }

        TrackDay trackDayWithSameDay = trackDayRepository.findByDayAndIdNot(trackDayDto.getDay(),
                foundTrackDay.get().getId());

        if (trackDayWithSameDay != null) {
            throw new ItemAlreadyExistsException("Ein Datensatz mit diesem Datum existiert bereits.");
        }

        TrackDay trackDay = new TrackDayMapperImpl().fromDto(trackDayDto);
        trackDay.setId(id);

        if (!trackDay.getHadSex()) {
            trackDay.setWithContraceptives(false);
        }

        if (foundTrackDay.get().getBleeding() != null && trackDayDto.getBleeding() == null) {
            cyclusService.deleteByDate(foundTrackDay.get().getDay());
        } else if (foundTrackDay.get().getBleeding() == null && trackDayDto.getBleeding() != null) {
            cyclusService.save(new Cyclus(foundTrackDay.get().getDay()));
        }

        trackDayRepository.save(trackDay);
    }

    public void delete(Long id) throws ItemNotFoundException {
        Optional<TrackDay> trackday = trackDayRepository.findById(id);

        if (trackday.isEmpty()) {
            throw new ItemNotFoundException(id, RESOURCE);
        }

        if (trackday.get().getBleeding() != null) {
            cyclusService.deleteByDate(trackday.get().getDay());
        }

        trackDayRepository.deleteById(id);
    }

    public List<CyclusStatisticDto> getCyclusData(Date cyclusStartDate) {
        List<TrackDay> trackDaysTop60 = trackDayRepository.findTop60ByDayGreaterThanEqualOrderByDayAsc(cyclusStartDate);
        List<TrackDay> trackDays = new ArrayList<TrackDay>();
        int index = 0;
        for (TrackDay trackDay : trackDaysTop60) {
            if (cyclusService.findByDate(trackDay.getDay()) != null && index != 0) {
                break;
            }
            trackDays.add(trackDay);
            index++;
        }

        return cyclusStatisticService.getCyclusData(cyclusStartDate, trackDays);
    }

    public TrackDayMinMaxTemperatureDto getMinAndMaxTemperature() {
        Float minTemperature = trackDayRepository.findMinTemperature();
        Float maxTemperature = trackDayRepository.findMaxTemperature();

        return new TrackDayMinMaxTemperatureDto(minTemperature, maxTemperature);
    }
}
