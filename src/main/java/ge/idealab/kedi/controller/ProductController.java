package ge.idealab.kedi.controller;

import ge.idealab.kedi.dto.ProductDTO;
import ge.idealab.kedi.dto.ProductFileDTO;
import ge.idealab.kedi.model.product.Product;
import ge.idealab.kedi.model.product.ProductFile;
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
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> addProduct(@RequestBody @Valid ProductDTO productDTO){
        ModelMapper modelMapper = new ModelMapper();
        Product product = productService.create(productDTO);
        return ResponseEntity.ok(modelMapper.map(product, ProductDTO.class));
    }

    @PostMapping("/add-product-file")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> addProductFile(@Valid @RequestParam("product-id") Long productId, @Valid @RequestParam("files") MultipartFile[] multipartFiles){
        ModelMapper modelMapper = new ModelMapper();
        Product product = productService.getOne(productId);
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
            ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
            /* START Map recent product files entity to Protuct files DTO START */
            List<ProductFileDTO> productFileDTOS = new ArrayList<>();
            for(ProductFile productFile: product.getProductFileList()){
                productFileDTOS.add(modelMapper.map(productFile, ProductFileDTO.class));
            }
            productDTO.setProductFiles(productFileDTOS);
            /* END Map recent product files entity to Protuct files DTO END */
            productDTOS.add(productDTO);
        }

//        return ResponseEntity.ok(new PageImpl<>(productDTOS, sortedByName, productPage.getTotalElements()));
        return ResponseEntity.ok(productDTOS);
    }
}
