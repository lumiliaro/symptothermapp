package de.lumiliaro.symptothermapp.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import de.lumiliaro.symptothermapp.model.Cyclus;
import io.swagger.v3.oas.annotations.Hidden;

@Hidden
@Repository
public interface CyclusRepository extends ListCrudRepository<Cyclus, Long> {
    public Cyclus findByDate(Date date);

    public List<Cyclus> findAllByOrderByDateDesc();
}
