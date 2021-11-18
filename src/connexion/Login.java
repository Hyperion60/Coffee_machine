package connexion;

import Structures.Global_lists;

import java.util.List;

public class Login {
    private User user;
    private  String Name,Password;



    public Login() {}

    public User log_in(Global_lists lists, String name, String password) throws Exception {
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
        public String parser(String name_password )
    {
        this.Name = null;
        this.Password = null;
        String[] tab = name_password.split(":");
        String[] tab2 = tab[1].split(",");
        Name=tab2[0];
        Password=tab2[1];
        return "test";
    }
}
