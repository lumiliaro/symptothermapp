package de.lumiliaro.symptothermapp.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.lumiliaro.symptothermapp.dto.CyclusStatisticDto;
import de.lumiliaro.symptothermapp.model.TrackDay;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CyclusStatisticService {
    private final CyclusService cyclusService;
    private boolean isFertileSet = false;

    private boolean checkIfLastNineAreValidForFertileCheck(List<CyclusStatisticDto> cyclusData) {
        int size = cyclusData.size();

        if (size < 9) {
            return false;
        }

        List<CyclusStatisticDto> lastNine = cyclusData.subList(size - 9, size);
        return lastNine.stream().allMatch(dto -> dto.getTemperature() != null);
    }

    private List<CyclusStatisticDto> getLastThreeCyclusStatisticDtos(List<CyclusStatisticDto> cyclusData) {
        int size = cyclusData.size();
        if (size < 3) {
            throw new IllegalArgumentException("Die Liste muss mindestens 3 Elemente enthalten.");
        }

        return cyclusData.subList(size - 3, size);
    }

    private boolean checkIfTrackDayIsFertile(List<CyclusStatisticDto> lastThreeTrackDays,
            List<CyclusStatisticDto> cyclusData) {
        int size = cyclusData.size();
        if (size < 9) {
            throw new IllegalArgumentException("Die Liste muss mindestens 9 Elemente enthalten.");
        }

        List<CyclusStatisticDto> firstSix = cyclusData.subList(size - 9, size - 3);

        if (firstSix.stream().anyMatch(dto -> dto.getTemperature() == null) ||
                lastThreeTrackDays.stream().anyMatch(dto -> dto.getTemperature() == null)) {
            return false;
        }

        return firstSix.stream()
                .allMatch(firstSixDto -> lastThreeTrackDays.stream()
                        .allMatch(lastThreeDto -> firstSixDto.getTemperature() < lastThreeDto.getTemperature()));
    }

    private void setFertileForLastThree(List<CyclusStatisticDto> cyclusData) {
        List<CyclusStatisticDto> lastThree = getLastThreeCyclusStatisticDtos(cyclusData);

        for (CyclusStatisticDto dto : lastThree) {
            dto.setFertile(true);
        }
    }

    public List<CyclusStatisticDto> getCyclusData(Date cyclusStartDate, List<TrackDay> trackDays) {
        List<CyclusStatisticDto> response = new ArrayList<>();
        SimpleDateFormat dateFormatterTrackDay = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dateFormatterResponse = new SimpleDateFormat("dd.MM");

        for (int day = 0; day < 30; day++) {
            Date date = DateUtils.addDays(cyclusStartDate, day);

            Optional<TrackDay> trackDayOpt = trackDays.stream().filter(
                    trackDay -> dateFormatterTrackDay.format(trackDay.getDay())
                            .equals(dateFormatterTrackDay.format(date)))
                    .findFirst();

            // Set fertile days
            if (!isFertileSet && checkIfLastNineAreValidForFertileCheck(response)) {
                List<CyclusStatisticDto> lastThree = getLastThreeCyclusStatisticDtos(response);
                if (checkIfTrackDayIsFertile(lastThree, response)) {
                    setFertileForLastThree(response);
                    isFertileSet = true;
                }
            }

            if (trackDayOpt.isPresent()) {
                // Check if this day is a new cyclus
                if (day != 0 && cyclusService.findByDate(trackDayOpt.get().getDay()) != null) {
                    break;
                }

                TrackDay trackDay = trackDayOpt.get();
                CyclusStatisticDto cyclusStatisticDto = new CyclusStatisticDto();
                cyclusStatisticDto.setCyclusDay(String.valueOf(day + 1));
                cyclusStatisticDto.setDate(dateFormatterResponse.format(trackDay.getDay()));
                cyclusStatisticDto.setTemperature(trackDay.getTemperature());
                cyclusStatisticDto.setCervicalMucus(
                        trackDay.getCervicalMucus() != null ? trackDay.getCervicalMucus().getValue() : null);
                cyclusStatisticDto
                        .setBleeding(trackDay.getBleeding() != null ? trackDay.getBleeding().getValue() : null);
                cyclusStatisticDto.setFertile(false);
                cyclusStatisticDto.setCreatedAt(trackDay.getCreatedAt());
                cyclusStatisticDto.setUpdatedAt(trackDay.getUpdatedAt());
                response.add(cyclusStatisticDto);
            } else {
                CyclusStatisticDto cyclusStatisticDto = new CyclusStatisticDto();
                cyclusStatisticDto.setCyclusDay(String.valueOf(day + 1));
                cyclusStatisticDto.setDate(dateFormatterResponse.format(date));
                response.add(cyclusStatisticDto);
            }
        }

        return response;
    }
}
