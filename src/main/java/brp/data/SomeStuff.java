package brp.data;

public class SomeStuff {
    private int id;
    private final String name;

    public SomeStuff(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
