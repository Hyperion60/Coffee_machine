package parser;

import Structures.Globals;
import connexion.Login;
import connexion.User;
import server.ServerThread;

public class main_parser {
    private Login login;

    public main_parser() {
        this.login = new Login();
    }

    public int main_parser_line(Globals lists, String input, ServerThread thread) {
        String type = input.split(":")[0];
        int return_code = -42;
        if (type.length() == 0) {
            return 1;
        }
        switch (type) {
            case "Login":
                thread.user = login.login_parser(lists.list_user, input);
                break;
            case "Signup":
                if (lists.signup.user_exists(lists, input)) {
                    thread.stream.ecrireReseau("Erreur : L'utilisateur existe déjà !");
                } else {
                    thread.user = lists.signup.Parser_signup(lists, input);
                }
                break;
            case "Logout":
                if (thread.user != null){
                    return_code = 2;
                    thread.user = null;
                } else {
                    return_code = -2;
                }
                break;
            case "bank":
                if (thread.user == null) {
                    thread.stream.ecrireReseau("Erreur : Vous n'êtes pas connecté");
                    return_code = -3;
                } else {
                    thread.user.recharge(Float.parseFloat(input.split(":")[1]));
                    return_code = 3;
                }
                break;
            default:
                return_code = 0;
        }
        return return_code;
    }
}
