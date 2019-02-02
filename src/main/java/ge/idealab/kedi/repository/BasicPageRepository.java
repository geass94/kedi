package ge.idealab.kedi.repository;

import ge.idealab.kedi.model.BasicPage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasicPageRepository extends JpaRepository<BasicPage, Long> {
    BasicPage findByAlias(String alias);
}
