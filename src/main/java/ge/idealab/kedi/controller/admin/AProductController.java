package ge.idealab.kedi.controller.admin;

import ge.idealab.kedi.dto.BundleDTO;
import ge.idealab.kedi.dto.ProductDTO;
import ge.idealab.kedi.dto.ProductFileDTO;
import ge.idealab.kedi.model.product.Product;
import ge.idealab.kedi.model.product.ProductFile;
import ge.idealab.kedi.payload.request.RefillStockRequest;
import ge.idealab.kedi.payload.request.SaleRequest;
import ge.idealab.kedi.repository.ColorRepository;
import ge.idealab.kedi.service.FileService;
import ge.idealab.kedi.service.ProductFileService;
import ge.idealab.kedi.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin/product")
@CrossOrigin(origins = "https://admin.kedi.ge")
public class AProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductFileService productFileService;
    @Autowired
    private FileService fileService;
    @Autowired
    private ColorRepository colorRepository;

    @PostMapping("/add-product")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addProduct(@RequestBody @Valid ProductDTO productDTO){
        ModelMapper modelMapper = new ModelMapper();
        Product product = productService.create(productDTO);
        return ResponseEntity.ok(modelMapper.map(product, ProductDTO.class));
    }

    @PostMapping(path = "/add-product-file", consumes = {"multipart/form-data"})
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addProductFile(@Valid @RequestPart("product-id") String productId, @Valid @RequestPart("color-id") String colorId, @Valid @RequestPart("files") MultipartFile[] multipartFiles){
        ModelMapper modelMapper = new ModelMapper();
        Product p = productService.getOne(Long.valueOf(productId));
        List<Product> products = productService.getProductVariants(p.getProductVariantIds());
        List<ProductFileDTO> productFileDTOS = new ArrayList<>();
        for(Product product : products) {
            if (product.getColor() == colorRepository.getOne(Long.valueOf(colorId))) {
                for(MultipartFile multipartFile: multipartFiles){
                    ProductFile productFile = modelMapper.map(fileService.uploadFile(multipartFile), ProductFile.class);
                    productFile.setProduct(product);
                    productFile = productFileService.create(productFile);
                    productFileDTOS.add(modelMapper.map(productFile, ProductFileDTO.class));
                }
            }
        }
        return ResponseEntity.ok(productFileDTOS);
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
        List<Product> products = productService.setSale(saleRequest.getProducts(), saleRequest.getSale(), saleRequest.getCountDown());
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

    @GetMapping("/get-products-for-bundling")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getProductsForBundling() {
        List<ProductDTO> productDTOS = this.mapProducts(productService.getProductsForBundling());
        return ResponseEntity.ok(productDTOS);
    }

    @PostMapping("/add-bundle")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addBundle(@RequestBody BundleDTO bundleDTO) {
        ModelMapper modelMapper = new ModelMapper();
        ProductDTO productDTO = modelMapper.map(productService.createBundle(bundleDTO), ProductDTO.class);
        return ResponseEntity.ok(productDTO);
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
