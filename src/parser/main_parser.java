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
                    thread.stream.ecrireReseau("Connexion r??ussie " + thread.user.getName());
                break;
            case "Signup":
                try {
                    if (lists.signup.user_exists(lists, input)) {
                        thread.stream.ecrireReseau("Erreur : L'utilisateur existe d??j?? !");
                    } else {
                        thread.user = lists.signup.Parser_signup(lists, input);
                        thread.stream.ecrireReseau("Compte cr??e avec succ??s !");
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    thread.stream.ecrireReseau("Erreur : Format invalide !");
                }
                break;
            case "Logout":
                if (thread.user != null){
                    thread.user = null;
                    thread.stream.ecrireReseau("Vous avez ??t?? d??connect?? !");
                } else {
                    thread.stream.ecrireReseau("Erreur: Vous n'??tes pas connect?? !");
                }
                break;
            case "Bank":
                if (thread.user == null) {
                    thread.stream.ecrireReseau("Erreur : Vous n'??tes pas connect??");
                } else if (input.split(":").length == 2) {
                    thread.user.recharge(Float.parseFloat(input.split(":")[1]));
                    thread.stream.ecrireReseau("Compte recharg?? avec succ??s, solde actuel : " + thread.user.getBank());
                } else {
                    thread.stream.ecrireReseau("Solde actuel : " + thread.user.getBank() + "???");
                }
                break;
            case "Cmd":
                if (thread.user == null) {
                    thread.stream.ecrireReseau("Erreur : Vous n'??tes pas connect??");
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
                    thread.stream.ecrireReseau("Erreur : Vous n'??tes pas connect??");
                } else if (thread.user.commands.size() == 0) {
                    thread.stream.ecrireReseau("Vous n'avez pass?? aucune commande");
                } else if (thread.user.commands.get(thread.user.commands.size() - 1).state.equals(CommandState.FINISH)) {
                    thread.stream.ecrireReseau("Toutes vos commandes sont termin??es");
                } else if (thread.user.commands.get(thread.user.commands.size() - 1).state == CommandState.CANCELLED) {
                    thread.stream.ecrireReseau("Votre commande a ??t?? annul??e");
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
                System.out.println(stocklist);
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
                thread.stream.ecrireReseau("Nombre de caf??:" + lists.nb_cafe);
                break;
            case "Machine_infos":
                thread.stream.ecrireReseau(lists.coffee.get_string_norme());
                break;
            case "Location":
                thread.stream.ecrireReseau(lists.coffee.get_string_location());
                break;
            case "Capacity":
                thread.stream.ecrireReseau(String.valueOf(lists.coffee.get_string_Capacity()));
                break;
            case "Monitor_Status":
                if (lists.coffee.state == State.IDLE)
                    thread.stream.ecrireReseau("IDLE");
                else if (lists.coffee.state == State.WORKING)
                    thread.stream.ecrireReseau("En fonction");
                else if (lists.coffee.state == State.OFFLINE)
                    thread.stream.ecrireReseau("Hors ligne");
                else
                    thread.stream.ecrireReseau("Inconnue");
                break;
            case "CmdStatus":
                for (Command cmd: lists.coffee.list_command) {
                    if (cmd.state == CommandState.PROGRESS) {
                        int percent = ((lists.coffee.second_remain(cmd) * 100) / cmd.product.getDuree());
                        if (percent < 0)
                            percent = 0;
                        thread.stream.ecrireReseau((100 - percent) + ":" + cmd.product.getName() + " - " + cmd.product.getType());
                        return;
                    }
                }
                thread.stream.ecrireReseau("Aucune commande en cours");
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
