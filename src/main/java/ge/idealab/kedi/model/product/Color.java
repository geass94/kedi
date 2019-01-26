package ge.idealab.kedi.model.product;

import ge.idealab.kedi.model.BaseEntity;

import javax.persistence.*;

@Entity
@Table
public class Color extends BaseEntity {
    @Column
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
