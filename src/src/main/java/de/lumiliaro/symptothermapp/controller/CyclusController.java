package de.lumiliaro.symptothermapp.controller;

import de.lumiliaro.symptothermapp.dto.CyclusStatisticDto;
import de.lumiliaro.symptothermapp.model.Cyclus;
import de.lumiliaro.symptothermapp.service.CyclusService;
import de.lumiliaro.symptothermapp.service.TrackDayService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/cyclus")
@Tag(name = "TrackDay", description = "Operations related to Cyclus management")
@RequiredArgsConstructor
public class CyclusController {

    private final CyclusService service;
    private final TrackDayService trackDayService;

    @GetMapping
    public final ResponseEntity<List<Cyclus>> getAllCycli() {
        return ResponseEntity.ok(service.findAllOrderByDateDesc());
    }

    @GetMapping("/{id}")
    public final ResponseEntity<Cyclus> getOneCyclus(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/statistic/{cyclusId}")
    public ResponseEntity<List<CyclusStatisticDto>> getCyclusStatisticById(@PathVariable Long cyclusId) {
        Cyclus cyclus = service.findById(cyclusId);
        Date cyclusStartDate = cyclus.getDate();
        return ResponseEntity.ok(trackDayService.getCyclusData(cyclusStartDate));
    }

}
