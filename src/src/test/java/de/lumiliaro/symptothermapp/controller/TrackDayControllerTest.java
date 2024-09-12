package de.lumiliaro.symptothermapp.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.lumiliaro.symptothermapp.dto.TrackDayDto;
import de.lumiliaro.symptothermapp.dto.TrackDayMinMaxTemperatureDto;
import de.lumiliaro.symptothermapp.model.TrackDay;
import de.lumiliaro.symptothermapp.service.TrackDayService;

@WebMvcTest(TrackDayController.class)
public class TrackDayControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private TrackDayService service;

        @Autowired
        private ObjectMapper objectMapper;

        private TrackDay trackDay;
        private TrackDayDto trackDayDto;

        @BeforeEach
        void setUp() {
                trackDay = new TrackDay();
                trackDay.setId(1L);
                trackDayDto = new TrackDayDto(36.5f, new Date());
        }

        @Test
        void testGetTrackDays() throws Exception {
                Page<TrackDay> page = new PageImpl<>(List.of(trackDay));
                when(service.findAllPageable(any(Pageable.class))).thenReturn(page);

                mockMvc.perform(get("/api/track-days"))
                                .andExpect(status().isOk())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        }

        @Test
        void testGetTrackDay() throws Exception {
                when(service.findOne(1L)).thenReturn(trackDay);

                mockMvc.perform(get("/api/track-days/1"))
                                .andExpect(status().isOk())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        }

        @Test
        void testGetTrackDaysByMonthAndYear() throws Exception {
                when(service.findAllByMonth(5, 2023)).thenReturn(List.of(trackDay));

                mockMvc.perform(get("/api/track-days/month/5/2023"))
                                .andExpect(status().isOk())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        }

        @Test
        void testGetTrackDayByDate() throws Exception {
                Date date = new Date();
                when(service.findByDay(date)).thenReturn(trackDay);

                mockMvc.perform(get("/api/track-days/date/" + date.toInstant().toString()))
                                .andExpect(status().isOk())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        }

        @Test
        void testGetTrackDayMinMaxTemperature() throws Exception {
                TrackDayMinMaxTemperatureDto minMaxDto = new TrackDayMinMaxTemperatureDto();
                when(service.getMinAndMaxTemperature()).thenReturn(minMaxDto);

                mockMvc.perform(get("/api/track-days/min-max-temperature"))
                                .andExpect(status().isOk())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        }

        @Test
        void testCreateTrackDay() throws Exception {
                when(service.save(any(TrackDayDto.class))).thenReturn(trackDay);

                mockMvc.perform(post("/api/track-days")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(trackDayDto)))
                                .andExpect(status().isCreated())
                                .andExpect(header().exists("Location"));
        }

        @Test
        void testUpdateTrackDay() throws Exception {
                mockMvc.perform(put("/api/track-days/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(trackDayDto)))
                                .andExpect(status().isNoContent());

                verify(service).update(eq(1L), any(TrackDayDto.class));
        }

        @Test
        void testDeleteTrackDay() throws Exception {
                mockMvc.perform(delete("/api/track-days/1"))
                                .andExpect(status().isNoContent());

                verify(service).delete(1L);
        }
}
