package ge.idealab.kedi.service;

import ge.idealab.kedi.dto.CarouselDTO;
import ge.idealab.kedi.model.carousel.Carousel;

public interface CarouselService {
    Carousel create(CarouselDTO carouselDTO);
    Carousel getOne(Long id);
}
