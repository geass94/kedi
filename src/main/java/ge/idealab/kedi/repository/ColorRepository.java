package ge.idealab.kedi.repository;

import ge.idealab.kedi.model.enums.Status;
import ge.idealab.kedi.model.product.attribute.Color;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ColorRepository extends JpaRepository<Color, Long> {
    List<Color> findAllByIdIn(Long[] ids);
    List<Color> findAllByStatus(Status status);
}
