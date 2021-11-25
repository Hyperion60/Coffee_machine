package connexion;
import Structures.Globals;

import java.util.List;

public class Login {
    private User user;
    private static String Name,Password;




    public Login() {}

    public User log_in(Globals lists, String name, String password) throws Exception {
        this.user = null;
        for (User user: lists.list_user) {
            if (user.getName().equals(name))
            {
                this.user = user;
                break;
            }
        }
        if (this.user == null || !this.user.checkPassword(password))
            throw new Exception("User does not exists or invalid password");
        return this.user;
    }

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
/*
    //Logout

    public String logout()
    {
        return Name;
    }*/
}
