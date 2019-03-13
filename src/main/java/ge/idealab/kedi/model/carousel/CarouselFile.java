package ge.idealab.kedi.model.carousel;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import ge.idealab.kedi.model.File;

import javax.persistence.*;


@Entity
@Table(name = "carousel_files")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class CarouselFile extends File {
    @ManyToOne
    @JoinColumn(name="carousel_id")
    private Carousel carousel;
    @Column
    private String caption;
    @Column
    private String externalURL;

    public Carousel getCarousel() {
        return carousel;
    }

    public void setCarousel(Carousel carousel) {
        this.carousel = carousel;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getExternalURL() {
        return externalURL;
    }

    public void setExternalURL(String externalURL) {
        this.externalURL = externalURL;
    }
}
