package de.lumiliaro.symptothermapp.controller;

import de.lumiliaro.symptothermapp.dto.TrackDayDto;
import de.lumiliaro.symptothermapp.model.TrackDay;
import de.lumiliaro.symptothermapp.service.TrackDayService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/track-days")
@Tag(name = "TrackDay", description = "Operations related to TrackDay management")
@AllArgsConstructor
public class TrackDayController {

    private final TrackDayService service;


    @GetMapping
    private ResponseEntity<List<TrackDay>> getTrackDays(Pageable pageable) {
        Page<TrackDay> page = service.repository.findAll(PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                pageable.getSortOr(Sort.by(Sort.Direction.ASC, "amount"))));

        return ResponseEntity.ok(page.getContent());
    }

    @GetMapping("/{id}")
    private ResponseEntity<TrackDay> getTrackDay(@PathVariable Long id) {
        Optional<TrackDay> day = service.repository.findById(id);
        return day.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/month/{month}")
    private List<TrackDay> getTrackDaysByMonth(@Valid @PathVariable int month) {
        return service.findAllByMonth(month);
    }

    @GetMapping("/date/{trackDay}")
    private TrackDay getTrackDayByDate(@PathVariable("trackDay") @DateTimeFormat(pattern = "yyyy-MM-dd") Date trackDay) {
        return service.repository.findByTrackDay(trackDay);
    }

    @PostMapping
    private ResponseEntity<?> storeTrackDay(@Valid @RequestBody TrackDayDto trackDayDto, UriComponentsBuilder ucb) {
        try {
            TrackDay savedTrackDay = service.save(trackDayDto);
            URI location = ucb
                    .path("api/days/{id}")
                    .buildAndExpand(savedTrackDay.getId())
                    .toUri();
            return ResponseEntity.created(location).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    private ResponseEntity<?> updateTrackDay(@PathVariable Long id, @Valid @RequestBody TrackDayDto trackDayDto) {
        if (!service.update(id, trackDayDto)) {
            return ResponseEntity.notFound().build();
        }

        service.update(id, trackDayDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Void> deleteTrackDay(@PathVariable Long id) {
        if (!service.delete(id)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}
