package ge.idealab.kedi.repository;

import ge.idealab.kedi.model.enums.Status;
import ge.idealab.kedi.model.product.attribute.Size;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SizeRepository extends JpaRepository<Size, Long> {
    List<Size> findAllByStatus(Status status);
}
