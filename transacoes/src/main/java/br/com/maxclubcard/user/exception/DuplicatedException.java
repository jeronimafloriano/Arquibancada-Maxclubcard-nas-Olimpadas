package br.com.maxclubcard.user.exception;


import javax.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DuplicatedException extends ValidationException {

  public DuplicatedException(String message) {
    super(String.format("Valor já existente: %s" , message));
  }

}
