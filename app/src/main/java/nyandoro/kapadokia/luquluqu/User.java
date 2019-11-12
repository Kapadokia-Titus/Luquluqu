package nyandoro.kapadokia.luquluqu;

public class User {

    private String name;
    private  String user_id;
    private String phone;
    private String photo_url;

    //creating a costructor
    public User(){
        //empty constructor
    }

    //an overriden method that converts tem to string literals

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", user_id='" + user_id + '\'' +
                ", phone='" + phone + '\'' +
                ", photo_url='" + photo_url + '\'' +
                '}';
    }

    //generating abstract getters and setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }
}
