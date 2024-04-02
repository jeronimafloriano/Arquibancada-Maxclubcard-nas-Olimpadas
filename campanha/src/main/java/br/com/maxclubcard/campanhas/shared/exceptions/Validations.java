package br.com.maxclubcard.campanhas.shared.exceptions;

import java.util.Objects;

public final class Validations {

  public static void isNotBlank(CharSequence value, ValidationMessage validationMessage) {
    if(Objects.isNull(value)) {
      throw new RequiredArgumentException(validationMessage);
    } else if(value.equals("")) {
      throw new InvalidArgumentException(validationMessage);
    }
  }

  public static void isNotNull(Object object, ValidationMessage validationMessage) {
    if(Objects.isNull(object)) {
      throw new RequiredArgumentException(validationMessage);
    }
  }
}
