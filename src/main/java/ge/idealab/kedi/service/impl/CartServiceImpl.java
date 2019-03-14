package ge.idealab.kedi.service.impl;

import ge.idealab.kedi.model.bag.Cart;
import ge.idealab.kedi.model.enums.Status;
import ge.idealab.kedi.model.product.Product;
import ge.idealab.kedi.model.user.User;
import ge.idealab.kedi.repository.CartRepository;
import ge.idealab.kedi.service.CartService;
import ge.idealab.kedi.service.ProductService;
import ge.idealab.kedi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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
    public Cart addToCart(Long productId, Long quantity) {
        Product product = productService.getOne(productId);
        User user = userService.getUserFromContext();
        Cart cart = cartRepository.findByProductAndUserAndStatus(product, user, Status.ACTIVE);
        if (cart != null) {
            cart.setQuantity( cart.getQuantity() + quantity );
            cart.setUpdatedAt(new Date());
            cart.setUpdatedBy(user.getId());
        } else {
            cart = new Cart();
            cart.setProduct(product);
            cart.setQuantity(quantity);
            cart.setUser(user);
        }
        cartRepository.save(cart);
        return cart;
    }

    @Override
    public Cart saveForLater(Long cartId) {
        Cart cart = cartRepository.getOne(cartId);
        cart.setSavedForLater(true);
        cart = cartRepository.save(cart);
        cart.setUpdatedAt(new Date());
        cart.setUpdatedBy(userService.getUserFromContext().getId());
        return cart;
    }

    @Override
    public Cart moveToCart(Long cartId) {
        Cart cart = cartRepository.getOne(cartId);
        cart.setSavedForLater(false);
        cart = cartRepository.save(cart);
        cart.setUpdatedAt(new Date());
        cart.setUpdatedBy(userService.getUserFromContext().getId());
        return cart;
    }

    @Override
    public Cart addToWishlist(Long productId) {
        Cart cart = new Cart();
        cart.setUser(userService.getUserFromContext());
        cart.setProduct(productService.getOne(productId));
        cart.setWishlist(true);
        cart = cartRepository.save(cart);
        return cart;
    }

    @Override
    public Boolean removeFromCart(Long cartId) {
        Cart cart = cartRepository.getOne(cartId);
        cart.setStatus(Status.DELETED);
        cart = cartRepository.save(cart);
        return cart.getStatus() == Status.DELETED;
    }

    @Override
    public List<Cart> getUserCart() {
        List<Cart> cartList = cartRepository.findAllByUserAndStatus(userService.getUserFromContext(), Status.ACTIVE);
        return cartList;
    }

    @Override
    public void clearShoppingCartByProducts(List<Product> products) {
        List<Cart> cartList = cartRepository.findAllByUserAndProductInAndSavedForLaterIsFalseAndWishlistIsFalseAndStatus(userService.getUserFromContext(), products, Status.ACTIVE);
        for (Cart cart : cartList) {
            cart.setStatus(Status.DELETED);
            cartRepository.save(cart);
        }
    }

}
