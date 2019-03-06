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

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductFileService productFileService;
    @Autowired
    private FileService fileService;

    @PostMapping("/add-product")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addProduct(@RequestBody @Valid ProductDTO productDTO){
        ModelMapper modelMapper = new ModelMapper();
        Product product = productService.create(productDTO);
        return ResponseEntity.ok(modelMapper.map(product, ProductDTO.class));
    }

    @PostMapping(path = "/add-product-file", consumes = {"multipart/form-data"})
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addProductFile(@Valid @RequestPart("product-id") String productId, @Valid @RequestPart("files") MultipartFile[] multipartFiles){
        ModelMapper modelMapper = new ModelMapper();
        Product product = productService.getOne(Long.valueOf(productId));
        List<ProductFileDTO> productFileDTOS = new ArrayList<>();
        for(MultipartFile multipartFile: multipartFiles){
            ProductFile productFile = modelMapper.map(fileService.uploadFile(multipartFile), ProductFile.class);
            productFile.setProduct(product);
            productFile = productFileService.create(productFile);
            productFileDTOS.add(modelMapper.map(productFile, ProductFileDTO.class));
        }
        return ResponseEntity.ok(productFileDTOS);
    }

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

        PageImpl<?> pageImpl = new PageImpl<>(productDTOS, sorting, productPage.getTotalElements());

        return ResponseEntity.ok(pageImpl);
    }

    @GetMapping("/get-products-for-bundling")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getProductsForBundling() {
        List<ProductDTO> productDTOS = this.mapProducts(productService.getProductsForBundling());
        return ResponseEntity.ok(productDTOS);
    }

    @GetMapping("/get-bundles")
    public ResponseEntity<?> getBundles() {
        List<ProductDTO> productDTOS = this.mapProducts(productService.getProductsWithBundles());
        return ResponseEntity.ok(productDTOS);
    }

    @PutMapping("/save-product/{pid}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateProduct(@RequestBody ProductDTO productDTO, @PathVariable Long pid) {
        ModelMapper modelMapper = new ModelMapper();
        ProductDTO productDTO1 = modelMapper.map(productService.update(productDTO, pid), ProductDTO.class);
        return ResponseEntity.ok(productDTO1);
    }

    @PostMapping("/toggle-promotion")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> togglePromotion(@RequestBody List<ProductDTO> productDTOS) {
        List<Product> products = productService.togglePromotion(productDTOS);
        return ResponseEntity.ok(this.mapProducts(products));
    }

    @PostMapping("/set-sale")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> setSale(@RequestBody SaleRequest saleRequest) {
        List<Product> products = productService.setSale(saleRequest.getProducts(), saleRequest.getSale());
        return ResponseEntity.ok(this.mapProducts(products));
    }

    @PostMapping("/refill-stock")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> refillStock(@RequestBody RefillStockRequest refillStockRequest) {
        List<Product> products = productService.refillStock(refillStockRequest.getProducts(), refillStockRequest.getQuantity());
        return ResponseEntity.ok(this.mapProducts(products));
    }

    @DeleteMapping("/delete-file/{fid}")
    @PreAuthorize("hasRole('ADMIN')")
    public boolean deleteFile(@PathVariable Long fid) throws IOException {
        return productFileService.delete(fid);
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
