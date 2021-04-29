package user;

import org.json.simple.JSONObject;

public abstract class User {

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

    public void writeToJson() {

    }

}
