package ge.idealab.kedi.controller;

import ge.idealab.kedi.dto.CategoryDTO;
import ge.idealab.kedi.dto.ColorDTO;
import ge.idealab.kedi.dto.ManufacturerDTO;
import ge.idealab.kedi.dto.SizeDTO;
import ge.idealab.kedi.model.product.attribute.Category;
import ge.idealab.kedi.model.enums.Status;
import ge.idealab.kedi.model.product.attribute.Color;
import ge.idealab.kedi.model.product.attribute.Manufacturer;
import ge.idealab.kedi.model.product.attribute.Size;
import ge.idealab.kedi.repository.CategoryRepository;
import ge.idealab.kedi.repository.ColorRepository;
import ge.idealab.kedi.repository.ManufacturerRepository;
import ge.idealab.kedi.repository.SizeRepository;
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
    @Autowired
    private SizeRepository sizeRepository;

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
        for(Category model: categoryRepository.findAllByStatusOrderByParentDesc(Status.ACTIVE)){
            dtos.add(modelMapper.map(model, CategoryDTO.class));
        }
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/get-parent-categories")
    public ResponseEntity<?> getParentCategories(){
        ModelMapper modelMapper = new ModelMapper();
        List<CategoryDTO> dtos = new ArrayList<>();
        for(Category model : categoryRepository.findAllByParentIsNullAndStatusOrderByWeightAsc(Status.ACTIVE)){
            dtos.add(modelMapper.map(model, CategoryDTO.class));
        }
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/get-categories-by-parent/{pid}")
    public ResponseEntity<?> getCategoriesByParent(@PathVariable Long pid) {
        List<CategoryDTO> categories = this.getChildren(categoryRepository.getOne(pid));
        return ResponseEntity.ok(categories);
    }

    List<CategoryDTO> getChildren(Category category) {
        ModelMapper modelMapper = new ModelMapper();
        List<CategoryDTO> categoryDTOList = new ArrayList<>();
        List<Category> children = categoryRepository.findAllByParentIsAndStatus(category, Status.ACTIVE);
        if (children.size() > 0) {
            for (Category child : children) {
                categoryDTOList.add(modelMapper.map(child, CategoryDTO.class));
                this.getChildren(child);
            }
        }
        return categoryDTOList;
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

    @GetMapping("/get-sizes")
    public ResponseEntity<?> getSizes(){
        ModelMapper modelMapper = new ModelMapper();
        List<SizeDTO> dtos = new ArrayList<>();
        for(Size model: sizeRepository.findAllByStatus(Status.ACTIVE)){
            dtos.add(modelMapper.map(model, SizeDTO.class));
        }
        return ResponseEntity.ok(dtos);
    }
}
