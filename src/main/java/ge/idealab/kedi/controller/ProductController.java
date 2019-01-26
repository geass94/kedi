package ge.idealab.kedi.controller;

import ge.idealab.kedi.dto.ProductDTO;
import ge.idealab.kedi.model.product.Product;
import ge.idealab.kedi.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/add-product")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> addProduct(@RequestBody @Valid ProductDTO productDTO){
        ModelMapper modelMapper = new ModelMapper();
        Product product = productService.create(productDTO);
        return ResponseEntity.ok(modelMapper.map(product, ProductDTO.class));
    }

    @GetMapping("/get-product-by-id/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id){
        ModelMapper modelMapper = new ModelMapper();
        ProductDTO productDTO = modelMapper.map(productService.getOne(id), ProductDTO.class);
        return ResponseEntity.ok(productDTO);
    }

    @GetMapping("/get-products")
    public ResponseEntity<?> getProducts(){
        ModelMapper modelMapper = new ModelMapper();
        Pageable sortedByName =
                PageRequest.of(0, 3, Sort.by("name"));

        List<ProductDTO> productDTOS = new ArrayList<>();
        Page<Product> productPage = productService.getPaginatedProducts(sortedByName);
        for(Product product: productPage.getContent()){
            productDTOS.add(modelMapper.map(product, ProductDTO.class));
        }

        return ResponseEntity.ok(new PageImpl<>(productDTOS, sortedByName, productPage.getTotalElements()));
    }
}
