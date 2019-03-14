package ge.idealab.kedi.repository;

import ge.idealab.kedi.model.carousel.Carousel;
import ge.idealab.kedi.model.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarouselRepository extends JpaRepository<Carousel, Long> {
    Carousel findByStatus(Status status);
    Carousel findByAreaAndStatus(String area, Status status);
    List<Carousel> findAllByStatus(Status status);
}
