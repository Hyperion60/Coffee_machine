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
    public static String parser(User user, String name_password)
    {
        String[] separation1 = name_password.split(":");
        String[] separation2 = separation1[1].split(",");
        Name=separation2[0];
        Password=separation2[1];
        return null;
    }
/*
    //Logout

    public String logout()
    {
        return Name;
    }*/
}
