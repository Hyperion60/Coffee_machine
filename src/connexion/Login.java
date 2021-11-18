package connexion;

import Structures.Global_lists;

public class Login {
    private User user;

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

}
