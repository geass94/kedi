package ge.idealab.kedi.repository;

import ge.idealab.kedi.model.user.PersonalInformation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonalInformationRepository extends JpaRepository<PersonalInformation, Long> {
}
