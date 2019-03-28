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
}
