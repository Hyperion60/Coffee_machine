package Coffee;

import Structures.CommandState;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class Machine {
    //1.declaration
    private String Norme;
    private int Capacity;
    private List<String> Type;
    private String Location;
    private Monitor Monitor;
    private List<Command> list_command;


    //2.initialisation
    public Machine()
    {
        this.Norme = "Vendor description";
        this.Capacity = 0;
        this.Type = new ArrayList<>();
        this.Location = "Emplacement machine";
        this.Monitor = new Monitor();
        this.list_command = new ArrayList<>();
    }

    // Command
    public void AddCommand(Command command) {
        this.list_command.add(command);
    }

    public String ProgressCommand(Command command) {
        if (this.list_command.size() == 0)
        {
            return "Commande terminée ou non traitée";
        }
        if (this.list_command.get(0).equals(command) && this.list_command.get(0).state.equals(CommandState.PROGRESS)) {
            int second_remain = Math.toIntExact(command.begin_date.atZone(ZoneId.systemDefault()).toEpochSecond());
            second_remain += command.product.getDuree();
            second_remain -= Math.toIntExact(LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond());
            return "En cours de préparation, temps restant : " + second_remain;
        }
        int i = 0;
        for (Command cmd: this.list_command) {
            if (cmd.equals(command)) {
                return "Commande en attente, place dans la file d'attente : " + i;
            }
            i += 1;
        }
        return "Commande terminée";
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
