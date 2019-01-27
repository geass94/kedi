package ge.idealab.kedi.dto;

import ge.idealab.kedi.model.converters.SexConverter;
import ge.idealab.kedi.model.converters.SizeConverter;
import ge.idealab.kedi.model.enums.Sex;
import ge.idealab.kedi.model.enums.Size;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

public class ProductDTO {
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private BigDecimal price;
    @NotNull
    private ColorDTO color;
    @Convert(converter = SizeConverter.class)
    @NotNull
    private Size size;
    @NotNull
    @Convert(converter = SexConverter.class)
    private Sex sex;
    private String description;
    private ManufacturerDTO manufacturer;
    private List<CategoryDTO> categoryList;
    private List<ProductFileDTO> productFiles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public ColorDTO getColor() {
        return color;
    }

    public void setColor(ColorDTO color) {
        this.color = color;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ManufacturerDTO getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(ManufacturerDTO manufacturer) {
        this.manufacturer = manufacturer;
    }

    public List<CategoryDTO> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<CategoryDTO> categoryList) {
        this.categoryList = categoryList;
    }

    public List<ProductFileDTO> getProductFiles() {
        return productFiles;
    }

    public void setProductFiles(List<ProductFileDTO> productFiles) {
        this.productFiles = productFiles;
    }
}
