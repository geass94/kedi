package ge.idealab.kedi.dto;

import java.util.List;

public class CartDTO {
    private Long id;
    private List<ProductDTO> shoppingCart;
    private List<ProductDTO> savedForLater;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<ProductDTO> getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(List<ProductDTO> shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public List<ProductDTO> getSavedForLater() {
        return savedForLater;
    }

    public void setSavedForLater(List<ProductDTO> savedForLater) {
        this.savedForLater = savedForLater;
    }
}
