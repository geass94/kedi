package ge.idealab.kedi.model.product;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import ge.idealab.kedi.model.BaseStatusAuditEntity;
import ge.idealab.kedi.model.Category;
import ge.idealab.kedi.model.converters.SexConverter;
import ge.idealab.kedi.model.converters.SizeConverter;
import ge.idealab.kedi.model.enums.Sex;
import ge.idealab.kedi.model.enums.Size;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Product extends BaseStatusAuditEntity {
    @Column
    private Boolean baseProduct = false;
    @Column
    private Long productVariantId;
    @Column
    private Long[] productVariantIds;
    @Column
    @NotNull
    private String name;
    @Column
    @NotNull
    private BigDecimal price;
    @Column
    @NotNull
    private Long quanityty;
    @Column
    private Float sale;
    @NotNull
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="color_id", nullable=false)
    private Color color;
    @Convert(converter = SizeConverter.class)
    @NotNull
    private Size size;
    @NotNull
    @Convert(converter = SexConverter.class)
    private Sex sex;
    @Column
    private String description;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="manufacturer_id", nullable=false)
    private Manufacturer manufacturer;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "product_category",
            joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name="fk_prdctr_product_id")),
            inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name="fk_prdctr_categoryid")))
    private List<Category> categoryList;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "product")
    private List<ProductFile> productFileList;
    @Column
    private Float bundleSale;
    @Column
    private BigDecimal bundlePrice;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Product product;
    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Product> bundle = new ArrayList<Product>();

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

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
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

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public List<ProductFile> getProductFileList() {
        return productFileList;
    }

    public void setProductFileList(List<ProductFile> productFileList) {
        this.productFileList = productFileList;
    }

    public Boolean getBaseProduct() {
        return baseProduct;
    }

    public void setBaseProduct(Boolean baseProduct) {
        this.baseProduct = baseProduct;
    }

    public Long getProductVariantId() {
        return productVariantId;
    }

    public void setProductVariantId(Long productVariantId) {
        this.productVariantId = productVariantId;
    }

    public Long[] getProductVariantIds() {
        return productVariantIds;
    }

    public void setProductVariantIds(Long[] productVariantIds) {
        this.productVariantIds = productVariantIds;
    }

    public Long getQuanityty() {
        return quanityty;
    }

    public void setQuanityty(Long quanityty) {
        this.quanityty = quanityty;
    }

    public Float getSale() {
        return sale;
    }

    public void setSale(Float sale) {
        this.sale = sale;
    }

    public Float getBundleSale() {
        return bundleSale;
    }

    public void setBundleSale(Float bundleSale) {
        this.bundleSale = bundleSale;
    }

    public List<Product> getBundle() {
        return bundle;
    }

    public void setBundle(List<Product> bundle) {
        this.bundle = bundle;
    }

    public BigDecimal getBundlePrice() {
        return bundlePrice;
    }

    public void setBundlePrice(BigDecimal bundlePrice) {
        this.bundlePrice = bundlePrice;
    }
}
