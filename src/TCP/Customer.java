package TCP;
import java.io.Serializable;

public class Customer implements Serializable {
    private static final long serialVersionUID = 20170711L;
    private int id;
    private String code;
    private String name;
    private String dayOfBirth ;
    private String userName;
    public Customer() {
    }
    public Customer(int id, String code, String name, String dayOfBirth , String username) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.dayOfBirth  = dayOfBirth ;
        this.userName = username;
    }
    public int getId() {
        return id;
    }
    public String getCode() {
        return code;}
    public String getName() {
        return name;}
    public String getDayOfBirth (){
        return dayOfBirth ;
    }
    public String getUsername() {
        return userName;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setDayOfBirth (String dayOfBirth ) {
        this.dayOfBirth  = dayOfBirth ;
    }
    public void setUsername(String userName) {
        this.userName = userName;
    }
}
