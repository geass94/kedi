package ge.idealab.kedi.repository;

import ge.idealab.kedi.model.Category;
import ge.idealab.kedi.model.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllByParentIsNull();
    List<Category> findAllByParentIsNullAndStatus(Status status);
    List<Category> findAllByStatus(Status status);
    List<Category> findAllByStatusOrderByParent(Status status);
    List<Category> findAllByIdIn(Long[] ids);
}
