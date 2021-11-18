package Structures;

import Coffee.Machine;
import connexion.User;
import java.util.ArrayList;
import java.util.List;

public class Global_lists {
    public List<User> list_user;
    public Machine coffee;

    public Global_lists() {
        this.list_user = new ArrayList<>();
        this.coffee = new Machine();
    }

}
