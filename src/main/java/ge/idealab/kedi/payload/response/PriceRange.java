package ge.idealab.kedi.payload.response;

import java.math.BigDecimal;

public class PriceRange {
    private BigDecimal maxPrice;
    private BigDecimal minPrice;

    public BigDecimal getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(BigDecimal maxPrice) {
        this.maxPrice = maxPrice;
    }

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(BigDecimal minPrice) {
        this.minPrice = minPrice;
    }
}
