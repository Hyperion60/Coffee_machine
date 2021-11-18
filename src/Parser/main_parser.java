package Parser;

import Structures.Global_lists;
import connexion.Login;
import connexion.User;

public class main_parser {
    public int main_parser_line(Global_lists lists, String input, User user) {
        String type = input.split(":")[0];
        int return_code = 0;
        if (type.length() == 0) {
            return 1;
        }
        switch (type) {
            case "Login":
                Login.Parser(user, input);
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
