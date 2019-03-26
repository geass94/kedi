package ge.idealab.kedi.dto;

import com.fasterxml.jackson.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class BundleDTO {
    private Long id;
    private ProductDTO parent;
    private List<ProductDTO> products;
    private BigDecimal price;
    private Float sale;

    public Long getId() {
        return id;
    }

    public ProductDTO getParent() {
        return parent;
    }

    public void setParent(ProductDTO parent) {
        this.parent = parent;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public List<ProductDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDTO> products) {
        this.products = products;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Float getSale() {
        return sale;
    }

    public void setSale(Float sale) {
        this.sale = sale;
    }
}
