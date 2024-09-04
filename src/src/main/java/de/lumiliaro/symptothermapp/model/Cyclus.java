package de.lumiliaro.symptothermapp.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
@Table(name = "cyclus")
@Entity
public class Cyclus extends BaseEntity {

  @Column(name = "date", nullable = false, unique = true)
  @NotNull(message = "Das Zyklus-Datum muss gesetzt sein.")
  @PastOrPresent(message = "Das Datum muss in der Vergangenheit oder Gegenwart liegen.")
  @Temporal(TemporalType.DATE)
  private final Date date;

  protected Cyclus() {
    this.date = null;
  }
}
