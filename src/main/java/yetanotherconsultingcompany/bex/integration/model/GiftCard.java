package yetanotherconsultingcompany.bex.integration.model;

import org.springframework.lang.NonNull;

import java.util.Objects;

public record GiftCard(@NonNull String id, @NonNull Price amount) {

  public GiftCard(@NonNull final String id, @NonNull final Price amount) {
    this.id = Objects.requireNonNull(id, "Id cannot be null.");
    this.amount = Objects.requireNonNull(amount, "Amount cannot be null.");
  }

  public static GiftCertificateBuilder builder() {
    return new GiftCertificateBuilder();
  }

  public static final class GiftCertificateBuilder {
    private String id;
    private Price amount;

    private GiftCertificateBuilder() {}

    public GiftCertificateBuilder withId(@NonNull final String id) {
      this.id = id;
      return this;
    }

    public GiftCertificateBuilder withAmount(@NonNull final Price amount) {
      this.amount = amount;
      return this;
    }

    public GiftCard build() {
      return new GiftCard(this.id, this.amount);
    }
  }
}
