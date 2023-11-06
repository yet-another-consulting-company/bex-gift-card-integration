package yetanotherconsultingcompany.bex.configuration.resttemplate;

import yetanotherconsultingcompany.bex.configuration.exception.ConfigException;
import yetanotherconsultingcompany.bex.configuration.model.BabySecret;
import yetanotherconsultingcompany.bex.integration.exception.BeXAdapterServerException;
import yetanotherconsultingcompany.bex.integration.RestErrorHandler;
import java.net.URL;
import java.time.Duration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.backoff.BackOffPolicyBuilder;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableRetry
public class RestTemplateConfiguration {
  private static final int TIMEOUT_CONNECT_MILLIS = 5_000;
  private static final int TIMEOUT_SOCKET_MILLIS = 10_000;
  public static final String ERP_BASE_URL_KEY = "bex";

  private final RestTemplateBuilder restTemplateBuilder;

  public RestTemplateConfiguration(final RestErrorHandler errorHandler) {
    this.restTemplateBuilder =
        new RestTemplateBuilder()
            .setConnectTimeout(Duration.ofMillis(TIMEOUT_CONNECT_MILLIS))
            .setReadTimeout(Duration.ofMillis(TIMEOUT_SOCKET_MILLIS))
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .errorHandler(errorHandler)
            .requestFactory(
                () -> new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));
  }

  @Bean
  public RestTemplate httpClient(final BabySecret babySecret) {
    try {
      final URL serviceUrl = new URL(babySecret.url().get(ERP_BASE_URL_KEY).url());
      return this.restTemplateBuilder
          .rootUri(serviceUrl.toExternalForm())
          .basicAuthentication(
              babySecret.url().get(ERP_BASE_URL_KEY).user(),
              babySecret.url().get(ERP_BASE_URL_KEY).password())
          .build();
    } catch (final Exception e) {
      throw new ConfigException(
          String.format("Could not load configuration for HTTP Client. Key: %s.", ERP_BASE_URL_KEY),
          e);
    }
  }

  @Bean
  public RetryTemplate retryTemplate() {
    return RetryTemplate.builder()
        .retryOn(BeXAdapterServerException.class)
        .maxAttempts(5)
        .customBackoff(
            BackOffPolicyBuilder.newBuilder()
                .random(true)
                .delay(1000)
                .maxDelay(5000)
                .multiplier(2)
                .build())
        .build();
  }


}
