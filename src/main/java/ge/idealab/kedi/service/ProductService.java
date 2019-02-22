package ge.idealab.kedi.service;

import ge.idealab.kedi.dto.CategoryDTO;
import ge.idealab.kedi.dto.ProductDTO;
import ge.idealab.kedi.model.Category;
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

    List<Category> addCategories(List<CategoryDTO> categoryDTOS);
}
