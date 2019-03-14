package ge.idealab.kedi.service.impl;

import ge.idealab.kedi.dto.CarouselDTO;
import ge.idealab.kedi.model.carousel.Carousel;
import ge.idealab.kedi.model.enums.Status;
import ge.idealab.kedi.repository.CarouselRepository;
import ge.idealab.kedi.service.CarouselService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarouselServiceImpl implements CarouselService {
    @Autowired
    private CarouselRepository carouselRepository;

    @Override
    public Carousel create(CarouselDTO carouselDTO) {
        ModelMapper modelMapper = new ModelMapper();
        Carousel carousel = modelMapper.map(carouselDTO, Carousel.class);
        carousel.setStatus(Status.ACTIVE);
        carousel = carouselRepository.save(carousel);
        return carousel;
    }

    @Override
    public Carousel getOne(Long id) {
        return carouselRepository.getOne(id);
    }

    @Override
    public Carousel getOneByArea(String area) {
        return carouselRepository.findByAreaAndStatus(area, Status.ACTIVE);
    }

    @Override
    public List<Carousel> getAll() {
        return carouselRepository.findAllByStatus(Status.ACTIVE);
    }
}
