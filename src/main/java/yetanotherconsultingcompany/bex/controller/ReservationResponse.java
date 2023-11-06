package yetanotherconsultingcompany.bex.controller;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ReservationResponse(@JsonProperty("reservationId") String reservationId) {
  public static ReservationResponse of(String id) {
    return new ReservationResponse(id);
  }
}
