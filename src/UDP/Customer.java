package UDP;
import java.io.Serializable;
public class Customer implements Serializable{
    private static final long serialVersionUID = 20151107;
    public String id;
    public String code;
    public String name;
    public String dayOfBirth ;
    public String userName  ;
    public Customer (String id, String code, String name, String dayOfBirth , String userName  ){
        this.id = id;
        this.name = name;
        this.code = code;
        this.dayOfBirth  = dayOfBirth ;
        this.userName   = userName  ;
    }
    @Override
    public String toString(){
        return id + " " + code + " "  + name + " " + dayOfBirth  + " " + userName  ;
    }
}
