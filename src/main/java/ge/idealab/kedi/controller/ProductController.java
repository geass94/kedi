package ge.idealab.kedi.controller;

import ge.idealab.kedi.dto.*;
import ge.idealab.kedi.model.product.Product;
import ge.idealab.kedi.model.product.ProductFile;
import ge.idealab.kedi.payload.request.RefillStockRequest;
import ge.idealab.kedi.payload.request.SaleRequest;
import ge.idealab.kedi.service.FileService;
import ge.idealab.kedi.service.ProductFileService;
import ge.idealab.kedi.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/product")
//@CrossOrigin(origins = "http://localhost:4200")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/get-product-by-id/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id){
        ModelMapper modelMapper = new ModelMapper();
        Product product = productService.getOne(id);
        ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
        productDTO.setProductFiles(mapFiles(product));
        return ResponseEntity.ok(productDTO);
    }

    @GetMapping("/get-products")
    public ResponseEntity<?> getProducts(@RequestParam("sort") String sort,
                                         @RequestParam("order") String order,
                                         @RequestParam("page") String page){
        ModelMapper modelMapper = new ModelMapper();

        Pageable sortedByName =
                PageRequest.of(Integer.valueOf(page), 30, order == "desc" ? Sort.by(sort).descending() : Sort.by(sort).ascending());

        List<ProductDTO> productDTOS = new ArrayList<>();
        Page<Product> productPage = productService.getPaginatedProducts(sortedByName);

        for(Product product: productPage.getContent()){
            ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
            productDTO.setProductFiles(mapFiles(product));
            productDTOS.add(productDTO);
        }

        PageImpl<?> pageImpl = new PageImpl<>(productDTOS, sortedByName, productPage.getTotalElements());
        return ResponseEntity.ok(pageImpl);
    }

    @GetMapping("/get-price-range")
    public ResponseEntity<?> getPriceRange() {
        return ResponseEntity.ok(productService.priceRange());
    }

    @GetMapping("/get-product-variants")
    public ResponseEntity<?> getProductVariants(@RequestParam("ids") String[] variantIds){

        List<Product> products = productService.getProductVariants(stringArrayToLongArray(variantIds));
        List<ProductDTO> productDTOS = this.mapProducts(products);

        return ResponseEntity.ok(productDTOS);
    }

    @GetMapping("/get-products-by-filter")
    public ResponseEntity<?> getProductsByFilter(@RequestParam("categories") String[] categorIds,
                                                 @RequestParam("colors") String[] colorIds,
                                                 @RequestParam("manufacturers") String[] manufacturerIds,
                                                 @RequestParam("min_price") String min_price,
                                                 @RequestParam("max_price") String max_price,
                                                 @RequestParam("sort") String sort,
                                                 @RequestParam("order") String order,
                                                 @RequestParam("page") String page){

        Long[] categories = this.stringArrayToLongArray(categorIds);
        Long[] colors = this.stringArrayToLongArray(colorIds);
        Long[] manufacturers = this.stringArrayToLongArray(manufacturerIds);
        BigDecimal maxPrice = BigDecimal.valueOf(Double.valueOf(max_price));
        BigDecimal minPrice = BigDecimal.valueOf(Double.valueOf(min_price));

        Pageable sorting =
                PageRequest.of(Integer.valueOf(page), 30, order == "desc" ? Sort.by(sort).descending() : Sort.by(sort).ascending());

        Page<Product> productPage = productService.getPaginatedProductsByFilter(categories, colors, manufacturers, minPrice, maxPrice, sorting);
        List<ProductDTO> productDTOS = this.mapProducts(productPage.getContent());
        List<ProductDTO> deduped = productDTOS.stream().distinct().collect(Collectors.toList());
        PageImpl<?> pageImpl = new PageImpl<>(deduped, sorting, productPage.getTotalElements());

        return ResponseEntity.ok(pageImpl);
    }

    @GetMapping("/get-bundles")
    public ResponseEntity<?> getBundles() {
        List<ProductDTO> productDTOS = this.mapProducts(productService.getProductsWithBundles());
        return ResponseEntity.ok(productDTOS);
    }

    @GetMapping("/get-featured-products")
    public ResponseEntity<?> getFeaturedProducts() {
        return ResponseEntity.ok(this.mapProducts(productService.getFeaturedProducts()));
    }

    @GetMapping("/get-new-products")
    public ResponseEntity<?> getNewProducts() {
        return ResponseEntity.ok(this.mapProducts(productService.getNewProducts()));
    }

    @GetMapping("/get-related-products/{pid}")
    public ResponseEntity<?> getRelatedProcust(@PathVariable Long pid) {
        return ResponseEntity.ok(this.mapProducts(productService.getRealtedProducts(pid)));
    }

    @GetMapping("/get-sale-off")
    public ResponseEntity<?> getProductsOnSale() {
        return ResponseEntity.ok(this.mapProducts(productService.getProductsOnSale()));
    }

    private List<ProductFileDTO> mapFiles(Product product){
        ModelMapper modelMapper = new ModelMapper();
        List<ProductFileDTO> productFileDTOS = new ArrayList<>();
        for(ProductFile productFile: product.getProductFileList()){
            productFileDTOS.add(modelMapper.map(productFile, ProductFileDTO.class));
        }
        return productFileDTOS;
    }

    private List<ProductDTO> mapProducts(List<Product> products) {
        ModelMapper modelMapper = new ModelMapper();
        List<ProductDTO> productDTOS = new ArrayList<>();
        for(Product product: products){
            ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
            productDTO.setProductFiles(mapFiles(product));
            productDTOS.add(productDTO);
        }
        return productDTOS;
    }

    private Long[] stringArrayToLongArray(String[] numbers) {
        Long[] result = new Long[numbers.length];
        for (int i = 0; i < numbers.length; i++)
            result[i] = Long.valueOf(numbers[i]);
        return result;
    }
}
