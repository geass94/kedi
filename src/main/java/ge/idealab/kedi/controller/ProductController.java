package ge.idealab.kedi.controller;

import ge.idealab.kedi.dto.*;
import ge.idealab.kedi.model.Category;
import ge.idealab.kedi.model.product.Color;
import ge.idealab.kedi.model.product.Manufacturer;
import ge.idealab.kedi.model.product.Product;
import ge.idealab.kedi.model.product.ProductFile;
import ge.idealab.kedi.repository.CategoryRepository;
import ge.idealab.kedi.repository.ColorRepository;
import ge.idealab.kedi.repository.ManufacturerRepository;
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
    @Autowired
    private ColorRepository colorRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ManufacturerRepository manufacturerRepository;

    @PostMapping("/add-product")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addProduct(@RequestBody @Valid ProductDTO productDTO){
        ModelMapper modelMapper = new ModelMapper();
        Product product = productService.create(productDTO);
        return ResponseEntity.ok(modelMapper.map(product, ProductDTO.class));
    }

    @PostMapping("/add-categories")
    public ResponseEntity<?> addCategories(@RequestBody List<CategoryDTO> categoryDTOList){
        ModelMapper modelMapper = new ModelMapper();
        List<Category> categories = productService.addCategories(categoryDTOList);
        List<CategoryDTO> categoryDTOS = new ArrayList<>();
        for(Category category : categories){
            categoryDTOS.add(modelMapper.map(category, CategoryDTO.class));
        }
        return ResponseEntity.ok(categoryDTOS);
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
    public ResponseEntity<?> getProducts(){
        ModelMapper modelMapper = new ModelMapper();
        Pageable sortedByName =
                PageRequest.of(0, 50, Sort.by("id").descending());

        List<ProductDTO> productDTOS = new ArrayList<>();
        Page<Product> productPage = productService.getPaginatedProducts(sortedByName);

        for(Product product: productPage.getContent()){
            ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
            productDTO.setProductFiles(mapFiles(product));
            productDTOS.add(productDTO);
        }

//        return ResponseEntity.ok(new PageImpl<>(productDTOS, sortedByName, productPage.getTotalElements()));
        return ResponseEntity.ok(productDTOS);
    }

    @GetMapping("/get-product-variants")
    public ResponseEntity<?> getProductVariants(@RequestParam("ids") String[] variantIds){

        List<Product> products = productService.getProductVariants(stringArrayToLongArray(variantIds));
        List<ProductDTO> productDTOS = this.mapProducts(products);

        return ResponseEntity.ok(productDTOS);
    }

    @GetMapping("/get-colors")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getColors(){
        ModelMapper modelMapper = new ModelMapper();
        List<ColorDTO> dtos = new ArrayList<>();
        for(Color model: colorRepository.findAll()){
            dtos.add(modelMapper.map(model, ColorDTO.class));
        }
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/get-categories")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getCategories(){
        ModelMapper modelMapper = new ModelMapper();
        List<CategoryDTO> dtos = new ArrayList<>();
        for(Category model: categoryRepository.findAll()){
            dtos.add(modelMapper.map(model, CategoryDTO.class));
        }
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/get-manufacturers")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getManufacturers(){
        ModelMapper modelMapper = new ModelMapper();
        List<ManufacturerDTO> dtos = new ArrayList<>();
        for(Manufacturer model: manufacturerRepository.findAll()){
            dtos.add(modelMapper.map(model, ManufacturerDTO.class));
        }
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/get-products-by-filter")
    public ResponseEntity<?> getProductsByFilter(@RequestParam("categories") String[] categorIds,
                                                 @RequestParam("colors") String[] colorIds,
                                                 @RequestParam("manufacturers") String[] manufacturerIds,
                                                 @RequestParam("min_price") String min_price,
                                                 @RequestParam("max_price") String max_price){
        Long[] categories = this.stringArrayToLongArray(categorIds);
        Long[] colors = this.stringArrayToLongArray(colorIds);
        Long[] manufacturers = this.stringArrayToLongArray(manufacturerIds);
        BigDecimal maxPrice = BigDecimal.valueOf(Double.valueOf(max_price));
        BigDecimal minPrice = BigDecimal.valueOf(Double.valueOf(min_price));

        List<Product> products = productService.getProductsByFilter(categories, colors, manufacturers, minPrice, maxPrice);
        List<ProductDTO> productDTOS = this.mapProducts(products);

        return ResponseEntity.ok(productDTOS);
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
