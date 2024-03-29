package br.com.maxclubcard.cliente.domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.internal.constraintvalidators.bv.EmailValidator;

@Embeddable
@Access(AccessType.FIELD)
public class Email {

  @NotNull
  private String address;

  private Email(String address) {
    EmailValidator validator = new EmailValidator();

    if (!validator.isValid(address, null)) {
      throw new RuntimeException();
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
