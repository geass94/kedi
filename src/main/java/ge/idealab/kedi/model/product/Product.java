package ge.idealab.kedi.model.product;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import ge.idealab.kedi.model.BaseStatusAuditEntity;
import ge.idealab.kedi.model.converters.SexConverter;
import ge.idealab.kedi.model.enums.Sex;
import ge.idealab.kedi.model.product.attribute.Category;
import ge.idealab.kedi.model.product.attribute.Color;
import ge.idealab.kedi.model.product.attribute.Manufacturer;
import ge.idealab.kedi.model.product.attribute.Size;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "products")
@JsonIdentityInfo(
        generator=ObjectIdGenerators.IntSequenceGenerator.class,
        property="@id",
        scope = Product.class)
public class Product extends BaseStatusAuditEntity {
//  Basic information
    @Column
    @NotNull
    private String name;
    @Column
    private String referenceCode;
    @Column
    private String barCode;
    @Column
    @NotNull
    private BigDecimal price = BigDecimal.valueOf(0);
    @Column
    @NotNull
    private Long quantity = 1L;
    @Column
    private Long totalQuantity = 1L;
    @Column
    private Float sale = 0f;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Column
    private Boolean promoted = false;
    @Column
    private Date countDown;

//  Specifications
    @NotNull
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="color_id", nullable=false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Color color;
    @NotNull
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="size_id", nullable=false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Size size;
    @Convert(converter = SexConverter.class)
    private Sex sex = Sex.MALE;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="manufacturer_id", nullable=false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Manufacturer manufacturer;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "product_category",
            joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name="fk_prdctr_product_id")),
            inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name="fk_prdctr_categoryid")))
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<Category> categoryList;

//  File attachments
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "product")
    private List<ProductFile> productFiles = new ArrayList<>();

//  Product Variants
    @Column
    private Boolean baseProduct = false;
    @ManyToOne(cascade={CascadeType.MERGE})
    @JoinColumn(name="base_variant_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Product baseVariant;
    @JoinTable(name = "product_variants", joinColumns = {
            @JoinColumn(name = "variant_product_id", referencedColumnName = "id", nullable = false)}, inverseJoinColumns = {
            @JoinColumn(name = "base_product_id", referencedColumnName = "id", nullable = false)})
    @ManyToMany
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<Product> variants = new ArrayList<Product>();

//  Bundles and gifts
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "bundled_products",
            joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "bundled_product_id", referencedColumnName = "id", nullable = false))
    private List<Product> bundledProducts = new ArrayList<>();
    @Column
    private Boolean makeBundle = false;

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

    public List<ProductFile> getProductFiles() {
        return productFiles;
    }

    public void setProductFiles(List<ProductFile> productFiles) {
        this.productFiles = productFiles;
    }

    public Boolean getBaseProduct() {
        return baseProduct;
    }

    public void setBaseProduct(Boolean baseProduct) {
        this.baseProduct = baseProduct;
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

    public List<Product> getBundledProducts() {
        return bundledProducts;
    }

    public void setBundledProducts(List<Product> bundledProducts) {
        this.bundledProducts = bundledProducts;
    }

    public Boolean getMakeBundle() {
        return makeBundle;
    }

    public void setMakeBundle(Boolean makeBundle) {
        this.makeBundle = makeBundle;
    }
}
