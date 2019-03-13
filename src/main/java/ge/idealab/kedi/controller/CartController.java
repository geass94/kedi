package ge.idealab.kedi.controller;

import ge.idealab.kedi.dto.CartDTO;
import ge.idealab.kedi.dto.ProductDTO;
import ge.idealab.kedi.dto.ProductFileDTO;
import ge.idealab.kedi.model.bag.Cart;
import ge.idealab.kedi.model.product.Product;
import ge.idealab.kedi.model.product.ProductFile;
import ge.idealab.kedi.payload.request.AddtoCartRequest;
import ge.idealab.kedi.service.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/cart")
//@CrossOrigin(origins = "http://localhost:4200")
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping("/get-user-cart")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getUserCart() {
        return ResponseEntity.ok(this.mapCarts(cartService.getUserCart()));
    }

    @PostMapping("/add-to-cart")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> addToCart(@RequestBody @Valid AddtoCartRequest addtoCartRequest) {
        Cart cart = cartService.addToCart(addtoCartRequest.getProductId(), addtoCartRequest.getQuantity());
        return ResponseEntity.ok(this.mapCart(cart));
    }

    @PostMapping("/add-to-wishlist")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> addToWishList(@RequestBody @Valid AddtoCartRequest addtoCartRequest) {
        Cart cart = cartService.addToWishlist(addtoCartRequest.getProductId());
        return ResponseEntity.ok(this.mapCart(cart));
    }

    @PostMapping("/save-for-later")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> saveForLater(@RequestBody @Valid AddtoCartRequest addtoCartRequest) {
        Cart cart = cartService.saveForLater(addtoCartRequest.getCartId());
        return ResponseEntity.ok(this.mapCart(cart));
    }

    @PostMapping("/move-to-cart")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> moveToCart(@RequestBody @Valid AddtoCartRequest addtoCartRequest) {
        Cart cart = cartService.moveToCart(addtoCartRequest.getCartId());
        return ResponseEntity.ok(this.mapCart(cart));
    }

    @DeleteMapping("/remove-from-cart/{cid}")
    @PreAuthorize("hasRole('USER')")
    public Boolean removeFromCart(@PathVariable Long cid) {
        return cartService.removeFromCart(cid);
    }

    private CartDTO mapCart(Cart cart) {
        ModelMapper modelMapper = new ModelMapper();
        CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);
        cartDTO.setProduct( this.mapProduct(cart.getProduct()) );
        return cartDTO;
    }

    private List<CartDTO> mapCarts(List<Cart> carts) {
        ModelMapper modelMapper = new ModelMapper();
        List<CartDTO> cartDTOS = new ArrayList<>();
        for (Cart c : carts) {
            CartDTO c1 = modelMapper.map(c, CartDTO.class);
            c1.setProduct(this.mapProduct(c.getProduct()));
            cartDTOS.add(c1);
        }
        return cartDTOS;
    }

    private List<ProductFileDTO> mapFiles(Product product){
        ModelMapper modelMapper = new ModelMapper();
        List<ProductFileDTO> productFileDTOS = new ArrayList<>();
        for(ProductFile productFile: product.getProductFileList()){
            productFileDTOS.add(modelMapper.map(productFile, ProductFileDTO.class));
        }
        return productFileDTOS;
    }

    private ProductDTO mapProduct(Product product) {
        ModelMapper modelMapper = new ModelMapper();
        ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
        productDTO.setProductFiles(mapFiles(product));
        return productDTO;
    }
}
