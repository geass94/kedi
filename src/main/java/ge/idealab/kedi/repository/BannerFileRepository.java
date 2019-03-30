package ge.idealab.kedi.repository;

import ge.idealab.kedi.model.banner.Banner;
import ge.idealab.kedi.model.banner.BannerFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BannerFileRepository extends JpaRepository<BannerFile, Long> {
    BannerFile findByName(String name);
    List<BannerFile> findAllByBanner(Banner banner);
}
