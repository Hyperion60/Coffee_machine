package Coffee;

import Structures.CommandState;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class Machine {
    //1.declaration
    private String Norme;
    public int Capacity_Coffee;
    public int Capacity_Thea;
    public int Capacity_Milk;
    public float Remain_Coffee;
    public float Remain_Thea;
    public float Remain_Milk;
    protected int Cafe_servis;
    private List<String> Type;
    private String Location;
    private Monitor Monitor;
    private List<Command> list_command;


    //2.initialisation
    public Machine()
    {
        this.Norme = "Vendor description";
        this.Capacity_Coffee = 15;
        this.Capacity_Thea = 15;
        this.Capacity_Milk = 15;
        this.Remain_Coffee = 10f;
        this.Remain_Milk = 10f;
        this.Remain_Thea = 10f;
        this.Cafe_servis = 0;
        this.Type = new ArrayList<>();
        this.Location = "Emplacement machine";
        this.Monitor = new Monitor();
        this.list_command = new ArrayList<>();
    }

    protected int second_remain(Command command) {
        int delta_time = Math.toIntExact(command.begin_date.atZone(ZoneId.systemDefault()).toEpochSecond());
        delta_time += command.product.getDuree();
        delta_time -= Math.toIntExact(LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond());
        return delta_time;
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
            return "En cours de préparation, temps restant:" + second_remain(command) + "/" + command.product.getDuree() + " secondes";
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

    public void RefreshCommand() {
        if (this.list_command.size() == 0)
            return;
        if (this.list_command.get(0).state != CommandState.PROGRESS) {
            this.list_command.get(0).BeginPreparation();
        } else {
            int delta = second_remain(this.list_command.get(0));
            if (delta < 0) {
                this.list_command.get(0).state = CommandState.FINISH;
                this.list_command.remove(0);
                this.Cafe_servis += 1;
            }
        }
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
        return this.Capacity_Coffee;
    }
    public boolean set_int_Capacity(int capacity_status){
        if(capacity_status <= 0)
            return false;
        this.Capacity_Coffee =capacity_status;
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
