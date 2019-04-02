package ge.idealab.kedi.model.product.attribute;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ge.idealab.kedi.model.BaseStatusAuditEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
public class Category extends BaseStatusAuditEntity {
    @Column
    private String name;

    @Column
    private Integer weight = 0;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Category> children = new ArrayList<Category>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getParent() {
        return parent;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }

    public List<Category> getChildren() {
        return children;
    }

    public void setChildren(List<Category> children) {
        this.children = children;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }
}
