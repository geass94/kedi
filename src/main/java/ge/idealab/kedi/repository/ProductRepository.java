package ge.idealab.kedi.repository;

import ge.idealab.kedi.model.product.attribute.Category;
import ge.idealab.kedi.model.enums.Status;
import ge.idealab.kedi.model.product.attribute.Color;
import ge.idealab.kedi.model.product.attribute.Manufacturer;
import ge.idealab.kedi.model.product.Product;
import ge.idealab.kedi.model.product.attribute.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findFirstByIdAndSizeAndStatus(Long id, Size size, Status status);

    Page<Product> findAllByBaseProductIsTrue(Pageable pageable);
    Page<Product> findDistinctByCategoryListInAndColorInAndManufacturerInAndPriceBetweenAndMakeBundleIsFalseAndStatus(Pageable pageable, List<Category> categories, List<Color> colors, List<Manufacturer> manufacturers, BigDecimal min, BigDecimal max, Status status);

//    List<Product> findAllByCategoryListInAndColorInAndManufacturerInAndPriceBetween(List<Category> categories, List<Color> colors, List<Manufacturer> manufacturers, BigDecimal min, BigDecimal max);
    List<Product> findAllByProductVariantIdIn(Long[] ids);
    List<Product> findAllByMakeBundleIsFalseAndStatus(Status status);
    List<Product> findAllByMakeBundleIsTrueAndBundledProductsContainsAndStatus(Product product, Status status);


    List<Product> findByPromotedIsTrueAndStatus(Status status);
    List<Product> findAllByCreatedAtAfterAndMakeBundleIsFalseAndStatus(Date date, Status status);
    List<Product> findAllByCategoryListInAndIdIsNotAndMakeBundleIsFalseAndStatus(List<Category> categories, Long id, Status status);
    List<Product> findAllBySaleIsGreaterThanAndStatus(Float sale, Status status);
    List<Product> findAllBySaleIsGreaterThanAndCountDownIsAfterAndMakeBundleIsFalseAndStatus(Float sale, Date countdown, Status status);

    @Query("SELECT p FROM Product p WHERE p.sale > :sale AND p.countDown = NULL AND p.status = :status AND p.makeBundle = false ORDER BY RAND() ASC")
    List<Product> findSaleOff(@Param("sale") Float sale, @Param("status") Status status);


    @Query("SELECT coalesce(max(p.price), 0) FROM Product p")
    BigDecimal getMaxPrice();
    @Query("SELECT coalesce(min(p.price), 0) FROM Product p")
    BigDecimal getMinPrice();

}
