package ge.idealab.kedi.model.bag;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ge.idealab.kedi.model.BaseStatusAuditEntity;
import ge.idealab.kedi.model.product.Product;
import ge.idealab.kedi.model.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "shopping_cart")
public class Cart extends BaseStatusAuditEntity {
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    @NotNull
    @Column
    private Long quantity;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @Column
    private Boolean savedForLater = false;
    @Column
    private Boolean wishlist = false;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Boolean getSavedForLater() {
        return savedForLater;
    }

    public void setSavedForLater(Boolean savedForLater) {
        this.savedForLater = savedForLater;
    }

    public Boolean getWishlist() {
        return wishlist;
    }

    public void setWishlist(Boolean wishlist) {
        this.wishlist = wishlist;
    }
}
