package ge.idealab.kedi.repository;

import ge.idealab.kedi.model.banner.Banner;
import ge.idealab.kedi.model.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BannerRepository extends JpaRepository<Banner, Long> {
    Banner findByStatus(Status status);
    Banner findByAreaAndStatus(String area, Status status);
    Banner findFirstByAreaAndStatus(String area, Status status);
    List<Banner> findAllByAreaAndStatus(String area, Status status);

    List<Banner> findAllByStatusIn(List<Status> statuses);
}
