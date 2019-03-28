package ge.idealab.kedi.service;

import ge.idealab.kedi.dto.CategoryDTO;
import ge.idealab.kedi.dto.ColorDTO;
import ge.idealab.kedi.dto.ManufacturerDTO;
import ge.idealab.kedi.dto.SizeDTO;
import ge.idealab.kedi.model.product.attribute.Category;
import ge.idealab.kedi.model.product.attribute.Color;
import ge.idealab.kedi.model.product.attribute.Manufacturer;
import ge.idealab.kedi.model.product.attribute.Size;

import java.util.List;

public interface SpecificationService {
    List<Category> addCategories(List<CategoryDTO> categoryDTOS);
    Category addCategory(CategoryDTO categoryDTO);
    void deleteCategory(Long id);
    void saveCategory(CategoryDTO categoryDTO, Long id);

    Color addColor(ColorDTO colorDTO);
    void deleteColor(Long id);
    void saveColor(ColorDTO colorDTO, Long id);

    Manufacturer addManufacturer(ManufacturerDTO manufacturerDTO);
    void deleteManufacturer(Long id);
    void saveManufacturer(ManufacturerDTO manufacturerDTO, Long id);

    Size addSize(SizeDTO sizeDTO);
    void deleteSize(Long id);
    void saveSize(SizeDTO sizeDTO, Long id)
;}
