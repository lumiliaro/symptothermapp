package de.lumiliaro.symptothermapp.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import de.lumiliaro.symptothermapp.dto.TrackDayDto;
import de.lumiliaro.symptothermapp.dto.TrackDayLineChartStatisticDto;
import de.lumiliaro.symptothermapp.model.TrackDay;
import de.lumiliaro.symptothermapp.service.TrackDayService;

@WebMvcTest(TrackDayController.class)
class TrackDayControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @Mock
        private TrackDayService service;

        @InjectMocks
        private TrackDayController controller;

        @BeforeEach
        void setUp() {
                MockitoAnnotations.openMocks(this);
        }

        @Test
        void testGetTrackDays() throws Exception {
                Pageable pageable = PageRequest.of(0, 10);
                TrackDay trackDay = new TrackDay(1L, new Date(), null, null, null, null, null, null, null, null, null,
                                null,
                                null);
                Page<TrackDay> page = new PageImpl<>(Arrays.asList(trackDay), pageable, 1);

                when(service.findAllPageable(any(Pageable.class))).thenReturn(page);

                mockMvc.perform(MockMvcRequestBuilders.get("/api/track-days")
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].id").value(trackDay.getId()))
                                .andDo(print());
        }

        @Test
        void testGetTrackDay() throws Exception {
                TrackDay trackDay = new TrackDay(1L, new Date(), null, null, null, null, null, null, null, null, null,
                                null,
                                null);

                when(service.findOne(anyLong())).thenReturn(trackDay);

                mockMvc.perform(MockMvcRequestBuilders.get("/api/track-days/{id}", 1L)
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(trackDay.getId()))
                                .andDo(print());
        }

        @Test
        void testGetTrackDaysByMonthAndYear() throws Exception {
                TrackDay trackDay = new TrackDay(1L, new Date(), null, null, null, null, null, null, null, null, null,
                                null,
                                null);
                List<TrackDay> trackDays = Arrays.asList(trackDay);

                when(service.findAllByMonth(eq(9), eq(2024))).thenReturn(trackDays);

                mockMvc.perform(MockMvcRequestBuilders.get("/api/track-days/month/{month}/{year}", 9, 2024)
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(trackDay.getId()))
                                .andDo(print());
        }

        @Test
        void testGetTrackDayByDate() throws Exception {
                TrackDay trackDay = new TrackDay(1L, new Date(), null, null, null, null, null, null, null, null, null,
                                null,
                                null);

                when(service.getTrackDayRepository().findByDay(any(Date.class))).thenReturn(trackDay);

                mockMvc.perform(MockMvcRequestBuilders.get("/api/track-days/date/{trackDay}", "2024-09-01")
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(trackDay.getId()))
                                .andDo(print());
        }

        @Test
        void testGetTrackDaysStatisticByMonthAndYear() throws Exception {
                TrackDayLineChartStatisticDto statisticDto = new TrackDayLineChartStatisticDto("01", 36.5f, "A");
                List<TrackDayLineChartStatisticDto> statistics = Arrays.asList(statisticDto);

                when(service.getTrackDaysForMonthStatistic(eq(9), eq(2024))).thenReturn(statistics);

                mockMvc.perform(MockMvcRequestBuilders.get("/api/track-days/statistic/{month}/{year}", 9, 2024)
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andExpect(MockMvcResultMatchers.jsonPath("$[0].temperature")
                                                .value(statisticDto.getTemperature()))
                                .andDo(print());
        }

        @Test
        void testStoreTrackDay() throws Exception {
                TrackDayDto trackDayDto = new TrackDayDto(36.5f, new Date(), null, null, null, null, null, null, null,
                                null);

                TrackDay trackDay = new TrackDay(1L, new Date(), null, null, null, null, null, null, null, null, null,
                                null,
                                null);

                when(service.save(any(TrackDayDto.class))).thenReturn(trackDay);

                mockMvc.perform(MockMvcRequestBuilders.post("/api/track-days")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"day\":\"2024-09-01\",\"temperature\":36.5}"))
                                .andExpect(MockMvcResultMatchers.status().isCreated())
                                .andExpect(MockMvcResultMatchers.header().string("Location",
                                                "http://localhost/api/track-days/1"))
                                .andDo(print());
        }

        @Test
        void testUpdateTrackDay() throws Exception {
                mockMvc.perform(MockMvcRequestBuilders.put("/api/track-days/{id}", 1L)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"day\":\"2024-09-01\",\"temperature\":36.5}"))
                                .andExpect(MockMvcResultMatchers.status().isNoContent())
                                .andDo(print());
        }

        @Test
        void testDeleteTrackDay() throws Exception {
                mockMvc.perform(MockMvcRequestBuilders.delete("/api/track-days/{id}", 1L)
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.status().isNoContent())
                                .andDo(print());
        }
}
