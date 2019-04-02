package ge.idealab.kedi.repository;

import ge.idealab.kedi.model.enums.Status;
import ge.idealab.kedi.model.product.attribute.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {
    List<Manufacturer> findAllByIdIn(List<Long> ids);
    List<Manufacturer> findAllByStatus(Status status);
}
