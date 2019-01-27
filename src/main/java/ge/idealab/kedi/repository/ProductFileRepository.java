package ge.idealab.kedi.repository;

import ge.idealab.kedi.model.product.Product;
import ge.idealab.kedi.model.product.ProductFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductFileRepository extends JpaRepository<ProductFile, Long> {
    ProductFile findByName(String name);
    List<ProductFile> findAllByProduct(Product product);
}
