package persistent;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import persistent.service.FileSystemHandler;

import java.io.IOException;
import java.util.ArrayList;


@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value=TestPersistent.class, name = "TestPersistent"),
        @JsonSubTypes.Type(value=TestPersistentSubclass.class, name = "TestPersistentSubclass")
})
public class TestPersistent {
    private final String type = "TestPersistent";
    private static ObjectMapper mapper;
    static {
        mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
    }

    int x;
    String name;

    public TestPersistent(int x, String name) {
        this.x = x;
        this.name = name;
    }

    @Override
    public String toString() {
        return "TestPersistent{" +
                "x=" + x +
                ", name='" + name + '\'' +
                '}';
    }

    protected TestPersistent(){

    }

    public static ArrayList<TestPersistent> load(){
        try {
            return mapper.readValue(FileSystemHandler.FileType.TEST.getSavePath().toFile(),
                    new TypeReference<ArrayList<TestPersistent>>(){});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void save(ArrayList<TestPersistent> list){
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(FileSystemHandler.FileType.TEST.getSavePath().toFile(), list);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
