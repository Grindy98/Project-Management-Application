package persistent.user;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import persistent.service.FileSystemHandler;
import persistent.user.utils.Encryptor;
import scene.MainApp;
import scene.list.utils.MapBind;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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

    private static final ObservableMap<String, User> users;
    static {
        mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        users = FXCollections.observableHashMap();
    }

    protected String username;
    protected String passwd;
    protected String address;
    protected String phone;

    public User(String username, String passwd, String address, String phone) {
        this.username = username;
        this.passwd = Encryptor.encodePassword(username, passwd);
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

    @Override
    public int hashCode() {
        return username.hashCode();
    }

    public static void load(){
        List<User> list;
        try {
            list =  mapper.readValue(FileSystemHandler.FileType.USER.getSavePath().toFile(),
                    new TypeReference<List<User>>(){});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (User u : list) {
            users.put(u.getUsername(), u);
        }
    }

    public static void save() {
        List<User> list = List.copyOf(users.values());
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(FileSystemHandler.FileType.USER.getSavePath().toFile(), list);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static ObservableMap<String, User> getUsers(){
        return users;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswd() {
        return passwd;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    //returns true if username is not found in json
    public boolean validateUsername(){
        return !users.containsKey(username);
    }

    public boolean validatePassword(String password){
        return (password.length() >= 8);
    }

    public boolean validateAddress(){
        if(address.matches("[0-9]+") || address.length() > 100)
            return false;
        return true;
    }

    public boolean validatePhone(){
        return phone.matches("[0-9]+");
    }

    //returns true if given password matches this user's password
    public boolean checkPassword(String password){
        String encPass = Encryptor.encodePassword(username, password);

        return (encPass.equals(passwd));
    }
}
