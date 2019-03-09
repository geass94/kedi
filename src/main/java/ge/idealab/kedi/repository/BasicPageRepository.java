package ge.idealab.kedi.repository;

import ge.idealab.kedi.model.BasicPage;
import ge.idealab.kedi.model.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasicPageRepository extends JpaRepository<BasicPage, Long> {
    BasicPage findByAlias(String alias);
    Page<BasicPage> findAllByStatus(Pageable pageable, Status status);
}
