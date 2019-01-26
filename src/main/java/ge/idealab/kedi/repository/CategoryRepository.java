package ge.idealab.kedi.repository;

import ge.idealab.kedi.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
