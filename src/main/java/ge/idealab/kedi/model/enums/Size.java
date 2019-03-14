package ge.idealab.kedi.model.enums;

public enum Size {
    XS(1, "XS"),
    S(2, "S"),
    M(3, "M"),
    L(4, "Large"),
    XL(5, "XL"),
    XXL(6, "XXL");
    
    private int id;
    private String name;

    Size(int id, String name) {
        this.id = id;
        this.name = name;
    }
    
    public static Size getFromName(String name){
        for (Size size: Size.values()){
            if(size.getName().equals(name)){
                return size;
            }
        }
        throw new RuntimeException("Size with provided name[" + name + "] not found");
    }

    public static Size getFromId(int id){
        for (Size size: Size.values()){
            if(size.id == id){
                return size;
            }
        }
        throw new RuntimeException("Size with provided id[" + id + "] not found");
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
