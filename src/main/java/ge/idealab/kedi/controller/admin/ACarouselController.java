package ge.idealab.kedi.controller.admin;

import ge.idealab.kedi.dto.CarouselDTO;
import ge.idealab.kedi.dto.CarouselFileDTO;
import ge.idealab.kedi.model.carousel.Carousel;
import ge.idealab.kedi.model.carousel.CarouselFile;
import ge.idealab.kedi.service.CarouselFileService;
import ge.idealab.kedi.service.CarouselService;
import ge.idealab.kedi.service.FileService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin/carousel")
public class ACarouselController {
    @Autowired
    private CarouselService carouselService;
    @Autowired
    private FileService fileService;
    @Autowired
    private CarouselFileService carouselFileService;

    @PostMapping("/add-carousel")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addCarousel(@RequestBody CarouselDTO carouselDTO){
        return ResponseEntity.ok(this.mapCarousel(carouselService.create(carouselDTO)));
    }

    @PutMapping("/switch-status/{cid}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> switchStatus(@PathVariable Long cid, @RequestBody CarouselDTO carouselDTO) {
        return ResponseEntity.ok(this.mapCarousel(carouselService.switchStatus(carouselDTO, cid)));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteCarousel(@PathVariable Long id) {
        carouselService.delete(id);
    }

    @PostMapping("/add-captions")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addCaptions(@RequestBody List<CarouselFileDTO> carouselFileDTOS) {
        return ResponseEntity.ok(this.mapCarouselFiles(carouselFileService.addCaptions(carouselFileDTOS)));
    }

    @PostMapping(path = "/add-carousel-file", consumes = {"multipart/form-data"})
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addProductFile(@Valid @RequestPart("carousel-id") String carouselId, @Valid @RequestPart("files") MultipartFile[] multipartFiles){
        ModelMapper modelMapper = new ModelMapper();
        Carousel carousel = carouselService.getOne(Long.valueOf(carouselId));
        List<CarouselFileDTO> carouselFileDTOS = new ArrayList<>();
        for(MultipartFile multipartFile: multipartFiles){
            CarouselFile carouselFile = modelMapper.map(fileService.uploadFile(multipartFile), CarouselFile.class);
            carouselFile.setCarousel(carousel);
            carouselFile = carouselFileService.create(carouselFile);
            carouselFileDTOS.add(modelMapper.map(carouselFile, CarouselFileDTO.class));
        }
        return ResponseEntity.ok(carouselFileDTOS);
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
