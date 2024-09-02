package de.lumiliaro.symptothermapp.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemNotFoundException extends RuntimeException {
  private Long id;
  private String resource;

  public ItemNotFoundException(Long id, String resource) {
    super(String.format("Die angefragte Resource \"%s\" mit der ID \"%s\" wurde nicht gefunden.", resource, id));
    this.setId(id);
    this.setResource(resource);
  }
}
