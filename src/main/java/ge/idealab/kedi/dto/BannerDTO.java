package ge.idealab.kedi.dto;

import ge.idealab.kedi.model.converters.StatusConverter;
import ge.idealab.kedi.model.enums.Status;

import javax.persistence.Convert;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BannerDTO {
    private Long id;
    @Convert(converter = StatusConverter.class)
    private Status status;
    private String name;
    private String area;
    private String sliderEffect;
    private Date validTill;
    private Double width = 600d;
    private Double height = 480d;
    private String dimensionUnit = "px";
    private List<BannerFileDTO> bannerFiles = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
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

    public List<BannerFileDTO> getBannerFiles() {
        return bannerFiles;
    }

    public void setBannerFiles(List<BannerFileDTO> bannerFiles) {
        this.bannerFiles = bannerFiles;
    }

    public Date getValidTill() {
        return validTill;
    }

    public void setValidTill(Date validTill) {
        this.validTill = validTill;
    }
}
