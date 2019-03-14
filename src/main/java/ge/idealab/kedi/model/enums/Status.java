package ge.idealab.kedi.model.enums;

public enum Status {

    ACTIVE(1, "ACTIVE"),
    DELETED(2, "DELETED"),
    LOCKED(3, "LOCKED"),
    EMAIL_NOT_VERIFIED(4, "EMAIL_NOT_VERIFIED"),
    BOUGHT(5, "BOUGHT"),
    VALIDATING(6, "VALIDATING"),
    ORDERED(7, "ORDERED"),
    CANCELED(8, "CANCELED"),
    PAID(9, "PAID"),
    DECLINED(10, "DECLINED"),
    FAILED(11, "FAILED");

    private int id;
    private String name;

    Status(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Status getFromId(int id) {
        for (Status status : Status.values()) {
            if (status.getId() == id) {
                return status;
            }
        }
        throw new RuntimeException("Status with provided id[" + id + "] not found");
    }

    public static Status getFromName(String name) {
        for (Status status : Status.values()) {
            if (status.getName().equals(name)) {
                return status;
            }
        }
        throw new RuntimeException("Status with provided name[" + name + "] not found");
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
