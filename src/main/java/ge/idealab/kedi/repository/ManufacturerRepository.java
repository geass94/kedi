package ge.idealab.kedi.repository;

import ge.idealab.kedi.model.product.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {
    List<Manufacturer> findAllByIdIn(Long[] ids);
}
