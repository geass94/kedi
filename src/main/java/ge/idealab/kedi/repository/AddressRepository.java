package ge.idealab.kedi.repository;

import ge.idealab.kedi.model.user.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
