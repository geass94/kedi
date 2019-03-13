package ge.idealab.kedi.model.carousel;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import ge.idealab.kedi.model.BaseStatusAuditEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "carousels")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Carousel extends BaseStatusAuditEntity {
    @Column
    private String name;
    @Column
    private String area;
    @Column
    private String sliderEffect;
    @Column
    private Double width = 600d;
    @Column
    private Double height = 480d;
    @Column
    private String dimensionUnit = "px";
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "carousel")
    private List<CarouselFile> carouselFiles = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getSliderEffect() {
        return sliderEffect;
    }

    public void setSliderEffect(String sliderEffect) {
        this.sliderEffect = sliderEffect;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public String getDimensionUnit() {
        return dimensionUnit;
    }

    public void setDimensionUnit(String dimensionUnit) {
        this.dimensionUnit = dimensionUnit;
    }

    public List<CarouselFile> getCarouselFiles() {
        return carouselFiles;
    }

    public void setCarouselFiles(List<CarouselFile> carouselFiles) {
        this.carouselFiles = carouselFiles;
    }
}
