package parser;

import Structures.Globals;
import connexion.Login;
import connexion.User;

public class main_parser {
    private Login login;

    public main_parser() {
        this.login = new Login();
    }

    public int main_parser_line(Globals lists, String input, ServerThread thread) {
        String type = input.split(":")[0];
        int return_code = 0;
        if (type.length() == 0) {
            return 1;
        }
        switch (type) {
            case "Login":
                Login.parser(user, input);
                break;
            case "Signup":
                lists.signup.Parser_signup(lists, input);
                break;
            case "Logout":
                return_code = 2;
                user = null;
                break;
            case "bank":
                return_code = 3;
            default:
                return_code = 0;
        }
        return return_code;
    }
}
