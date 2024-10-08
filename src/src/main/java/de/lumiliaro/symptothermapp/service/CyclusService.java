package de.lumiliaro.symptothermapp.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;

import de.lumiliaro.symptothermapp.exception.ItemNotFoundException;
import de.lumiliaro.symptothermapp.model.Cyclus;
import de.lumiliaro.symptothermapp.repository.CyclusRepository;
import jakarta.transaction.Transactional;
import lombok.Data;

@Service
@Transactional
@Data
public class CyclusService {
  private final CyclusRepository cyclusRepository;

  public List<Cyclus> findAll() {
    return cyclusRepository.findAll();
  }

  public List<Cyclus> findAllOrderByDateDesc() {
    return cyclusRepository.findAllByOrderByDateDesc();
  }

  public Cyclus findById(Long id) throws ItemNotFoundException {
    return cyclusRepository.findById(id).orElseThrow(() -> new ItemNotFoundException(id, "Cyclus"));
  }

  public Cyclus findByDate(Date date) {
    return cyclusRepository.findByDate(date);
  }

  public void save(Cyclus cyclus) {
    // There is already one cyclus today or past 10 days
    for (int day = 0; day < 10; day++) {
      Date past10Days = DateUtils.addDays(cyclus.getDate(), -day);

      if (cyclusRepository.findByDate(past10Days) != null) {
        return;
      }
    }

    cyclusRepository.save(cyclus);
  }

  public void deleteByDate(Date date) {
    Cyclus cyclus = cyclusRepository.findByDate(date);
    if (cyclus != null) {
      cyclusRepository.delete(cyclus);
    }
  }
}
