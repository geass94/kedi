package ge.idealab.kedi.model.product.attribute;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import ge.idealab.kedi.model.BaseStatusAuditEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "size_chart")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Size extends BaseStatusAuditEntity {
    @Column
    private String countrySuffix;
    @Column
    private String genderSuffix;
    @Column
    private String size;
    @Column
    private Float width;
    @Column
    private Float depth;
    @Column
    private Float length;

    public String getCountrySuffix() {
        return countrySuffix;
    }

    public void setCountrySuffix(String countrySuffix) {
        this.countrySuffix = countrySuffix;
    }

    public String getGenderSuffix() {
        return genderSuffix;
    }

    public void setGenderSuffix(String genderSuffix) {
        this.genderSuffix = genderSuffix;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Float getWidth() {
        return width;
    }

    public void setWidth(Float width) {
        this.width = width;
    }

    public Float getDepth() {
        return depth;
    }

    public void setDepth(Float depth) {
        this.depth = depth;
    }

    public Float getLength() {
        return length;
    }

    public void setLength(Float length) {
        this.length = length;
    }
}
