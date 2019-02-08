package ge.idealab.kedi.model.product;

import ge.idealab.kedi.model.BaseStatusAuditEntity;

import javax.persistence.*;

@Entity
@Table(name = "manufacturers")
public class Manufacturer extends BaseStatusAuditEntity {
    private String name;

    public Manufacturer() {
    }

    public Manufacturer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
