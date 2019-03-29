package ge.idealab.kedi.service.impl;

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
import ge.idealab.kedi.service.SpecificationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SpecificationServiceImpl implements SpecificationService {
    @Autowired
    private ManufacturerRepository manufacturerRepository;
    @Autowired
    private ColorRepository colorRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private SizeRepository sizeRepository;

    @Override
    public List<Category> addCategories(List<CategoryDTO> categoryDTOS) {
        ModelMapper modelMapper = new ModelMapper();

        List<Category> categoryList = new ArrayList<>();

        for (CategoryDTO categoryDTO : categoryDTOS){
            Category category = modelMapper.map(categoryDTO, Category.class);
            Category savedCategory = categoryRepository.save(category);
            if (category.getChildren() != null){
                List<Category> children = new ArrayList<>();
                for (Category subitem : category.getChildren()){
                    subitem.setParent(savedCategory);
                    Category savedChild = categoryRepository.save(subitem);
                    children.add(savedChild);
                }
                savedCategory.setChildren(children);
            }
            categoryList.add(savedCategory);
        }
        return categoryList;
    }

    @Override
    public Category addCategory(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setName(categoryDTO.getName());
        if (categoryDTO.getParent() != null && categoryDTO.getParent().getId() != null) {
            Category parent = categoryRepository.getOne(categoryDTO.getParent().getId());
            category.setParent(parent);
        }
        category = categoryRepository.save(category);
        return category;
    }

    @Override
    public void deleteCategory(Long id) {
        Category category = categoryRepository.getOne(id);
        category.setStatus(Status.DELETED);
        categoryRepository.save(category);
        for (Category sub : category.getChildren()) {
            sub.setStatus(Status.DELETED);
            categoryRepository.save(sub);
        }
    }

    @Override
    public void saveCategory(CategoryDTO categoryDTO, Long id) {
        ModelMapper modelMapper = new ModelMapper();
        Category original = categoryRepository.getOne(id);
        Category edited = modelMapper.map(categoryDTO, Category.class);

        if (edited.getName() != null) {
            original.setName(edited.getName());
        }

        if (edited.getChildren() != null) {
//            original.setChildren(edited.getChildren());
        }

        if (edited.getParent() != null) {
            original.setParent(edited.getParent());
        }

        categoryRepository.save(original);
    }

    @Override
    public Color addColor(ColorDTO colorDTO) {
        ModelMapper modelMapper = new ModelMapper();
        Color color = modelMapper.map(colorDTO, Color.class);
        return colorRepository.save(color);
    }

    @Override
    public void deleteColor(Long id) {
        Color color = colorRepository.getOne(id);
        color.setStatus(Status.DELETED);
        colorRepository.save(color);
    }

    @Override
    public void saveColor(ColorDTO colorDTO, Long id) {
        Color color = colorRepository.getOne(id);
        color.setName(colorDTO.getName());
        color.setHex(colorDTO.getHex());
        colorRepository.save(color);
    }

    @Override
    public Manufacturer addManufacturer(ManufacturerDTO manufacturerDTO) {
        ModelMapper modelMapper = new ModelMapper();
        Manufacturer manufacturer = modelMapper.map(manufacturerDTO, Manufacturer.class);
        return manufacturerRepository.save(manufacturer);
    }

    @Override
    public void deleteManufacturer(Long id) {
        Manufacturer manufacturer = manufacturerRepository.getOne(id);
        manufacturer.setStatus(Status.DELETED);
        manufacturerRepository.save(manufacturer);
    }

    @Override
    public void saveManufacturer(ManufacturerDTO manufacturerDTO, Long id) {
        Manufacturer manufacturer = manufacturerRepository.getOne(id);
        manufacturer.setName(manufacturerDTO.getName());
        manufacturerRepository.save(manufacturer);
    }

    @Override
    public Size addSize(SizeDTO sizeDTO) {
        ModelMapper modelMapper = new ModelMapper();
        Size size = modelMapper.map(sizeDTO, Size.class);
        return sizeRepository.save(size);
    }

    @Override
    public void deleteSize(Long id) {
        Size size = sizeRepository.getOne(id);
        size.setStatus(Status.DELETED);
        sizeRepository.save(size);
    }

    @Override
    public void saveSize(SizeDTO sizeDTO, Long id) {
        Size size = sizeRepository.getOne(id);
        size.setCountrySuffix(sizeDTO.getCountrySuffix());
        size.setGenderSuffix(sizeDTO.getGenderSuffix());
        size.setSize(sizeDTO.getSize());
        sizeRepository.save(size);
    }
}
