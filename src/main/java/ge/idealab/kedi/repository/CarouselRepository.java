package ge.idealab.kedi.repository;

import ge.idealab.kedi.model.carousel.Carousel;
import ge.idealab.kedi.model.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarouselRepository extends JpaRepository<Carousel, Long> {
    Carousel findByStatus(Status status);
}
