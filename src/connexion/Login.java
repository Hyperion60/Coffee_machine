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
        public void parser(String name_password)
    {
        //this.Name = null;
        //this.Password = null;
        String[] separation1 = name_password.split(":");
        String[] separation2 = separation1[1].split(",");
        this.Name=separation2[0];
        this.Password=separation2[1];
    }
}
