package ge.idealab.kedi.service;

import ge.idealab.kedi.model.Cart;

public interface CartService {
    Cart addToCart(Long productId);
    Cart addToWishList(Long productId);
    Cart saveForLater(Long productId);
}
