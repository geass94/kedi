package ge.idealab.kedi.dto;

public class SizeDTO {
    private Long id;
    private String countrySuffix;
    private String genderSuffix;
    private String size;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
