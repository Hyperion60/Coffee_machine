import Structures.Global_lists;
import Structures.Privileges;
import connexion.User;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Initialisation Global variables
        Global_lists lists = new Global_lists();

        // Create Admin user
        lists.list_user.add(new User("admin", "secret", Privileges.MAINTAINER));

        // Server thread

    }

}
