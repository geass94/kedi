package ge.idealab.kedi.model.enums;

public enum Size {
    S(1, "Small"),
    M(2, "Medium"),
    L(3, "Large");
    
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
