package ge.idealab.kedi.payload.request;

import ge.idealab.kedi.dto.ProductDTO;

import java.util.List;

public class SaleRequest {
    private List<ProductDTO> products;
    private Float sale;

    public List<ProductDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDTO> products) {
        this.products = products;
    }

    public Float getSale() {
        return sale;
    }

    public void setSale(Float sale) {
        this.sale = sale;
    }
}
