package ge.idealab.kedi.service.impl;

import ge.idealab.kedi.dto.CarouselFileDTO;
import ge.idealab.kedi.model.carousel.Carousel;
import ge.idealab.kedi.model.carousel.CarouselFile;
import ge.idealab.kedi.repository.CarouselFileRepository;
import ge.idealab.kedi.service.CarouselFileService;
import ge.idealab.kedi.service.FileService;
import org.apache.commons.io.FileUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CarouselFileServiceImpl implements CarouselFileService {
    @Autowired
    private CarouselFileRepository carouselFileRepository;
    @Autowired
    private FileService fileService;

    @Override
    public CarouselFile create(CarouselFile carouselFile) {
        return carouselFileRepository.save(carouselFile);
    }

    @Override
    public CarouselFile getOne(Long id) {
        return carouselFileRepository.getOne(id);
    }

    @Override
    public CarouselFile getOneByName(String name) {
        return carouselFileRepository.findByName(name);
    }

    @Override
    public List<CarouselFile> getCarouselFilesByCarousel(Carousel pcarousel) {
        return carouselFileRepository.findAllByCarousel(pcarousel);
    }

    @Override
    public Boolean delete(Long fid) throws IOException {
        CarouselFile carouselFile = this.getOne(fid);
        Resource resource = fileService.loadFileAsResource(carouselFile.getName());
        File fileToDelete = FileUtils.getFile(resource.getFile().getAbsolutePath());
        boolean result = FileUtils.deleteQuietly(fileToDelete);
        if (result) {
            this.carouselFileRepository.delete(carouselFile);
        }
        return result;
    }

    @Override
    public List<CarouselFile> addCaptions(List<CarouselFileDTO> carouselFileDTOS) {
        List<CarouselFile> carouselFiles = new ArrayList<>();
        for (CarouselFileDTO c : carouselFileDTOS) {
            CarouselFile o = carouselFileRepository.getOne(c.getId());
            o.setCaption(c.getCaption());
            o.setExternalURL(c.getExternalURL());
            carouselFiles.add(o);
        }
        carouselFiles = carouselFileRepository.saveAll(carouselFiles);
        return carouselFiles;
    }

}
