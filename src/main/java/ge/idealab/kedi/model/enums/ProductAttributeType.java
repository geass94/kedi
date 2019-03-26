package ge.idealab.kedi.model.enums;

public enum ProductAttributeType {
    LIST(1, "LIST"),
    COLOR(2, "COLOR");

    private int id;
    private String name;

    ProductAttributeType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static ProductAttributeType getFromName(String name){
        for (ProductAttributeType productAttributeType: ProductAttributeType.values()){
            if(productAttributeType.getName().equals(name)){
                return productAttributeType;
            }
        }
        throw new RuntimeException("ProductAttributeType with provided name[" + name + "] not found");
    }

    public static ProductAttributeType getFromId(int id){
        for (ProductAttributeType productAttributeType: ProductAttributeType.values()){
            if(productAttributeType.id == id){
                return productAttributeType;
            }
        }
        throw new RuntimeException("ProductAttributeType with provided id[" + id + "] not found");
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
