package br.com.maxclubcard.campanhas.shared.exceptions;


import javax.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NotFoundException extends ValidationException {

  private final Object object;

  public NotFoundException(ValidationMessage message, Object object) {
    super(message.getMessage());
    this.object = object;
  }

  @Override
  public String toString() {
    return super.toString() + "(" + object + ")";
  }
}
