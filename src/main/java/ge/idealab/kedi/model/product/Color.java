package ge.idealab.kedi.model.product;

import ge.idealab.kedi.model.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "colors")
public class Color extends BaseEntity {
    @Column
    private String name;

    public Color() {
    }

    public Color(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
