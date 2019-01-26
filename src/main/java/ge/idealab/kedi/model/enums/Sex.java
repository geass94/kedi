package ge.idealab.kedi.model.enums;

public enum Sex {
    MALE(1, "Male"),
    FEMALE(2, "Female");

    private int id;
    private String name;

    Sex(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Sex getFromName(String name){
        for (Sex sex: Sex.values()){
            if(sex.getName().equals(name)){
                return sex;
            }
        }
        throw new RuntimeException("Sex with provided name[" + name + "] not found");
    }

    public static Sex getFromId(int id){
        for (Sex sex: Sex.values()){
            if(sex.id == id){
                return sex;
            }
        }
        throw new RuntimeException("Sex with provided id[" + id + "] not found");
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
