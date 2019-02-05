package ge.idealab.kedi.repository;

import ge.idealab.kedi.model.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findAllByBaseProductIsTrue(Pageable pageable);
    List<Product> findAllByBaseProductIsFalseAndProductVariantIdIn(Long[] ids);
}
