package ge.idealab.kedi.payload.request;

import javax.validation.constraints.NotNull;

public class AddtoCartRequest {
    @NotNull
    private Long productId;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
