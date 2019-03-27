package ge.idealab.kedi.repository;

import ge.idealab.kedi.model.BasicPage;
import ge.idealab.kedi.model.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BasicPageRepository extends JpaRepository<BasicPage, Long> {
    BasicPage findByAlias(String alias);
    Page<BasicPage> findAllByStatus(Pageable pageable, Status status);

    @Query("SELECT count(id) FROM BasicPage b WHERE b.alias = :alias")
    Integer checkAlias(@Param("alias") String alias);
}
