package connexion;
import Structures.Globals;

import java.util.List;

public class Login {
    //Parser
    public User login_parser(List<User> list_user, String input_line)
    {
        String[] separation = input_line.split(":")[1].split(",");
        return get_user(list_user, separation[0], separation[1]);
    }

    public User get_user(List<User> list_user, String name, String password) {
        for (User user: list_user) {
            if (user.getName().equals(name) && user.checkPassword(password))
                return user;
        }
        return null;
    }
}
