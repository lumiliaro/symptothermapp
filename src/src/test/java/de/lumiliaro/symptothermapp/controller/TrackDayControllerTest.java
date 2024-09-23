package de.lumiliaro.symptothermapp.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
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
import de.lumiliaro.symptothermapp.mapper.TrackDayMapperImpl;
import de.lumiliaro.symptothermapp.model.TrackDay;
import de.lumiliaro.symptothermapp.repository.TrackDayRepository;
import de.lumiliaro.symptothermapp.service.TrackDayService;

@WebMvcTest(TrackDayController.class)
public class TrackDayControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private TrackDayService service;

        @MockBean
        private TrackDayRepository repository;

        @Autowired
        private ObjectMapper objectMapper;

        private TrackDay trackDay;
        private TrackDayDto trackDayDto;
        private List<TrackDay> listTrackDays;

        @BeforeEach
        void setUp() {
                MockitoAnnotations.openMocks(this);
                trackDay = mock(TrackDay.class);
                trackDayDto = mock(TrackDayDto.class);
                listTrackDays = List.of(mock(TrackDay.class), mock(TrackDay.class));
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
                when(service.findAllByMonth(5, 2023)).thenReturn(listTrackDays);

                mockMvc.perform(get("/api/track-days/month/5/2023"))
                                .andExpect(status().isOk())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(content().json(objectMapper.writeValueAsString(listTrackDays)));
        }

        @Test
        void testGetTrackDayByDate() throws Exception {
                Date date = new Date();
                when(service.findByDay(date)).thenReturn(trackDay);

                LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;

                mockMvc.perform(get("/api/track-days/date/" + localDate.format(formatter)))
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
                // Given
                Calendar calender = Calendar.getInstance();
                calender.set(2023, 1, 1);
                TrackDay savedTrackDay = new TrackDay();
                savedTrackDay.setId(1L);
                savedTrackDay.setTemperature(32.1F);
                savedTrackDay.setDay(calender.getTime());
                TrackDayMapperImpl mapper = new TrackDayMapperImpl();
                TrackDayDto createtrackDayDto = mapper.toDto(savedTrackDay);

                // When
                when(service.save(createtrackDayDto)).thenReturn(savedTrackDay);

                // Then
                mockMvc.perform(post("/api/track-days")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(createtrackDayDto)))
                                .andExpect(status().isCreated())
                                .andExpect(header().exists("Location"));
        }

        @Test
        void testUpdateTrackDay() throws Exception {
                // When
                when(repository.findById(1L)).thenReturn(Optional.of(trackDay));

                // Then
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
