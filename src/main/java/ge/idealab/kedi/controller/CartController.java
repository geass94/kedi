package ge.idealab.kedi.controller;

import ge.idealab.kedi.dto.CartDTO;
import ge.idealab.kedi.model.Cart;
import ge.idealab.kedi.payload.request.AddtoCartRequest;
import ge.idealab.kedi.service.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @PostMapping("/add-to-cart")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> addToCart(@RequestBody @Valid AddtoCartRequest addtoCartRequest) {
        ModelMapper modelMapper = new ModelMapper();
        Cart cart = cartService.addToCart(addtoCartRequest.getProductId());
        CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);
        return ResponseEntity.ok(cartDTO);
    }

    @PostMapping("/add-to-wishlist")
    public ResponseEntity<?> addToWishList(@RequestBody @Valid AddtoCartRequest addtoCartRequest) {
        ModelMapper modelMapper = new ModelMapper();
        Cart cart = cartService.addToWishList(addtoCartRequest.getProductId());
        CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);
        return ResponseEntity.ok(cartDTO);
    }
}
