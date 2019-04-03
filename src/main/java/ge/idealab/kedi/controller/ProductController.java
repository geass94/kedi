package ge.idealab.kedi.controller;

import ge.idealab.kedi.dto.*;
import ge.idealab.kedi.model.product.Product;
import ge.idealab.kedi.model.product.ProductFile;
import ge.idealab.kedi.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> getProductById(@PathVariable Long id, @RequestParam(value = "size", required=false) String size){
        ModelMapper modelMapper = new ModelMapper();
        Product product = productService.getOneByParams(id, size != null ? Long.valueOf(size) : 0L);
        ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
        productDTO.setBaseVariantId(product.getBaseVariant().getId());
//        if (product.getBundle() != null) {
//            List<ProductDTO> bundleProducts = this.mapProducts(product.getBundle().getProducts());
//            productDTO.getBundle().setProducts(bundleProducts);
//        }
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
            productDTO.setBaseVariantId(product.getBaseVariant().getId());
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
    public ResponseEntity<?> getProductVariants(@RequestParam("id") Long id){

        List<Product> products = productService.getProductVariants(productService.getOne(id));
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

        List<Long> categories = this.stringArrayToLongArray(categorIds);
        List<Long> colors = this.stringArrayToLongArray(colorIds);
        List<Long> manufacturers = this.stringArrayToLongArray(manufacturerIds);
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

    @GetMapping("/get-bundles-for-product/{pid}")
    public ResponseEntity<?> getBundles(@PathVariable Long pid) {
        List<ProductDTO> productDTOS = this.mapProducts(productService.getProductsWithBundles(pid));
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

    @GetMapping("/get-best-sale")
    public ResponseEntity<?> getBestSaleProducts() {
        return ResponseEntity.ok(this.mapProducts(productService.getBestSaleProducts()));
    }

    private List<ProductFileDTO> mapFiles(Product product){
        ModelMapper modelMapper = new ModelMapper();
        List<ProductFileDTO> productFileDTOS = new ArrayList<>();
        for(ProductFile productFile: product.getProductFiles()){
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
            productDTO.setBaseVariantId(product.getBaseVariant().getId());
            productDTOS.add(productDTO);
        }
        return productDTOS;
    }

    private List<Long> stringArrayToLongArray(String[] numbers) {
//        Long[] result = new Long[numbers.length];
        List<Long> result = new ArrayList<>();
        for (int i = 0; i < numbers.length; i++)
            result.add(Long.valueOf(numbers[i]));
        return result;
    }
}
