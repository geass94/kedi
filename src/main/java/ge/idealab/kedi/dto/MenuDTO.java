package ge.idealab.kedi.dto;

import java.util.List;

public class MenuDTO {
    private List<CategoryDTO> categories;
    private List<ManufacturerDTO> manufacturers;
    private List<ColorDTO> colors;

    public List<CategoryDTO> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryDTO> categories) {
        this.categories = categories;
    }

    public List<ManufacturerDTO> getManufacturers() {
        return manufacturers;
    }

    public void setManufacturers(List<ManufacturerDTO> manufacturers) {
        this.manufacturers = manufacturers;
    }

    public List<ColorDTO> getColors() {
        return colors;
    }

    public void setColors(List<ColorDTO> colors) {
        this.colors = colors;
    }
}
