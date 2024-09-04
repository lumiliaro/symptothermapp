package de.lumiliaro.symptothermapp.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import de.lumiliaro.symptothermapp.dto.CyclusStatisticDto;
import de.lumiliaro.symptothermapp.dto.TrackDayDto;
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
    private final String resource = "TrackDay";

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
            throw new ItemNotFoundException(id, resource);
        }

        return trackDay.get();
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
            throw new ItemNotFoundException(id, resource);
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
            throw new ItemNotFoundException(id, resource);
        }

        if (trackday.get().getBleeding() != null) {
            cyclusService.deleteByDate(trackday.get().getDay());
        }

        trackDayRepository.deleteById(id);
    }

    public List<CyclusStatisticDto> getCyclusData(Date cyclusStartDate) {
        List<TrackDay> trackDays = trackDayRepository.findTop30ByDayGreaterThanEqualOrderByDayAsc(cyclusStartDate);
        List<CyclusStatisticDto> response = new ArrayList<>();
        SimpleDateFormat dateFormatterTrackDay = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dateFormatterResponse = new SimpleDateFormat("dd");

        for (int day = 0; day < 30; day++) {
            Date date = DateUtils.addDays(cyclusStartDate, day);

            Optional<TrackDay> trackDayOpt = trackDays.stream().filter(
                    trackDay -> dateFormatterTrackDay.format(trackDay.getDay())
                            .equals(dateFormatterTrackDay.format(date)))
                    .findFirst();

            if (trackDayOpt.isPresent()) {
                // Check if this day is a new cyclus
                if (day != 0 && cyclusService.findByDate(trackDayOpt.get().getDay()) != null) {
                    break;
                }
                TrackDay trackDay = trackDayOpt.get();
                response.add(new CyclusStatisticDto(dateFormatterResponse.format(
                        trackDay.getDay())
                        + (trackDay.getCervicalMucus() != null ? " " + trackDay.getCervicalMucus().getValue() : ""),
                        trackDay.getTemperature(),
                        trackDay.getCervicalMucus() != null ? trackDay.getCervicalMucus().getValue() : null,
                        trackDay.getBleeding() != null,
                        trackDay.getCreatedAt(),
                        trackDay.getUpdatedAt()));
            } else {
                response.add(new CyclusStatisticDto(dateFormatterResponse.format(date), null, null, false,
                        null, null));
            }
        }

        return response;
    }
}
