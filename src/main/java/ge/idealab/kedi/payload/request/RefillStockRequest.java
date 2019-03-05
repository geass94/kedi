package ge.idealab.kedi.payload.request;

import ge.idealab.kedi.dto.ProductDTO;

import java.util.List;

public class RefillStockRequest {
    private List<ProductDTO> products;
    private Long quantity;

    public List<ProductDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDTO> products) {
        this.products = products;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
