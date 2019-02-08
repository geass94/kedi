package ge.idealab.kedi.model;

import ge.idealab.kedi.model.product.Product;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carts")
public class Cart extends BaseStatusAuditEntity {
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "shopping_cart_products",
            joinColumns = { @JoinColumn(name = "cart_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name="fk_shpngcrt_cart_id")) },
            inverseJoinColumns = { @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name="fk_shpngcrt_product_id")) })
    private List<Product> shoppingCart = new ArrayList<>();
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "saved_for_later_products",
            joinColumns = { @JoinColumn(name = "cart_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name="fk_svdltr_cart_id")) },
            inverseJoinColumns = { @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name="fk_svdltr_product_id")) })
    private List<Product> savedForLater = new ArrayList<>();

    public List<Product> getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(List<Product> shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public List<Product> getSavedForLater() {
        return savedForLater;
    }

    public void setSavedForLater(List<Product> savedForLater) {
        this.savedForLater = savedForLater;
    }
}
