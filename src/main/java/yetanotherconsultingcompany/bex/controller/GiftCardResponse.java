package yetanotherconsultingcompany.bex.controller;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GiftCardResponse(
    @JsonProperty("amount") long amount, @JsonProperty("currency") String currency) {
  public static GiftCardResponse of(long amount, String currencyCode) {
    return new GiftCardResponse(amount, currencyCode);
  }
}
