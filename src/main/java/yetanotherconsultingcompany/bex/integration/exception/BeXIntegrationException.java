package yetanotherconsultingcompany.bex.integration.exception;

import org.springframework.http.HttpStatus;

public class BeXIntegrationException extends RuntimeException {

  private final HttpStatus status;

  public BeXIntegrationException(final String message, final HttpStatus status) {
    super(message);
    this.status = status;
  }

  public HttpStatus status() {
    return this.status;
  }
}
