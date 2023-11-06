package yetanotherconsultingcompany.bex.integration.model;

import static java.util.Objects.requireNonNull;

import org.springframework.lang.NonNull;

public record GiftCertificateReservationErpRequest(
    @NonNull String code, @NonNull String currency, double amount) {

  public GiftCertificateReservationErpRequest(
      @NonNull final String code, @NonNull final String currency, final double amount) {
    this.code = requireNonNull(code, "Code cannot be null.");
    this.currency = requireNonNull(currency, "Currency cannot be null.");
    this.amount = amount;
  }

  public static GiftCertificateReservationErpRequestBuilder builder() {
    return new GiftCertificateReservationErpRequestBuilder();
  }

  public static final class GiftCertificateReservationErpRequestBuilder {
    private String code;
    private String currency;
    private double amount;

    private GiftCertificateReservationErpRequestBuilder() {}

    public GiftCertificateReservationErpRequestBuilder withCode(final String code) {
      this.code = code;
      return this;
    }

    public GiftCertificateReservationErpRequestBuilder withCurrency(final String currency) {
      this.currency = currency;
      return this;
    }

    public GiftCertificateReservationErpRequestBuilder withAmount(final double amount) {
      this.amount = amount;
      return this;
    }

    public GiftCertificateReservationErpRequest build() {
      return new GiftCertificateReservationErpRequest(this.code, this.currency, this.amount);
    }
  }
}
