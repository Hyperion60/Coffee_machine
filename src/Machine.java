import java.util.ArrayList;
import java.util.List;

public class Machine {
    //1.declaration
    private String Norme;
    private int Capacity;
    private List<String> Type;
    private String Location;
    private Monitor Monitor;


    //2.initialisation
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
    public int get_string_Capacity(){
        return this.Capacity;
    }
    public boolean set_int_Capacity(int capacity_status){
        if(capacity_status <= 0)
            return false;
        this.Capacity=capacity_status;
        return true;
    }
    //Type
    public List<String> get_list_Type() {return this.Type;}
    public String get_int_type(int t){
        if (t < 0 || t > this.Type.size() - 1)
            return null;
        return this.Type.get(t);
    }
    public boolean add_Type(String new_type) {
        if (new_type == null)
            return false;
        this.Type.add(new_type);
        return true;
    }
    public boolean remove_Type(String old_type) {
        if (!Type.contains(old_type))
            return false;
        this.Type.remove(old_type);
        return true;
    }
    //Location
    public String get_string_location() {
        return this.Location;
    }
    public boolean set_string_location(String new_location ) {
        if (new_location == null)
            return false;
        this.Location = new_location;
        return true;
    }
}
