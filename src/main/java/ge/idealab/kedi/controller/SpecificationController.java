package ge.idealab.kedi.controller;

import ge.idealab.kedi.dto.CategoryDTO;
import ge.idealab.kedi.dto.ColorDTO;
import ge.idealab.kedi.dto.ManufacturerDTO;
import ge.idealab.kedi.model.product.Category;
import ge.idealab.kedi.model.enums.Status;
import ge.idealab.kedi.model.product.Color;
import ge.idealab.kedi.model.product.Manufacturer;
import ge.idealab.kedi.repository.CategoryRepository;
import ge.idealab.kedi.repository.ColorRepository;
import ge.idealab.kedi.repository.ManufacturerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/specification")
//@CrossOrigin(origins = "http://localhost:4200")
public class SpecificationController {
    @Autowired
    private ColorRepository colorRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ManufacturerRepository manufacturerRepository;

    @GetMapping("/get-colors")
    public ResponseEntity<?> getColors(){
        ModelMapper modelMapper = new ModelMapper();
        List<ColorDTO> dtos = new ArrayList<>();
        for(Color model: colorRepository.findAllByStatus(Status.ACTIVE)){
            dtos.add(modelMapper.map(model, ColorDTO.class));
        }
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/get-categories")
    public ResponseEntity<?> getCategories(){
        ModelMapper modelMapper = new ModelMapper();
        List<CategoryDTO> dtos = new ArrayList<>();
        for(Category model: categoryRepository.findAllByStatusOrderByParent(Status.ACTIVE)){
            dtos.add(modelMapper.map(model, CategoryDTO.class));
        }
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/get-parent-categories")
    public ResponseEntity<?> getParentCategories(){
        ModelMapper modelMapper = new ModelMapper();
        List<CategoryDTO> dtos = new ArrayList<>();
        for(Category model: categoryRepository.findAllByParentIsNullAndStatus(Status.ACTIVE)){
            dtos.add(modelMapper.map(model, CategoryDTO.class));
        }
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/get-manufacturers")
    public ResponseEntity<?> getManufacturers(){
        ModelMapper modelMapper = new ModelMapper();
        List<ManufacturerDTO> dtos = new ArrayList<>();
        for(Manufacturer model: manufacturerRepository.findAllByStatus(Status.ACTIVE)){
            dtos.add(modelMapper.map(model, ManufacturerDTO.class));
        }
        return ResponseEntity.ok(dtos);
    }
}
