package ge.idealab.kedi.model.product;

import ge.idealab.kedi.model.File;

import javax.persistence.*;

@Entity
@Table(name = "product_files")
public class ProductFile extends File {
    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
