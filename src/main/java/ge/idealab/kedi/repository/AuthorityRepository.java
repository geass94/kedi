package ge.idealab.kedi.repository;

import ge.idealab.kedi.model.user.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    List<Authority> findAllByIdIn(List<Long> authIds);
}