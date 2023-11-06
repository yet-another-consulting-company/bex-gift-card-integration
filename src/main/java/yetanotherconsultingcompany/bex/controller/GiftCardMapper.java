package yetanotherconsultingcompany.bex.controller;

import org.springframework.stereotype.Component;
import yetanotherconsultingcompany.bex.integration.model.GiftCard;
import yetanotherconsultingcompany.bex.integration.model.Price;

import java.util.Currency;
import java.util.Objects;

@Component
public class GiftCardMapper {

  public GiftCard parse(final GiftCardReservationRequest request) {
    try {
      return GiftCard.builder()
            .withId(validate(request.id()))
            .withAmount(Price.builder()
                  .withAmount(request.amount())
                  .withCurrency(Currency.getInstance(request.currency()))
                  .build())
            .build();

    } catch (final Exception e) {
      throw new IllegalArgumentException("Invalid request.");
    }
  }

  public String validate(String id) {

    if (Objects.isNull(id) || !id.matches("^[A-Za-z0-9]+$")) {
      throw new IllegalArgumentException("ID must be alphanumeric");
    }

    return id;
  }
}
