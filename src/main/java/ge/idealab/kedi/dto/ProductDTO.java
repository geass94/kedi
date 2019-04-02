package ge.idealab.kedi.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import ge.idealab.kedi.model.converters.SexConverter;
import ge.idealab.kedi.model.enums.Sex;
import ge.idealab.kedi.model.product.Product;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductDTO {
    private Long id;
    private String name;
    private String referenceCode;
    private String barCode;
    private BigDecimal price = BigDecimal.valueOf(0);
    private Long quantity = 1L;
    private Long totalQuantity = 1L;
    private Float sale = 0f;
    private String description;
    private Boolean promoted = false;
    private Date countDown;

    //  Specifications
    private ColorDTO color;

    private SizeDTO size;
    @Convert(converter = SexConverter.class)
    private Sex sex = Sex.MALE;

    private ManufacturerDTO manufacturer;

    private List<CategoryDTO> categoryList;

    //  File attachments
    private List<ProductFileDTO> productFiles = new ArrayList<>();

    //  Product Variants
    private Boolean baseProduct = false;
    private Product baseVariant;
    @JsonIgnore
    private List<Product> variants;

    //  Bundles and gifts
    private List<ProductDTO> bundledProducts = new ArrayList<>();
    private Boolean makeBundle = false;

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

    public String getReferenceCode() {
        return referenceCode;
    }

    public void setReferenceCode(String referenceCode) {
        this.referenceCode = referenceCode;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Long getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Long totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public Float getSale() {
        return sale;
    }

    public void setSale(Float sale) {
        this.sale = sale;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getPromoted() {
        return promoted;
    }

    public void setPromoted(Boolean promoted) {
        this.promoted = promoted;
    }

    public Date getCountDown() {
        return countDown;
    }

    public void setCountDown(Date countDown) {
        this.countDown = countDown;
    }

    public ColorDTO getColor() {
        return color;
    }

    public void setColor(ColorDTO color) {
        this.color = color;
    }

    public SizeDTO getSize() {
        return size;
    }

    public void setSize(SizeDTO size) {
        this.size = size;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
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

    public Boolean getBaseProduct() {
        return baseProduct;
    }

    public void setBaseProduct(Boolean baseProduct) {
        this.baseProduct = baseProduct;
    }

    public List<ProductDTO> getBundledProducts() {
        return bundledProducts;
    }

    public void setBundledProducts(List<ProductDTO> bundledProducts) {
        this.bundledProducts = bundledProducts;
    }

    public Boolean getMakeBundle() {
        return makeBundle;
    }

    public void setMakeBundle(Boolean makeBundle) {
        this.makeBundle = makeBundle;
    }

    public Product getBaseVariant() {
        return baseVariant;
    }

    public void setBaseVariant(Product baseVariant) {
        this.baseVariant = baseVariant;
    }

    public List<Product> getVariants() {
        return variants;
    }

    public void setVariants(List<Product> variants) {
        this.variants = variants;
    }
}
