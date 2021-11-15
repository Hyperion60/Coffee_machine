import java.util.ArrayList;
import java.util.List;

public class Machine {
    //declaration
    private String Norme;
    private int Capacity;
    private List<String> Type;
    private String Location;
    private Monitor Monitor;
    //initialisation
    public Machine()
    {
        this.Norme = "Vendor description";
        this.Capacity = 0;
        this.Type = new ArrayList<>();
        this.Location = "Emplacement machine";
        this.Monitor = new Monitor();
    }

    //Norme
    public String get_string_norme() {
        return this.Norme;
    }
    public boolean set_string_norme(String new_norme ){
        if(new_norme == null)
            return false;
        this.Norme = new_norme;
        return true;
    }

    //Capacity
    public int get_string_capacity(){
        return this.Capacity;
    }
    public boolean set_int_capacity(int capacity_status){
        if(capacity_status <= 0)
            return false;
        this.Capacity=capacity_status;
        return true;
    }
    //Type
    public List<String> get_list_type() {
        return this.Type;
    }
    public String get_int_type(int t){
        if (t < 0 || t > this.Type.size() - 1)
            return null;
        return this.Type.get(t);
    }
}
