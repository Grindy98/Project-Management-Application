package persistent.user;

public class TeamMember extends User {

    private final String type = "TeamMember";

    public TeamMember(String username, String passwd, String address, String phone){
        super(username, passwd, address, phone);
    }

    @Override
    public String toString(){
        return "TeamMember{" +
                "username=" + username +
                ", passwd=" + passwd +
                ", address=" + address +
                ", phone=" + phone + "}";
    }

    private TeamMember(){}
}
