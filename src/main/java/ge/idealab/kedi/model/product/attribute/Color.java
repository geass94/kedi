package ge.idealab.kedi.model.product.attribute;

import ge.idealab.kedi.model.BaseStatusAuditEntity;

import javax.persistence.*;

@Entity
@Table(name = "colors")
public class Color extends BaseStatusAuditEntity {
    @Column
    private String name;
    @Column
    private String hex;
    @Column
    private String icon;

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

    public String getHex() {
        return hex;
    }

    public void setHex(String hex) {
        this.hex = hex;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
