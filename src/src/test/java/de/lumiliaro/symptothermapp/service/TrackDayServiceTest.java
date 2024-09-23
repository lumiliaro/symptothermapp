package de.lumiliaro.symptothermapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import de.lumiliaro.symptothermapp.dto.CyclusStatisticDto;
import de.lumiliaro.symptothermapp.dto.TrackDayDto;
import de.lumiliaro.symptothermapp.dto.TrackDayMinMaxTemperatureDto;
import de.lumiliaro.symptothermapp.enums.BleedingEnum;
import de.lumiliaro.symptothermapp.exception.ItemAlreadyExistsException;
import de.lumiliaro.symptothermapp.exception.ItemNotFoundException;
import de.lumiliaro.symptothermapp.helper.CalenderHelper;
import de.lumiliaro.symptothermapp.mapper.TrackDayMapperImpl;
import de.lumiliaro.symptothermapp.model.Cyclus;
import de.lumiliaro.symptothermapp.model.TrackDay;
import de.lumiliaro.symptothermapp.repository.TrackDayRepository;

class TrackDayServiceTest {

    @Mock
    private TrackDayRepository repository;

    @Mock
    private CyclusService cyclusService;

    @Mock
    private CyclusStatisticService cyclusStatisticService;

    @InjectMocks
    private TrackDayService trackDayService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAllPageable() {
        // Given
        Pageable pageable = PageRequest.of(
                0,
                10,
                Sort.by(Sort.Direction.DESC, "day"));
        List<TrackDay> trackDays = Arrays.asList(
                mock(TrackDay.class),
                mock(TrackDay.class));
        Page<TrackDay> page = new PageImpl<>(trackDays, pageable, trackDays.size());

        when(repository.findAll(pageable)).thenReturn(page);

        // When
        Page<TrackDay> result = trackDayService.findAllPageable(pageable);

        // Then
        assertEquals(trackDays.size(), result.getTotalElements());
        verify(repository, times(1)).findAll(pageable);
    }

    @Test
    void testFindAllByMonth() {
        // Given
        int month = 5;
        int year = 2023;
        CalenderHelper calender = new CalenderHelper(month, year);

        TrackDay trackday1 = new TrackDay();
        trackday1.setId(1L);
        trackday1.setDay(calender.getStartDate());
        trackday1.setHadSex(false);
        trackday1.setWithContraceptives(false);

        TrackDay trackday2 = new TrackDay();
        trackday2.setId(2L);
        trackday2.setDay(calender.getEndDate());
        trackday2.setHadSex(false);
        trackday2.setWithContraceptives(false);

        List<TrackDay> trackDays = Arrays.asList(
                trackday1,
                trackday2);

        when(repository.findByDayBetween(calender.getStartDate(), calender.getEndDate())).thenReturn(trackDays);

        // When
        List<TrackDay> result = trackDayService.findAllByMonth(month, year);

        // Then
        assertEquals(trackDays.size(), result.size());
        verify(repository, times(1)).findByDayBetween(calender.getStartDate(), calender.getEndDate());
    }

    @Test
    void testFindOne_WhenTrackDayExists() throws ItemNotFoundException {
        // Given
        Long id = 1L;
        TrackDay trackday1 = new TrackDay();
        trackday1.setId(1L);
        trackday1.setDay(new Date());

        TrackDay trackday2 = new TrackDay();
        trackday2.setId(2L);
        trackday2.setDay(new Date());
        trackday2.setHadSex(false);
        trackday2.setWithContraceptives(false);
        when(repository.findById(id)).thenReturn(Optional.of(trackday1));

        // When
        TrackDay foundTrackDay = trackDayService.findOne(id);

        // Then
        assertNotNull(foundTrackDay);
        assertEquals(id, foundTrackDay.getId());
        verify(repository, times(1)).findById(id);
    }

    @Test
    void testFindOne_WhenTrackDayDoesNotExist() {
        // Given
        Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        // When & Then
        ItemNotFoundException exception = assertThrows(ItemNotFoundException.class, () -> {
            trackDayService.findOne(id);
        });

        assertEquals("Die angefragte Resource \"TrackDay\" mit der ID \"1\" wurde nicht gefunden.",
                exception.getMessage());
        verify(repository, times(1)).findById(id);
    }

    @Test
    void testSave_WhenTrackDayDoesNotExist() throws ItemAlreadyExistsException {
        // Given
        Date trackDayDate = new Date();
        TrackDayDto trackDayDto = new TrackDayDto(36.5f, new Date());
        TrackDay trackDay = new TrackDayMapperImpl().fromDto(trackDayDto);

        when(repository.existsByDay(trackDayDate)).thenReturn(false);
        when(repository.save(any(TrackDay.class))).thenReturn(trackDay);

        // When
        TrackDay savedTrackDay = trackDayService.save(trackDayDto);

        // Then
        assertNotNull(savedTrackDay);
        assertEquals(trackDayDate, savedTrackDay.getDay());
        assertFalse(savedTrackDay.getHadSex());
        assertFalse(savedTrackDay.getWithContraceptives());
        verify(repository).existsByDay(trackDayDate);
        verify(repository).save(any(TrackDay.class));
        verify(cyclusService, never()).save(any(Cyclus.class));
    }

    @Test
    void testSave_WhenTrackDayAlreadyExists() {
        // Given
        Date trackDayDate = new Date();
        TrackDayDto trackDayDto = new TrackDayDto(36.5f, new Date());

        when(repository.existsByDay(trackDayDate)).thenReturn(true);

        // When & Then
        ItemAlreadyExistsException exception = assertThrows(ItemAlreadyExistsException.class, () -> {
            trackDayService.save(trackDayDto);
        });

        assertEquals("Ein Datensatz mit diesem Datum existiert bereits.", exception.getMessage());
        verify(repository, times(1)).existsByDay(trackDayDate);
        verify(repository, never()).save(any(TrackDay.class));
    }

    @Test
    void testSave_WhenTrackDayHasBleeding() throws ItemAlreadyExistsException {
        // Given
        Date trackDayDate = new Date();
        TrackDayDto trackDayDto = new TrackDayDto(36.5f, trackDayDate);
        trackDayDto.setBleeding(BleedingEnum.MEDIUM);

        TrackDay trackDay = new TrackDayMapperImpl().fromDto(trackDayDto);

        when(repository.existsByDay(trackDayDate)).thenReturn(false);
        when(repository.save(any(TrackDay.class))).thenReturn(trackDay);

        // When
        trackDayService.save(trackDayDto);

        // Then
        verify(cyclusService).save(argThat(cyclus -> cyclus.getDate().equals(trackDayDate)));
    }

    @Test
    void testUpdate_WhenTrackDayExistsAndNoConflict() throws ItemNotFoundException, ItemAlreadyExistsException {
        // Given
        Long id = 1L;
        Date trackDayDate = new Date();
        TrackDayDto trackDayDto = new TrackDayDto(36.5f, trackDayDate);

        TrackDay existingTrackDay = new TrackDay();
        existingTrackDay.setId(id);
        existingTrackDay.setDay(trackDayDate);
        when(repository.findById(id)).thenReturn(Optional.of(existingTrackDay));
        when(repository.findByDayAndIdNot(trackDayDate, id)).thenReturn(null);

        TrackDay updatedTrackDay = new TrackDayMapperImpl().fromDto(trackDayDto);
        updatedTrackDay.setId(id);

        // When
        trackDayService.update(id, trackDayDto);

        // Then
        verify(repository, times(1)).findById(id);
        verify(repository, times(1)).findByDayAndIdNot(trackDayDto.getDay(), id);
        verify(repository, times(1)).save(any(TrackDay.class));
    }

    @Test
    void testUpdate_WhenTrackDayNotFound() {
        // Given
        Long id = 1L;
        TrackDayDto trackDayDto = new TrackDayDto(36.5f, new Date());

        when(repository.findById(id)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ItemNotFoundException.class, () -> {
            trackDayService.update(id, trackDayDto);
        });

        verify(repository, times(1)).findById(id);
        verify(repository, never()).findByDayAndIdNot(any(), any());
        verify(repository, never()).save(any(TrackDay.class));
    }

    @Test
    void testUpdate_WhenTrackDayConflictExists() {
        // Given
        Long id = 1L;
        Date trackDayDate = new Date();
        TrackDayDto updateTrackDayDto = new TrackDayDto(36.5f, trackDayDate);

        TrackDay existingTrackDay = new TrackDay();
        existingTrackDay.setId(id);
        existingTrackDay.setDay(trackDayDate);
        existingTrackDay.setTemperature(39.0f);

        when(repository.findById(id)).thenReturn(Optional.of(existingTrackDay));

        TrackDay conflictingTrackDay = new TrackDay();
        conflictingTrackDay.setId(2L);
        conflictingTrackDay.setDay(trackDayDate);
        conflictingTrackDay.setTemperature(35.0f);
        when(repository.findByDayAndIdNot(trackDayDate, id)).thenReturn(conflictingTrackDay);

        // When & Then
        assertThrows(ItemAlreadyExistsException.class, () -> {
            trackDayService.update(id, updateTrackDayDto);
        });

        verify(repository, times(1)).findById(id);
        verify(repository, times(1)).findByDayAndIdNot(updateTrackDayDto.getDay(), id);
        verify(repository, never()).save(any(TrackDay.class));
    }

    @Test
    void testUpdate_WhenTrackDayExistsAndBleedingChanged() throws ItemNotFoundException, ItemAlreadyExistsException {
        // Given
        Long id = 1L;
        Date trackDayDate = new Date();
        TrackDayDto trackDayDto = new TrackDayDto(36.5F, trackDayDate);
        trackDayDto.setBleeding(BleedingEnum.SPOTTING_BLEEDING);

        TrackDay existingTrackDay = new TrackDay();
        existingTrackDay.setId(id);
        existingTrackDay.setDay(trackDayDate);
        existingTrackDay.setBleeding(BleedingEnum.MEDIUM);

        when(repository.findById(id)).thenReturn(Optional.of(existingTrackDay));
        when(repository.findByDayAndIdNot(trackDayDate, id)).thenReturn(null);

        // When
        trackDayService.update(id, trackDayDto);

        // Then
        verify(repository).save(any(TrackDay.class));
        verify(cyclusService).save(argThat(cyclus -> cyclus.getDate().equals(trackDayDate)));
    }

    @Test
    void testDelete_WhenTrackDayExists() throws ItemNotFoundException {
        // Given
        Long id = 1L;
        TrackDay trackDay = mock(TrackDay.class);

        when(repository.findById(id)).thenReturn(Optional.of(trackDay));

        // When
        trackDayService.delete(id);

        // Then
        verify(repository, times(1)).findById(id);
        verify(repository, times(1)).deleteById(id);
    }

    @Test
    void testDelete_WhenTrackDayNotFound() {
        // Given
        Long id = 1L;

        when(repository.findById(id)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ItemNotFoundException.class, () -> trackDayService.delete(id));
        verify(repository, times(1)).findById(id);
        verify(repository, never()).deleteById(id);
    }

    @Test
    void testDelete_WhenTrackDayExistsWithBleeding() throws ItemNotFoundException {
        // Given
        Long id = 1L;
        Date trackDayDate = new Date();
        TrackDay trackDay = new TrackDay();
        trackDay.setId(id);
        trackDay.setDay(trackDayDate);
        trackDay.setBleeding(BleedingEnum.STRONG);

        when(repository.findById(id)).thenReturn(Optional.of(trackDay));

        // When
        trackDayService.delete(id);

        // Then
        verify(repository).deleteById(id);
        verify(cyclusService).deleteByDate(trackDayDate);
    }

    // @Test
    // void testGetTrackDaysForMonthStatistic() {
    // // Given
    // int month = 5;
    // int year = 2023;

    // TrackDay trackday1 = new TrackDay();
    // trackday1.setId(1L);
    // trackday1.setDay(new Date());
    // trackday1.setBleeding(BleedingEnum.STRONG);

    // TrackDay trackday2 = new TrackDay();
    // trackday2.setId(2L);
    // trackday2.setDay(new Date());
    // trackday2.setBleeding(BleedingEnum.STRONG);

    // List<TrackDay> trackDays = Arrays.asList(
    // trackday1,
    // trackday2);

    // Calendar calendar = Calendar.getInstance();
    // calendar.set(Calendar.MONTH, month - 1);
    // calendar.set(Calendar.YEAR, year);
    // calendar.set(Calendar.DAY_OF_MONTH, 1);

    // Date startDate = calendar.getTime();
    // calendar.set(Calendar.DAY_OF_MONTH,
    // calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
    // Date endDate = calendar.getTime();

    // when(repository.findByDayBetween(startDate, endDate)).thenReturn(trackDays);

    // // When
    // List<CyclusStatisticDto> result = service.getCyclusData();

    // // Then
    // assertNotNull(result);
    // assertEquals(31, result.size());
    // }

    @Test
    void testGetCyclusData() {
        // Given
        Date cyclusStartDate = new Date();
        List<TrackDay> trackDays = Arrays.asList(
                mock(TrackDay.class),
                mock(TrackDay.class));

        List<CyclusStatisticDto> cyclusStatisticDtos = Arrays.asList(
                mock(CyclusStatisticDto.class),
                mock(CyclusStatisticDto.class));

        when(repository.findTop30ByDayGreaterThanEqualOrderByDayAsc(cyclusStartDate)).thenReturn(trackDays);
        when(cyclusStatisticService.getCyclusData(any(Date.class), anyList())).thenReturn(cyclusStatisticDtos);

        // When
        List<CyclusStatisticDto> result = trackDayService.getCyclusData(cyclusStartDate);

        // Then
        assertEquals(2, result.size());
    }

    @Test
    void testGetMinAndMaxTemperature() {
        // Given
        Float minTemp = 36.0f;
        Float maxTemp = 37.5f;
        when(repository.findMinTemperature()).thenReturn(minTemp);
        when(repository.findMaxTemperature()).thenReturn(maxTemp);

        // When
        TrackDayMinMaxTemperatureDto result = trackDayService.getMinAndMaxTemperature();

        // Then
        assertEquals(minTemp, result.getMinTemperature());
        assertEquals(maxTemp, result.getMaxTemperature());
    }
}