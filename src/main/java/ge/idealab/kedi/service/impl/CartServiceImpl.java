package ge.idealab.kedi.service.impl;

import ge.idealab.kedi.model.Cart;
import ge.idealab.kedi.model.product.Product;
import ge.idealab.kedi.model.user.User;
import ge.idealab.kedi.repository.CartRepository;
import ge.idealab.kedi.service.CartService;
import ge.idealab.kedi.service.ProductService;
import ge.idealab.kedi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;

    @Override
    public Cart addToCart(Long productId) {
        Product product = productService.getOne(productId);
        Cart cart = this.loadCart();
        List<Product> currentShoppingCart = cart.getShoppingCart();
        currentShoppingCart.add(product);
        cart.setShoppingCart(currentShoppingCart);
        cart = cartRepository.save(cart);
        return cart;
    }

    @Override
    public Cart addToWishList(Long productId) {
        Product product = productService.getOne(productId);
        Cart cart = this.loadCart();
        List<Product> currentWishList = cart.getSavedForLater();
        currentWishList.add(product);
        cart.setSavedForLater(currentWishList);
        cart = cartRepository.save(cart);
        return cart;
    }

    private Cart loadCart() {
        User user = userService.getUserFromContext();
        Cart cart = cartRepository.findCartByUser(user);
        if ( cart == null ) {
            cart = new Cart();
            cart.setUser(user);
            cart = cartRepository.save(cart);
        }
        return cart;
    }

    @Override
    public Cart saveForLater(Long productId) {
        return null;
    }
}
