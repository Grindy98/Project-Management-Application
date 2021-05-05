package user;

public class ProjectManager extends User {

    private final String type = "ProjectManager";

    public ProjectManager(String username, String passwd, String address, String phone){
        super(username, passwd, address, phone);
    }

    @Override
    public String toString(){
        return "ProjectManager{" +
                "username=" + username +
                ", passwd=" + passwd +
                ", address=" + address +
                ", phone=" + phone + "}";
    }

    private ProjectManager(){}
}
