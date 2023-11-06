package yetanotherconsultingcompany.bex.integration.model;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Objects;

public record Price(long amount, Currency currency) {

  public Price(final long amount, final Currency currency) {
    this.amount = naturalNumber(amount);
    this.currency = currency;
  }

  private long naturalNumber(final long number) {
    if (number < 0) {
      throw new NumberFormatException(String.format("Number %d is not natural.", number));
    } else {
      return number;
    }
  }

  public long value() {
    return this.amount;
  }

  public int minorUnitDecimalDigits() {
    return this.currency.getDefaultFractionDigits();
  }

  public String currencyCode() {
    return this.currency.getCurrencyCode();
  }

  public int numericCurrencyCode() {
    return this.currency.getNumericCode();
  }

  public BigDecimal isoDecimalFormat() {
    return new BigDecimal(this.amount).movePointLeft(this.currency.getDefaultFractionDigits());
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final Price price = (Price) o;
    return this.amount == price.amount && Objects.equals(this.currency, price.currency);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.amount, this.currency);
  }

  public static PriceBuilder builder() {
    return new PriceBuilder();
  }

  public static final class PriceBuilder {

    private long value;
    private Currency currency;

    private PriceBuilder() {}

    public PriceBuilder withAmount(final long value) {
      this.value = value;
      return this;
    }

    public PriceBuilder withCurrency(final Currency currency) {
      this.currency = currency;
      return this;
    }

    public Price build() {
      return new Price(this.value, this.currency);
    }
  }

  @Override
  public String toString() {
    return "Price{" + "amount=" + amount + ", currency=" + currency + '}';
  }
}
