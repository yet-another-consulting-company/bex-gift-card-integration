package yetanotherconsultingcompany.bex.integration;

import static java.util.Objects.requireNonNull;

import yetanotherconsultingcompany.bex.integration.model.Price;
import yetanotherconsultingcompany.bex.integration.model.CertificateErpResponse;
import yetanotherconsultingcompany.bex.integration.model.GiftCertificateReservationErpRequest;
import yetanotherconsultingcompany.bex.integration.model.GiftCertificateReservationErpResponse;
import yetanotherconsultingcompany.bex.integration.model.GiftCard;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Objects;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class BeXGiftCardIntegration {

  private static final String CERTIFICATE_URI = "Certificate";
  private static final String CERTIFICATE_AVAILABLE_AMOUNT_URI = "GetAvailableAmount";
  private static final String CODE_QUERY_PARAM = "code";
  private static final String CURRENCY_QUERY_PARAM = "currency";
  private static final String CREATE_RESERVATION_URI = "CreateReservation";

  private final RestTemplate erpRestTemplate;

  public BeXGiftCardIntegration(final RestTemplate erpRestTemplate) {
    this.erpRestTemplate = erpRestTemplate;
  }


  @NonNull
  public GiftCard balance(
      @NonNull final String id, @NonNull final Currency currency) {
    final ResponseEntity<CertificateErpResponse> response =
        this.erpRestTemplate.getForEntity(
            String.format(
                "/%s/%s?%s=%s&%s=%s",
                CERTIFICATE_URI,
                CERTIFICATE_AVAILABLE_AMOUNT_URI,
                CODE_QUERY_PARAM,
                id,
                CURRENCY_QUERY_PARAM,
                currency.getCurrencyCode()),
            CertificateErpResponse.class);

    return toGiftCertificate(
        Objects.requireNonNull(response.getBody(), "Response Body cannot be null."), currency, id);
  }


  @NonNull
  public String makeReservation(GiftCard certificate) {
    final var requestEntity =
        new HttpEntity<>(
            GiftCertificateReservationErpRequest.builder()
                .withAmount(certificate.amount().isoDecimalFormat().doubleValue())
                .withCurrency(certificate.amount().currencyCode())
                .withCode(certificate.id())
                .build());

    final var responseEntity =
        this.erpRestTemplate.exchange(
            String.format("/%s/%s", CERTIFICATE_URI, CREATE_RESERVATION_URI),
            HttpMethod.POST,
            requestEntity,
            GiftCertificateReservationErpResponse.class);

    return toGiftCertificateReservation(
        requireNonNull(responseEntity.getBody(), "Response Body cannot be null."));
  }

  private static GiftCard toGiftCertificate(
      final CertificateErpResponse response,
      final Currency currency,
      final String code) {
    return GiftCard.builder()
        .withAmount(
            Price.builder()
                .withAmount(
                    BigDecimal.valueOf(response.amount())
                        .multiply(BigDecimal.TEN.pow(currency.getDefaultFractionDigits()))
                        .longValue())
                .withCurrency(currency)
                .build())
        .withId(code)
        .build();
  }

  private static String toGiftCertificateReservation(
      final GiftCertificateReservationErpResponse response) {
    return response.reservationId();
  }
}
