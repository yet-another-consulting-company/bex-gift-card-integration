package yetanotherconsultingcompany.bex.integration.exception;

import org.springframework.http.HttpStatus;

public class BeXAdapterServerException extends RuntimeException {

  private HttpStatus status;

  public BeXAdapterServerException(final String message) {
    super(message);
  }

  public BeXAdapterServerException(final String message, final HttpStatus status) {
    super(message);
    this.status = status;
  }

  public BeXAdapterServerException(final String message, final Exception e) {
    super(message, e);
  }

  public BeXAdapterServerException() {}

  public HttpStatus status() {
    return this.status;
  }
}
