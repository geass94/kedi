package ge.idealab.kedi.model.product;

import ge.idealab.kedi.model.BaseStatusAuditEntity;

import javax.persistence.*;

@Entity
@Table
public class Manufacturer extends BaseStatusAuditEntity {
    private String name;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
