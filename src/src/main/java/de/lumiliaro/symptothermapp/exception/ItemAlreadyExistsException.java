package de.lumiliaro.symptothermapp.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemAlreadyExistsException extends RuntimeException {
  public ItemAlreadyExistsException(String message) {
    super(message);
  }
}
