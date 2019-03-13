package ge.idealab.kedi.service;

import ge.idealab.kedi.dto.CarouselFileDTO;
import ge.idealab.kedi.model.carousel.Carousel;
import ge.idealab.kedi.model.carousel.CarouselFile;

import java.io.IOException;
import java.util.List;

public interface CarouselFileService {
    CarouselFile create(CarouselFile productFile);
    CarouselFile getOne(Long id);
    CarouselFile getOneByName(String name);
    List<CarouselFile> getCarouselFilesByCarousel(Carousel carousel);
    Boolean delete(Long file) throws IOException;

    List<CarouselFile> addCaptions(List<CarouselFileDTO> carouselFileDTOS);
}
