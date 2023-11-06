package yetanotherconsultingcompany.bex.integration.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CertificateErpResponse(@JsonProperty("availableAmount") double amount) {}
