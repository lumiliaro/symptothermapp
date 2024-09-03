package de.lumiliaro.symptothermapp.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import de.lumiliaro.symptothermapp.model.TrackDay;
import io.swagger.v3.oas.annotations.Hidden;

@Hidden
@Repository
public interface TrackDayRepository
        extends ListCrudRepository<TrackDay, Long>, PagingAndSortingRepository<TrackDay, Long> {

    boolean existsByDay(Date day);

    TrackDay findByDay(Date day);

    List<TrackDay> findByDayBetween(Date startDate, Date endDate);

    TrackDay findByDayAndIdNot(Date day, Long id);
}
