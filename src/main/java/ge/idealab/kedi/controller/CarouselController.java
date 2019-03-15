package ge.idealab.kedi.controller;

import ge.idealab.kedi.dto.CarouselDTO;
import ge.idealab.kedi.dto.CarouselFileDTO;
import ge.idealab.kedi.model.carousel.Carousel;
import ge.idealab.kedi.model.carousel.CarouselFile;
import ge.idealab.kedi.service.CarouselService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/carousel")
public class CarouselController {
    @Autowired
    private CarouselService carouselService;

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllCarousels() {
        return ResponseEntity.ok(this.mapCarousels(carouselService.getAll()));
    }

    @GetMapping("/get-by-area/{area}")
    public ResponseEntity<?> getCarouselByArea(@PathVariable String area) {
        return ResponseEntity.ok(this.mapCarousel(carouselService.getOneByArea(area)));
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<?> getCarouselById(@PathVariable Long id) {
        return ResponseEntity.ok(this.mapCarousel(carouselService.getOne(id)));
    }

    private List<CarouselDTO> mapCarousels(List<Carousel> carousels) {
        ModelMapper modelMapper = new ModelMapper();
        List<CarouselDTO> carouselDTOS = new ArrayList<>();
        for (Carousel c : carousels) {
            CarouselDTO x = modelMapper.map(c, CarouselDTO.class);
            x.setCarouselFiles(this.mapCarouselFiles(c.getCarouselFiles()));
            carouselDTOS.add(x);
        }
        return carouselDTOS;
    }

    private CarouselDTO mapCarousel(Carousel carousel) {
        ModelMapper modelMapper = new ModelMapper();
        CarouselDTO c = modelMapper.map(carousel, CarouselDTO.class);
        c.setCarouselFiles(this.mapCarouselFiles(carousel.getCarouselFiles()));
        return c;
    }

    private List<CarouselFileDTO> mapCarouselFiles(List<CarouselFile> carouselFiles) {
        ModelMapper modelMapper = new ModelMapper();
        List<CarouselFileDTO> carouselFileDTOS = new ArrayList<>();
        for (CarouselFile c : carouselFiles) {
            carouselFileDTOS.add(modelMapper.map(c, CarouselFileDTO.class));
        }
        return carouselFileDTOS;
    }
}
