package ge.idealab.kedi.service;

import ge.idealab.kedi.model.bag.Cart;

import java.util.List;

public interface CartService {
    Cart addToCart(Long productId, Long quantity);
    Cart saveForLater(Long cartId);
    Cart moveToCart(Long cartId);
    Cart addToWishlist(Long productId);

    List<Cart> getUserCart();
}
