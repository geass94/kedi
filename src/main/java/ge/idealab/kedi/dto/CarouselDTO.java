package ge.idealab.kedi.dto;

import java.util.ArrayList;
import java.util.List;

public class CarouselDTO {
    private Long id;
    private String name;
    private String area;
    private String sliderEffect;
    private Double width = 600d;
    private Double height = 480d;
    private String dimensionUnit = "px";
    private List<CarouselFileDTO> carouselFiles = new ArrayList<>();

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

    public List<CarouselFileDTO> getCarouselFiles() {
        return carouselFiles;
    }

    public void setCarouselFiles(List<CarouselFileDTO> carouselFiles) {
        this.carouselFiles = carouselFiles;
    }
}
