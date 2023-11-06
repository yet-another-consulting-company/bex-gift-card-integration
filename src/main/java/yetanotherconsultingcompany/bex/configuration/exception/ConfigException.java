package yetanotherconsultingcompany.bex.configuration.exception;

public class ConfigException extends RuntimeException {

  public ConfigException(final String msg, final Exception e) {
    super(msg, e);
  }
}
