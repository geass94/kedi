package ge.idealab.kedi.model.banner;

import ge.idealab.kedi.model.BaseStatusAuditEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "banners")
public class Banner extends BaseStatusAuditEntity {
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
    @Column
    private Date validTill = new Date();
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "banner")
    private List<BannerFile> bannerFiles = new ArrayList<>();

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

    public Date getValidTill() {
        return validTill;
    }

    public void setValidTill(Date validTill) {
        this.validTill = validTill;
    }

    public List<BannerFile> getBannerFiles() {
        return bannerFiles;
    }

    public void setBannerFiles(List<BannerFile> bannerFiles) {
        this.bannerFiles = bannerFiles;
    }
}
