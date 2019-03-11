package ge.idealab.kedi.controller.admin;

import ge.idealab.kedi.dto.CategoryDTO;
import ge.idealab.kedi.dto.ColorDTO;
import ge.idealab.kedi.dto.ManufacturerDTO;
import ge.idealab.kedi.model.Category;
import ge.idealab.kedi.model.product.Color;
import ge.idealab.kedi.model.product.Manufacturer;
import ge.idealab.kedi.repository.CategoryRepository;
import ge.idealab.kedi.repository.ColorRepository;
import ge.idealab.kedi.repository.ManufacturerRepository;
import ge.idealab.kedi.service.SpecificationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin/specification")
@CrossOrigin(origins = "https://admin.kedi.ge")
public class ASpecificationController {
    @Autowired
    private SpecificationService specificationService;

    @PostMapping("/add-categories")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addCategories(@RequestBody List<CategoryDTO> categoryDTOList){
        ModelMapper modelMapper = new ModelMapper();
        List<Category> categories = specificationService.addCategories(categoryDTOList);
        List<CategoryDTO> categoryDTOS = new ArrayList<>();
        for(Category category : categories){
            categoryDTOS.add(modelMapper.map(category, CategoryDTO.class));
        }
        return ResponseEntity.ok(categoryDTOS);
    }

    @PostMapping("/add-category")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addCategory(@RequestBody CategoryDTO categoryDTO){
        ModelMapper modelMapper = new ModelMapper();
        Category category = specificationService.addCategory(categoryDTO);
        return ResponseEntity.ok(modelMapper.map(category, CategoryDTO.class));
    }

    @PutMapping("/save-category/{cid}")
    @PreAuthorize("hasRole('ADMIN')")
    public void saveCategory(@RequestBody CategoryDTO categoryDTO, @PathVariable Long cid) {
        specificationService.saveCategory(categoryDTO, cid);
    }

    @DeleteMapping("/delete-category/{cid}")
    @PreAuthorize("hasRole('ADMIN')")
    public boolean deleteCategory(@PathVariable Long cid) {
        specificationService.deleteCategory(cid);
        return true;
    }

    @PostMapping("/add-color")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addColor(@RequestBody ColorDTO colorDTO){
        ModelMapper modelMapper = new ModelMapper();
        Color color = specificationService.addColor(colorDTO);
        return ResponseEntity.ok(modelMapper.map(color, ColorDTO.class));
    }

    @PutMapping("/save-color/{cid}")
    @PreAuthorize("hasRole('ADMIN')")
    public void saveColor(@RequestBody ColorDTO colorDTO, @PathVariable Long cid){
        specificationService.saveColor(colorDTO, cid);
    }

    @DeleteMapping("/delete-color/{cid}")
    @PreAuthorize("hasRole('ADMIN')")
    public boolean deleteColor(@PathVariable Long cid){
        specificationService.deleteColor(cid);
        return true;
    }

    @PostMapping("/add-manufacturer")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addManufacturer(@RequestBody ManufacturerDTO manufacturerDTO){
        ModelMapper modelMapper = new ModelMapper();
        Manufacturer manufacturer = specificationService.addManufacturer(manufacturerDTO);
        return ResponseEntity.ok(modelMapper.map(manufacturer, ManufacturerDTO.class));
    }

    @PutMapping("/save-manufacturer/{cid}")
    @PreAuthorize("hasRole('ADMIN')")
    public void saveManufacturer(@RequestBody ManufacturerDTO manufacturerDTO, @PathVariable Long cid){
        specificationService.saveManufacturer(manufacturerDTO, cid);
    }

    @DeleteMapping("/delete-manufacturer/{cid}")
    @PreAuthorize("hasRole('ADMIN')")
    public boolean deleteManufacturer(@PathVariable Long cid){
        specificationService.deleteManufacturer(cid);
        return true;
    }
}
