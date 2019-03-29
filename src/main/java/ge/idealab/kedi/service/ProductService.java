package ge.idealab.kedi.service;

import ge.idealab.kedi.dto.BundleDTO;
import ge.idealab.kedi.dto.ProductDTO;
import ge.idealab.kedi.model.product.Product;
import ge.idealab.kedi.payload.response.PriceRange;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface ProductService {
    Product create(ProductDTO productDTO);
    Product getOne(Long id);
    Product getOneByParams(Long id, Long sizeId);
    Product update(ProductDTO productDTO, Long id);
    Product createBundle(BundleDTO bundleDTO);

    PriceRange priceRange();

    Page<Product> getPaginatedProducts(Pageable pageable);
    Page<Product> getPaginatedProductsByFilter(Long[] catId, Long[] colorIds, Long[] manuIds, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);

    List<Product> getProductVariants(Long[] variantIds);

    List<Product> togglePromotion(List<ProductDTO> productDTOS);
    List<Product> setSale(List<ProductDTO> productDTOS, Float sale, Date countDown);
    List<Product> refillStock(List<ProductDTO> productDTOS, Long quantity);
    List<Product> getProductsForBundling();

    List<Product> getProductsWithBundles();

    List<Product> getFeaturedProducts();
    List<Product> getBestSaleProducts();
    List<Product> getProductsOnSale();
    List<Product> getRealtedProducts(Long productId);
    List<Product> getNewProducts();

}
