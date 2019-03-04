package ge.idealab.kedi.repository;

import ge.idealab.kedi.model.Category;
import ge.idealab.kedi.model.product.Color;
import ge.idealab.kedi.model.product.Manufacturer;
import ge.idealab.kedi.model.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findAllByBaseProductIsTrue(Pageable pageable);
    List<Product> findAllByProductVariantIdIn(Long[] ids);

    Page<Product> findAllByCategoryListInAndColorInAndManufacturerInAndPriceBetween(Pageable pageable, List<Category> categories, List<Color> colors, List<Manufacturer> manufacturers, BigDecimal min, BigDecimal max);

    List<Product> findAllByCategoryListInAndColorInAndManufacturerInAndPriceBetween(List<Category> categories, List<Color> colors, List<Manufacturer> manufacturers, BigDecimal min, BigDecimal max);
}
