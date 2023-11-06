package yetanotherconsultingcompany.bex.controller;

import org.springframework.lang.NonNull;

public record GiftCardReservationRequest(
    @NonNull String id, @NonNull String currency, long amount) {

  public static GiftCardReservationRequestBuilder builder() {
    return new GiftCardReservationRequestBuilder();
  }

  public static final class GiftCardReservationRequestBuilder {

    private String id;
    private String currency;
    private long amount;

    private GiftCardReservationRequestBuilder() {}

    public GiftCardReservationRequestBuilder withId(@NonNull final String id) {
      this.id = id;
      return this;
    }

    public GiftCardReservationRequestBuilder withCurrency(@NonNull final String currency) {
      this.currency = currency;
      return this;
    }

    public GiftCardReservationRequestBuilder withAmount(final long amount) {
      this.amount = amount;
      return this;
    }

    public GiftCardReservationRequest build() {
      return new GiftCardReservationRequest(this.id, this.currency, this.amount);
    }
  }
}
