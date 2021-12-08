package parser;

import Coffee.Command;
import Coffee.Products.Product;
import Coffee.Products.Taille;
import Coffee.State;
import Structures.CommandState;
import Structures.Globals;
import Structures.Privileges;
import connexion.Login;
import server.ServerThread;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class main_parser {
    private Login login;
    private State state_obj;

    public main_parser() {
        this.login = new Login();
    }

    private String getLocalIP() {
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface iface = interfaces.nextElement();
                if (iface.isLoopback() || !iface.isUp())
                    continue;

                Enumeration<InetAddress> addresses = iface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress addr = addresses.nextElement();
                    System.out.println(iface.getDisplayName() + " " + addr.getHostAddress());
                    if (!iface.getDisplayName().contains("Virtual") && !iface.getDisplayName().contains("VM"))
                        return addr.getHostAddress();
                }
            }
        } catch (SocketException e) {
            System.out.println("Failed to load Network Interfaces");
        }
        return null;
    }

    public void main_parser_line(Globals lists, String input, ServerThread thread) {
        String type = input.split(":")[0];
        if (type.length() == 0) {
            return;
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
                    thread.user = null;
                    thread.stream.ecrireReseau("Vous avez été déconnecté !");
                } else {
                    thread.stream.ecrireReseau("Erreur: Vous n'êtes pas connecté !");
                }
                break;
            case "Bank":
                if (thread.user == null) {
                    thread.stream.ecrireReseau("Erreur : Vous n'êtes pas connecté");
                } else if (input.split(":").length == 2) {
                    thread.user.recharge(Float.parseFloat(input.split(":")[1]));
                    thread.stream.ecrireReseau("Compte rechargé avec succès, solde actuel : " + thread.user.getBank());
                } else {
                    thread.stream.ecrireReseau("Solde actuel : " + thread.user.getBank() + "€");
                }
                break;
            case "Cmd":
                if (thread.user == null) {
                    thread.stream.ecrireReseau("Erreur : Vous n'êtes pas connecté");
                } else {
                    String product_name, product_type, cmd_taille;
                    try {
                        product_name = input.split(":")[1].split(",")[0];
                        product_type = input.split(":")[1].split(",")[1];
                        cmd_taille = input.split(":")[1].split(",")[2];
                    } catch (ArrayIndexOutOfBoundsException e) {
                        thread.stream.ecrireReseau("Erreur: Arguments manquants !");
                        product_name = null;
                        product_type = null;
                        cmd_taille = null;
                    }
                    if (cmd_taille != null) {
                        Command command;
                        if ((command = thread.user.newCommand(lists, thread, product_name, product_type, cmd_taille)) != null) {
                            lists.coffee.AddCommand(command);
                            lists.nb_cafe += 1;
                        }
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
                } else if (thread.user.commands.get(thread.user.commands.size() - 1).state == CommandState.CANCELLED) {
                    thread.stream.ecrireReseau("Votre commande a été annulée");
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
            case "StockGet":
                String stocklist = "Stocks:" + "coffee," + lists.coffee.Remain_Coffee + "/" + lists.coffee.Capacity_Coffee + ";" +
                        "thea," + lists.coffee.Remain_Thea + "/" + lists.coffee.Capacity_Thea + ";" +
                        "milk," + lists.coffee.Remain_Milk + "/" + lists.coffee.Capacity_Milk + ";";
                thread.stream.ecrireReseau(stocklist);
                break;
            case "MachineState":
                switch (lists.coffee.state) {
                    case OFFLINE -> thread.stream.ecrireReseau("Hors ligne");
                    case IDLE -> thread.stream.ecrireReseau("Au repos");
                    case WORKING -> thread.stream.ecrireReseau("En fonction");
                    default -> thread.stream.ecrireReseau("?");
                }
                break;
            case "MachineNetwork":
                thread.stream.ecrireReseau("IP:" + getLocalIP() + ",Port:" + lists.server_port);
                break;
            case "ClientStat":
                thread.stream.ecrireReseau("Nombre de client:" + lists.list_client.size());
                break;
            case "CafeNb":
                thread.stream.ecrireReseau("Nombre de café:" + lists.nb_cafe);
                break;
            default:
                if (thread.user != null && thread.user.getPrivileges() == Privileges.MAINTAINER) {
                    admin_parser.parser_admin_cmds(lists, thread, input);
                } else {
                    thread.stream.ecrireReseau("Erreur : Commande inconnue");
                }
        }
    }
}
