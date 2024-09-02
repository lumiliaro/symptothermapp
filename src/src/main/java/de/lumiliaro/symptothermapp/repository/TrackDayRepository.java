package de.lumiliaro.symptothermapp.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import de.lumiliaro.symptothermapp.model.TrackDay;
import io.swagger.v3.oas.annotations.Hidden;

@Hidden
@Repository
public interface TrackDayRepository
        extends CrudRepository<TrackDay, Long>, PagingAndSortingRepository<TrackDay, Long> {

    boolean existsByDay(Date day);

    TrackDay findByDay(Date day);

    @Query("SELECT t FROM TrackDay t WHERE t.day BETWEEN :startDate AND :endDate")
    List<TrackDay> findAllByMonth(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query("SELECT t FROM TrackDay t WHERE t.day = :day And t.id != :id")
    TrackDay findByDayWithOtherId(Date day, Long id);
}
