package br.com.maxclubcard.campanhas.cliente.domain;

import br.com.maxclubcard.campanhas.shared.exceptions.InvalidArgumentException;
import br.com.maxclubcard.campanhas.shared.exceptions.ValidationMessage;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.internal.constraintvalidators.bv.EmailValidator;

@Embeddable
@Access(AccessType.FIELD)
public class Email {

  @Column(name = "email")
  @NotNull
  private String address;

  private Email(String address) {
    EmailValidator validator = new EmailValidator();

    if (!validator.isValid(address, null)) {
      throw new InvalidArgumentException(ValidationMessage.INVALID_EMAIL);
    }

    this.address = address;
  }

  protected Email() {}

  public static Email of(String address) {
    return new Email(address);
  }

  public String getAddress() {
    return address;
  }
}
