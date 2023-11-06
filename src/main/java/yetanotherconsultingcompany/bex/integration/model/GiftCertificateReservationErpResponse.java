package yetanotherconsultingcompany.bex.integration.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GiftCertificateReservationErpResponse(
    @JsonProperty("reservationId") String reservationId) {}
