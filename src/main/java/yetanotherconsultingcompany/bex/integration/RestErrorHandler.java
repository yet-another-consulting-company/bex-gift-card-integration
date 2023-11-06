package yetanotherconsultingcompany.bex.integration;

import yetanotherconsultingcompany.bex.integration.exception.BeXAdapterServerException;
import yetanotherconsultingcompany.bex.integration.exception.BeXIntegrationException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.IOUtils;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

@Component
public class RestErrorHandler implements ResponseErrorHandler {

  @Override
  public boolean hasError(final ClientHttpResponse httpResponse) throws IOException {
    return (!httpResponse.getStatusCode().is2xxSuccessful());
  }

  @Override
  public void handleError(final ClientHttpResponse httpResponse) throws IOException {
    if (httpResponse.getStatusCode().is5xxServerError()
        || httpResponse.getStatusCode().value() == 408) {
      throw new BeXAdapterServerException(
          String.format(
              "Received a 5XX error while getting VAT. Error message from source: %s",
              IOUtils.toString(httpResponse.getBody(), StandardCharsets.UTF_8)),
          httpResponse.getStatusCode());
    } else {
      throw new BeXIntegrationException(
          String.format(
              "Received a non-200 response id. HTTP error id was %d. Error message from source: %s",
              httpResponse.getStatusCode().value(),
              IOUtils.toString(httpResponse.getBody(), StandardCharsets.UTF_8)),
          httpResponse.getStatusCode());
    }
  }
}
