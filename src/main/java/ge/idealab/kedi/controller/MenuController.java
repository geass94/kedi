package ge.idealab.kedi.controller;

import ge.idealab.kedi.dto.CategoryDTO;
import ge.idealab.kedi.dto.ColorDTO;
import ge.idealab.kedi.dto.ManufacturerDTO;
import ge.idealab.kedi.dto.MenuDTO;
import ge.idealab.kedi.model.product.attribute.Category;
import ge.idealab.kedi.model.enums.Status;
import ge.idealab.kedi.model.product.attribute.Color;
import ge.idealab.kedi.model.product.attribute.Manufacturer;
import ge.idealab.kedi.repository.CategoryRepository;
import ge.idealab.kedi.repository.ColorRepository;
import ge.idealab.kedi.repository.ManufacturerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/menu")
//@CrossOrigin(origins = "http://localhost:4200")
public class MenuController {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ManufacturerRepository manufacturerRepository;
    @Autowired
    private ColorRepository colorRepository;

    @GetMapping("/get-sidebar")
    public ResponseEntity<?> getMenu(){
        ModelMapper modelMapper = new ModelMapper();
        MenuDTO menu = new MenuDTO();
        List<CategoryDTO> categoryDTOList = new ArrayList<>();
        List<ManufacturerDTO> manufacturerDTOList = new ArrayList<>();
        List<ColorDTO> colorDTOList = new ArrayList<>();
        List<Category> categories = categoryRepository.findAllByStatus(Status.ACTIVE);
        List<Color> colors = colorRepository.findAllByStatus(Status.ACTIVE);
        List<Manufacturer> manufacturers = manufacturerRepository.findAllByStatus(Status.ACTIVE);

        for (Category category : categories){
            categoryDTOList.add(modelMapper.map(category, CategoryDTO.class));
        }
        for(Color color : colors){
            colorDTOList.add(modelMapper.map(color, ColorDTO.class));
        }
        for(Manufacturer manufacturer : manufacturers){
            manufacturerDTOList.add(modelMapper.map(manufacturer, ManufacturerDTO.class));
        }

        menu.setCategories(categoryDTOList);
        menu.setColors(colorDTOList);
        menu.setManufacturers(manufacturerDTOList);

        return ResponseEntity.ok(menu);
    }
}
