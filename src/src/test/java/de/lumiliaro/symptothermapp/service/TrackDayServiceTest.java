package de.lumiliaro.symptothermapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Calendar;
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

import de.lumiliaro.symptothermapp.dto.TrackDayDto;
import de.lumiliaro.symptothermapp.dto.TrackDayLineChartStatisticDto;
import de.lumiliaro.symptothermapp.exception.ItemAlreadyExistsException;
import de.lumiliaro.symptothermapp.exception.ItemNotFoundException;
import de.lumiliaro.symptothermapp.mapper.TrackDayMapperImpl;
import de.lumiliaro.symptothermapp.model.TrackDay;
import de.lumiliaro.symptothermapp.repository.TrackDayRepository;

class TrackDayServiceTest {

    @Mock
    private TrackDayRepository repository;

    @InjectMocks
    private TrackDayService service;

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
        Page<TrackDay> result = service.findAllPageable(pageable);

        // Then
        assertEquals(trackDays.size(), result.getTotalElements());
        verify(repository, times(1)).findAll(pageable);
    }

    @Test
    void testFindAllByMonth() {
        // Given
        int month = 5;
        int year = 2023;

        TrackDay trackday1 = new TrackDay(1L, new Date(), null, null, null, null, null, null, null, null, null, null,
                null);
        TrackDay trackday2 = new TrackDay(1L, new Date(), null, null, null, null, null, null, null, null, null, null,
                null);

        List<TrackDay> trackDays = Arrays.asList(
                trackday1,
                trackday2);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        Date startDate = calendar.getTime();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date endDate = calendar.getTime();

        when(repository.findAllByMonth(startDate, endDate)).thenReturn(trackDays);

        // When
        List<TrackDay> result = service.findAllByMonth(month, year);

        // Then
        // assertEquals(trackDays.size(), result.size());
        // verify(repository, times(1)).findAllByMonth(startDate, endDate);
    }

    @Test
    void testFindOne_WhenTrackDayExists() throws ItemNotFoundException {
        // Given
        Long id = 1L;
        TrackDay trackDay = new TrackDay(1L, new Date(), null, null, null, null, null, null, null, null, null, null,
                null);
        when(repository.findById(id)).thenReturn(Optional.of(trackDay));

        // When
        TrackDay foundTrackDay = service.findOne(id);

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
            service.findOne(id);
        });

        assertEquals("Die angefragte Resource \"TrackDay\" mit der ID \"1\" wurde nicht gefunden.",
                exception.getMessage());
        verify(repository, times(1)).findById(id);
    }

    @Test
    void testSave_WhenTrackDayDoesNotExist() throws ItemAlreadyExistsException {
        // Given
        Date trackDayDate = new Date();
        TrackDayDto trackDayDto = new TrackDayDto(null, trackDayDate, null, null, null, null, null, false, null, null,
                null, null);

        TrackDay trackDay = new TrackDayMapperImpl().fromDto(trackDayDto);

        when(repository.existsByDay(trackDayDate)).thenReturn(false);
        when(repository.save(any(TrackDay.class))).thenReturn(trackDay);

        // When
        TrackDay savedTrackDay = service.save(trackDayDto);

        // Then
        assertNotNull(savedTrackDay);
        assertEquals(trackDayDate, savedTrackDay.getDay());
        assertFalse(savedTrackDay.getHadSex());
        assertNotEquals(true, savedTrackDay.getWithContraceptives());
        // hat
        verify(repository, times(1)).existsByDay(trackDayDate);
        verify(repository, times(1)).save(any(TrackDay.class));
    }

    @Test
    void testSave_WhenTrackDayAlreadyExists() {
        // Given
        Date trackDayDate = new Date();
        TrackDayDto trackDayDto = new TrackDayDto(null, trackDayDate, null, null, null, null, null, null, null, null,
                null, null);

        when(repository.existsByDay(trackDayDate)).thenReturn(true);

        // When & Then
        ItemAlreadyExistsException exception = assertThrows(ItemAlreadyExistsException.class, () -> {
            service.save(trackDayDto);
        });

        assertEquals("Ein Datensatz mit diesem Datum existiert bereits.", exception.getMessage());
        verify(repository, times(1)).existsByDay(trackDayDate);
        verify(repository, never()).save(any(TrackDay.class));
    }

    @Test
    void testUpdate_WhenTrackDayExistsAndNoConflict() throws ItemNotFoundException, ItemAlreadyExistsException {
        // Given
        Long id = 1L;
        Date trackDayDate = new Date();
        TrackDayDto trackDayDto = new TrackDayDto(null, trackDayDate, null, null, null, null, null,
                false, null, null,
                null, null); // Beispielwert, wenn kein Geschlechtsverkehr stattgefunden hat

        TrackDay existingTrackDay = new TrackDay(id, trackDayDate, null, null, null, null, null, null, null, null, null,
                null,
                null);
        when(repository.findById(id)).thenReturn(Optional.of(existingTrackDay));
        when(repository.findByDayAndIdNot(trackDayDate, id)).thenReturn(null);

        TrackDay updatedTrackDay = new TrackDayMapperImpl().fromDto(trackDayDto);
        updatedTrackDay.setId(id);

        // When
        service.update(id, trackDayDto);

        // Then
        verify(repository, times(1)).findById(id);
        verify(repository, times(1)).findByDayAndIdNot(trackDayDto.getDay(), id);
        verify(repository, times(1)).save(any(TrackDay.class));
    }

    @Test
    void testUpdate_WhenTrackDayNotFound() {
        // Given
        Long id = 1L;
        TrackDayDto trackDayDto = new TrackDayDto(null, new Date(), null, null, null, null, null, null, null, null,
                null, null);

        when(repository.findById(id)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ItemNotFoundException.class, () -> {
            service.update(id, trackDayDto);
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
        TrackDayDto trackDayDto = new TrackDayDto(null, trackDayDate, null, null, null, null, null, null, null, null,
                null, null);

        TrackDay existingTrackDay = new TrackDay(id, trackDayDate, null, null, null, null, null, null, null, null, null,
                null,
                null);
        when(repository.findById(id)).thenReturn(Optional.of(existingTrackDay));

        TrackDay conflictingTrackDay = new TrackDay(2L, trackDayDate, null, null, null, null, null, null, null, null,
                null,
                null,
                null);
        when(repository.findByDayAndIdNot(trackDayDate, id)).thenReturn(conflictingTrackDay);

        // When & Then
        assertThrows(ItemAlreadyExistsException.class, () -> {
            service.update(id, trackDayDto);
        });

        verify(repository, times(1)).findById(id);
        verify(repository, times(1)).findByDayAndIdNot(trackDayDto.getDay(), id);
        verify(repository, never()).save(any(TrackDay.class));
    }

    @Test
    void testDelete_WhenTrackDayExists() throws ItemNotFoundException {
        // Given
        Long id = 1L;
        TrackDay trackDay = mock(TrackDay.class);

        when(repository.findById(id)).thenReturn(Optional.of(trackDay));

        // When
        service.delete(id);

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
        assertThrows(ItemNotFoundException.class, () -> service.delete(id));
        verify(repository, times(1)).findById(id);
        verify(repository, never()).deleteById(id);
    }

    @Test
    void testGetTrackDaysForMonthStatistic() {
        // Given
        int month = 5;
        int year = 2023;

        TrackDay trackday1 = new TrackDay(1L, new Date(), null, null, null, null, null, null, null, null, null, null,
                null);
        TrackDay trackday2 = new TrackDay(2L, new Date(), null, null, null, null, null, null, null, null, null, null,
                null);

        List<TrackDay> trackDays = Arrays.asList(
                trackday1,
                trackday2);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        Date startDate = calendar.getTime();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date endDate = calendar.getTime();

        when(repository.findAllByMonth(startDate, endDate)).thenReturn(trackDays);

        // When
        List<TrackDayLineChartStatisticDto> result = service.getTrackDaysForMonthStatistic(month, year);

        // Then
        assertNotNull(result);
        assertEquals(31, result.size());
    }
}