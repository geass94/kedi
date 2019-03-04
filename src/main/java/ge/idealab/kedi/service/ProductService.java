package ge.idealab.kedi.service;

import ge.idealab.kedi.dto.ProductDTO;
import ge.idealab.kedi.model.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    Product create(ProductDTO productDTO);
    Product getOne(Long id);
    Page<Product> getPaginatedProducts(Pageable pageable);
    List<Product> getProductVariants(Long[] variantIds);
    List<Product> getProductsByFilter(Long[] catId, Long[] colorIds, Long[] manuIds, BigDecimal minPrice, BigDecimal maxPrice);
    Page<Product> getPaginatedProductsByFilter(Long[] catId, Long[] colorIds, Long[] manuIds, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);

    Product update(ProductDTO productDTO, Long id);}
