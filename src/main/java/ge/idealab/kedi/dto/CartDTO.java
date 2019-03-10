package ge.idealab.kedi.dto;

public class CartDTO {
    private Long id;
    private ProductDTO product;
    private Long quantity;
    private Boolean savedForLater = false;
    private Boolean wishlist = false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
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
