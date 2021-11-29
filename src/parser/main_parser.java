package parser;

import Coffee.Products.Product;
import Coffee.Products.Taille;
import Structures.CommandState;
import Structures.Globals;
import Structures.Privileges;
import connexion.Login;
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
                if (thread.user == null)
                    thread.stream.ecrireReseau("Erreur: Pseudo ou mot de passe invalide !");
                else
                    thread.stream.ecrireReseau("Connexion réussie " + thread.user.getName());
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
                    thread.stream.ecrireReseau("Vous avez été déconnecté !");
                } else {
                    thread.stream.ecrireReseau("Erreur: Vous n'êtes pas connecté !");
                    return_code = -2;
                }
                break;
            case "Bank":
                if (thread.user == null) {
                    thread.stream.ecrireReseau("Erreur : Vous n'êtes pas connecté");
                    return_code = -3;
                } else if (input.split(":").length == 2) {
                    thread.user.recharge(Float.parseFloat(input.split(":")[1]));
                    thread.stream.ecrireReseau("Compte rechargé avec succès, solde actuel : " + thread.user.getBank());
                    return_code = 3;
                } else {
                    thread.stream.ecrireReseau("Solde actuel : " + thread.user.getBank() + "€");
                }
                break;
            case "Cmd":
                if (thread.user == null) {
                    thread.stream.ecrireReseau("Erreur : Vous n'êtes pas connecté");
                    return_code = -4;
                } else {
                    String product_name = input.split(":")[1].split(",")[0];
                    String product_type = input.split(":")[1].split(",")[1];
                    if (!thread.user.newCommand(lists, thread, product_name, product_type)) {
                        // Log file
                        return_code = -4;
                    } else {
                        lists.coffee.AddCommand(thread.user.commands.get(thread.user.commands.size() - 1));
                        return_code = 4;
                    }
                }
                break;
            case "Progress":
                if (thread.user == null) {
                    thread.stream.ecrireReseau("Erreur : Vous n'êtes pas connecté");
                } else if (thread.user.commands.size() == 0) {
                    thread.stream.ecrireReseau("Vous n'avez passé aucune commande");
                } else if (thread.user.commands.get(thread.user.commands.size() - 1).state.equals(CommandState.FINISH)) {
                    thread.stream.ecrireReseau("Toutes vos commandes sont terminées");
                } else {
                    thread.stream.ecrireReseau(lists.coffee.ProgressCommand(thread.user.commands.get(thread.user.commands.size() - 1)));
                }
                break;
            case "ProductList":
                StringBuilder productlist = new StringBuilder("Liste des produits:");
                for (Product product: lists.list_product) {
                    productlist.append(product.getName()).append(",").append(product.getType());
                    productlist.append(",").append(product.getPrice()).append(";");
                }
                thread.stream.ecrireReseau(productlist.toString());
                break;
            case "TailleList":
                StringBuilder taillelist = new StringBuilder("Liste des tailles:");
                for (Taille taille: lists.list_taille) {
                    taillelist.append(taille.taille).append(",").append(taille.coffee_consumption).append(",");
                    taillelist.append(taille.thea_consumption).append(",").append(taille.milk_consumption);
                    taillelist.append(",").append(taille.price_coef).append(";");
                }
                thread.stream.ecrireReseau(taillelist.toString());
                break;
            default:
                if (thread.user != null && thread.user.getPrivileges() == Privileges.MAINTAINER) {
                    admin_parser.parser_admin_cmds(lists, thread, input);
                } else {
                    thread.stream.ecrireReseau("Erreur : Commande inconnue");
                    return_code = 0;
                }
        }
        return return_code;
    }
}
