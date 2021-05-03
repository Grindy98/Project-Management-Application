package user;

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
        @JsonSubTypes.Type(value=User.class, name = "User"),
        @JsonSubTypes.Type(value=TeamMember.class, name = "TeamMember"),
        @JsonSubTypes.Type(value=ProjectManager.class, name = "ProjectManager")
})

public abstract class User {

    private final String type = "User";
    private static ObjectMapper mapper;
    static {
        mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
    }

    protected String username;
    protected String passwd;
    protected String address;
    protected String phone;

    public User(String username, String passwd, String address, String phone) {
        this.username = username;
        this.passwd = passwd;
        this.address = address;
        this.phone = phone;

    }

    @Override
    public String toString(){
        return "User{" +
                "username=" + username +
                ", passwd=" + passwd +
                ", address=" + address +
                ", phone=" + phone + "}";
    }

    protected User(){}

    @Override
    public boolean equals(Object o){
        if(o instanceof User){
            return (this.username.equals(((User)o).username));
        }
        return false;
    }

    public static ArrayList<User> load(){
        try {
            return mapper.readValue(FileSystemHandler.FileType.USER.getSavePath().toFile(),
                    new TypeReference<ArrayList<User>>(){});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void save(ArrayList<User> list) {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(FileSystemHandler.FileType.USER.getSavePath().toFile(), list);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
