package ge.idealab.kedi.repository;

import ge.idealab.kedi.model.product.attribute.Category;
import ge.idealab.kedi.model.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllByParentIsNull();
    List<Category> findAllByParentIsNullAndStatusOrderByWeightAsc(Status status);
    List<Category> findAllByStatusOrderByWeightAsc(Status status);
    List<Category> findAllByStatusOrderByParentDesc(Status status);
    List<Category> findAllByIdIn(List<Long> ids);

    List<Category> findAllByParentIsAndStatus(Category category, Status status);
}
