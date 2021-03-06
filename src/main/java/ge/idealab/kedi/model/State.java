package ge.idealab.kedi.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;

@Table(name = "states")
@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class State extends BaseEntity {
    @Column
    private String name;
    @ManyToOne
    @JoinColumn(name="country_id", nullable = false, foreignKey = @ForeignKey(name="fk_cntr_country_id"))
    private Country country;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}
