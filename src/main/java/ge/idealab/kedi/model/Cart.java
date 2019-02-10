package ge.idealab.kedi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ge.idealab.kedi.model.product.Product;
import ge.idealab.kedi.model.user.User;

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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    @JsonIgnore
    private User user;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
