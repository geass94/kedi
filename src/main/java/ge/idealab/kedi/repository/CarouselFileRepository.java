package ge.idealab.kedi.repository;

import ge.idealab.kedi.model.carousel.Carousel;
import ge.idealab.kedi.model.carousel.CarouselFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarouselFileRepository extends JpaRepository<CarouselFile, Long> {
    CarouselFile findByName(String name);
    List<CarouselFile> findAllByCarousel(Carousel carousel);
}
