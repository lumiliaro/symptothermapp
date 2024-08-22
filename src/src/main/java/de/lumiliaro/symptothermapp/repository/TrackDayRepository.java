package de.lumiliaro.symptothermapp.repository;

import de.lumiliaro.symptothermapp.model.TrackDay;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Hidden
@Repository
public interface TrackDayRepository extends CrudRepository<TrackDay, Long>, PagingAndSortingRepository<TrackDay, Long> {
    boolean existsByTrackDay(Date trackDay);

    TrackDay findByTrackDay(Date trackDay);

    /*
    @Query("SELECT t FROM TrackDay t WHERE MONTH(t.trackDay) = :month")
    List<TrackDay> findAllByMonth(@Param("month") int month);
    */
    
    @Query("SELECT t FROM TrackDay t WHERE t.trackDay BETWEEN :startDate AND :endDate")
    List<TrackDay> findAllByMonth(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
