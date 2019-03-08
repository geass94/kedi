package ge.idealab.kedi.repository;

import ge.idealab.kedi.model.Category;
import ge.idealab.kedi.model.enums.Status;
import ge.idealab.kedi.model.product.Color;
import ge.idealab.kedi.model.product.Manufacturer;
import ge.idealab.kedi.model.product.Product;
import ge.idealab.kedi.payload.response.PriceRange;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findAllByBaseProductIsTrue(Pageable pageable);
    Page<Product> findDistinctByCategoryListInAndColorInAndManufacturerInAndPriceBetween(Pageable pageable, List<Category> categories, List<Color> colors, List<Manufacturer> manufacturers, BigDecimal min, BigDecimal max);

    List<Product> findAllByCategoryListInAndColorInAndManufacturerInAndPriceBetween(List<Category> categories, List<Color> colors, List<Manufacturer> manufacturers, BigDecimal min, BigDecimal max);
    List<Product> findAllByProductVariantIdIn(Long[] ids);
    List<Product> findAllByBundleIsNullAndStatus(Status status);
    List<Product> findAllByBundleIsNotNullAndStatus(Status status);

    @Query("SELECT coalesce(max(p.price), 0) FROM Product p")
    BigDecimal getMaxPrice();
    @Query("SELECT coalesce(min(p.price), 0) FROM Product p")
    BigDecimal getMinPrice();

}
