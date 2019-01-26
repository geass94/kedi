package ge.idealab.kedi.repository;

import ge.idealab.kedi.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {
}
