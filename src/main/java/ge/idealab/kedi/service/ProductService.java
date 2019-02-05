package ge.idealab.kedi.service;

import ge.idealab.kedi.dto.ProductDTO;
import ge.idealab.kedi.model.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    Product create(ProductDTO productDTO);
    Product getOne(Long id);
    Page<Product> getPaginatedProducts(Pageable pageable);
    List<Product> getProductVariants(Long[] variantIds);
}
