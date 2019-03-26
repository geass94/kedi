package ge.idealab.kedi.model.enums;

public enum Condition {
    NEW(1, "NEW"),
    USED(2, "USED"),
    REFURBISHED(3, "REFURBISHED");

    private int id;
    private String name;

    Condition(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Condition getFromName(String name){
        for (Condition condition: Condition.values()){
            if(condition.getName().equals(name)){
                return condition;
            }
        }
        throw new RuntimeException("Condition with provided name[" + name + "] not found");
    }

    public static Condition getFromId(int id){
        for (Condition condition: Condition.values()){
            if(condition.id == id){
                return condition;
            }
        }
        throw new RuntimeException("Condition with provided id[" + id + "] not found");
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
