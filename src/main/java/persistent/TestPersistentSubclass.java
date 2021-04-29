package persistent;

public class TestPersistentSubclass extends TestPersistent{
    String id;
    public TestPersistentSubclass(int x, String name, String id) {
        super(x, name);
        this.id = id;
    }
}
