package connexion;

import Structures.Globals;
import Structures.Privileges;

import java.util.List;

public class Signup {
    public boolean user_exists(Globals lists, String input) {
        String name = input.split(":")[1].split(",")[0];
        for (User user: lists.list_user) {
            if (user.getName().equals(name))
                return true;
        }
        return false;
    }

    public User Parser_signup(Globals lists, String input) {
        String name = input.split(":")[1].split(",")[0];
        String pass = input.split(":")[1].split(",")[1];

        lists.list_user.add(new User(name, pass, Privileges.USER));
        return get_user(lists.list_user, name, pass);
    }

    public User get_user(List<User> list_user, String name, String password) {
        for (User user: list_user) {
            if (user.getName().equals(name) && user.checkPassword(password))
                return user;
        }
        return null;
    }

}
