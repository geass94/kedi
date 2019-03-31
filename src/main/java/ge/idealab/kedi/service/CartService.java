package ge.idealab.kedi.service;

import ge.idealab.kedi.model.bag.Cart;
import ge.idealab.kedi.model.product.Product;
import ge.idealab.kedi.model.user.User;

import java.util.List;

public interface CartService {
    Cart addToCart(Long productId, Long quantity);
    Cart saveForLater(Long cartId);
    Cart moveToCart(Long cartId);
    Cart addToWishlist(Long productId);

    Boolean removeFromCart(Long cartId);

    List<Cart> getUserCart();

    void clearShoppingCartByProducts(List<Product> products, User user);
}
