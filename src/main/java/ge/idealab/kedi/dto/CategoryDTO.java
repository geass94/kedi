package ge.idealab.kedi.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class CategoryDTO {
    private Long id;
    private String name;
    private CategoryDTO parent;
    private List<CategoryDTO> children = new ArrayList<CategoryDTO>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    public CategoryDTO getParent() {
        return parent;
    }
    @JsonProperty
    public void setParent(CategoryDTO parent) {
        this.parent = parent;
    }

    public List<CategoryDTO> getChildren() {
        return children;
    }

    public void setChildren(List<CategoryDTO> children) {
        this.children = children;
    }
}
