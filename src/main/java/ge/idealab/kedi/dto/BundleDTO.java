package ge.idealab.kedi.dto;

import com.fasterxml.jackson.annotation.*;
import ge.idealab.kedi.model.product.Product;

import java.math.BigDecimal;
import java.util.List;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class BundleDTO {
    private Long id;
    private Product parent;
    private List<Product> products;
    private BigDecimal price;
    private Float sale;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonIgnore
    public Product getParent() {
        return parent;
    }

    @JsonProperty
    public void setParent(Product parent) {
        this.parent = parent;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
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
