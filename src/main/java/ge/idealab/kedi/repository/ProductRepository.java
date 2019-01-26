package ge.idealab.kedi.repository;

import ge.idealab.kedi.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
