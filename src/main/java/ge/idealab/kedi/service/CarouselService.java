package ge.idealab.kedi.service;

import ge.idealab.kedi.dto.CarouselDTO;
import ge.idealab.kedi.model.carousel.Carousel;

import java.util.List;

public interface CarouselService {
    Carousel create(CarouselDTO carouselDTO);
    Carousel getOne(Long id);
    Carousel getOneByArea(String area);
    List<Carousel> getAll();
}
