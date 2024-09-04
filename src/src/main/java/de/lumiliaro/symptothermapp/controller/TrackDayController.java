package de.lumiliaro.symptothermapp.controller;

import java.net.URI;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import de.lumiliaro.symptothermapp.dto.TrackDayDto;
import de.lumiliaro.symptothermapp.model.TrackDay;
import de.lumiliaro.symptothermapp.service.TrackDayService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/track-days")
@Tag(name = "TrackDay", description = "Operations related to TrackDay management")
@RequiredArgsConstructor
public class TrackDayController {

    private final TrackDayService service;

    @GetMapping
    public final ResponseEntity<Page<TrackDay>> getTrackDays(Pageable pageable) {
        Page<TrackDay> page = service.findAllPageable(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public final ResponseEntity<TrackDay> getTrackDay(@PathVariable Long id) {
        return ResponseEntity.ok(service.findOne(id));
    }

    @GetMapping("/month/{month}/{year}")
    public final List<TrackDay> getTrackDaysByMonthAndYear(@PathVariable int month, @PathVariable int year) {
        return service.findAllByMonth(month, year);
    }

    @GetMapping("/date/{day}")
    public final ResponseEntity<TrackDay> getTrackDayByDate(
            @PathVariable("day") @DateTimeFormat(pattern = "yyyy-MM-dd") Date day) {
        return ResponseEntity.ok(service.getTrackDayRepository().findByDay(day));
    }

    @PostMapping
    public final ResponseEntity<Void> storeTrackDay(@Valid @RequestBody TrackDayDto request,
            UriComponentsBuilder ucb) {
        TrackDay savedTrackDay = service.save(request);
        URI location = ucb
                .path("api/track-days/{id}")
                .buildAndExpand(savedTrackDay.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public final ResponseEntity<Void> updateTrackDay(@PathVariable Long id,
            @Valid @RequestBody TrackDayDto trackDayDto) {
        service.update(id, trackDayDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public final ResponseEntity<Void> deleteTrackDay(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
