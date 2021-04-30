package persistent;

public class TestPersistentSubclass extends TestPersistent{
    private final String type = "TestPersistentSubclass";
    String id;
    public TestPersistentSubclass(int x, String name, String id) {
        super(x, name);
        this.id = id;
    }

    @Override
    public String toString() {
        return "TestPersistentSubclass{" +
                "id='" + id + '\'' +
                ", x=" + x +
                ", name='" + name + '\'' +
                '}';
    }

    private TestPersistentSubclass(){
    }
}
