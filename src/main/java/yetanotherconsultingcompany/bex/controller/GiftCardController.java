package yetanotherconsultingcompany.bex.controller;

import yetanotherconsultingcompany.bex.integration.BeXGiftCardIntegration;
import yetanotherconsultingcompany.bex.integration.model.GiftCard;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.Currency;

@RestController
@RequestMapping("/gift-card")
public class GiftCardController {


  private final BeXGiftCardIntegration integration;
  private final GiftCardMapper mapper;

  public GiftCardController(final BeXGiftCardIntegration integration, final GiftCardMapper mapper) {
    this.integration = integration;
    this.mapper = mapper;
  }

  @GetMapping("/{id}/{currency}")
  public ResponseEntity<GiftCardResponse> getBalance(
        @PathVariable @NonNull final String id, @PathVariable @NonNull final String currency) {

    final GiftCard availableAmount = this.integration.balance(this.mapper.validate(id), Currency.getInstance(currency));

    return ResponseEntity.ok(GiftCardResponse.of(availableAmount.amount().value(), availableAmount.amount().currencyCode()));
  }

  @PostMapping(produces = "application/json", consumes = "application/json")
  public ResponseEntity<ReservationResponse> makeReservation(
        @RequestBody final GiftCardReservationRequest request) {

    return ResponseEntity.ok(ReservationResponse.of(this.integration.makeReservation(this.mapper.parse(request))));
  }


}