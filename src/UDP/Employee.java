package UDP;
import java.awt.geom.RoundRectangle2D;
import java.io.Serializable;
public class Employee implements Serializable {
    private static final long serialVersionUID = 20261107L;
    public String id, name, hireDate;
    public Double salary;
    public Employee(String id, String name, double salary, String hireDate){
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.hireDate = hireDate;
    }
    @Override
    public String toString(){
        return id + "/" + name + " / " + salary + " / " + hireDate;
    }
}
