package connexion;

import Coffee.Command;
import Coffee.Products.Product;
import Coffee.Products.Taille;
import Structures.Globals;
import Structures.Privileges;
import server.ServerThread;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String Name;
    private String Password;
    private Privileges privileges;
    private float bank;
    public List<Command> commands;


    public User(String name, String password, Privileges privileges) {
        this.Name = name;
        this.privileges = privileges;
        this.Password = password;
        this.bank = 0;
        this.commands = new ArrayList<>();
    }

    public Command newCommand(Globals lists, ServerThread client, String name, String type, String taille) {
        Product selected = null;
        Command new_command;
        Taille taille1;
        if ((taille1 = lists.search_taille_name(taille)) == null) {
            client.stream.ecrireReseau("Erreur: Taille inconnue !");
            return null;
        }
        for (Product product: lists.list_product) {
            if (product.getType().equals(type) && product.getName().equals(name)) {
                selected = product;
                break;
            }
        }
        if (selected != null) {
            if (this.bank < selected.getPrice()) {
                client.stream.ecrireReseau("Erreur: Solde insuffisant");
                return null;
            }
            if (lists.coffee.Remain_Coffee < selected.getCoffee_consumption() * taille1.coffee_consumption ||
                lists.coffee.Remain_Thea < selected.getThea_consumption() * taille1.thea_consumption ||
                lists.coffee.Remain_Milk < selected.getMilk_consumption() * taille1.milk_consumption) {
                client.stream.ecrireReseau("Erreur: Stock insuffisant");
                return null;
            }
            lists.coffee.Remain_Coffee -= selected.getCoffee_consumption() * taille1.coffee_consumption;
            lists.coffee.Remain_Thea -= selected.getThea_consumption() * taille1.thea_consumption;
            lists.coffee.Remain_Milk -= selected.getMilk_consumption() * taille1.milk_consumption;
            this.bank -= selected.getPrice();
            new_command = new Command(selected, taille1);
            this.commands.add(new_command);
            client.stream.ecrireReseau("Commande de " + this.commands.get(this.commands.size() - 1).product.getName() + this.commands.get(this.commands.size() - 1).product.getType() + "effectuée succès");
            return new_command;
        }
        client.stream.ecrireReseau("Erreur: Le produit n'existe pas.");
        return null;
    }

    // Name
    public String getName() {
        return this.Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public boolean paiement(int sum) {
        if (sum > this.bank)
            return false;
        this.bank -= sum;
        return true;
    }

    public void recharge(float value) {
        this.bank += value;
        this.bank = Math.round(this.bank * 100f) / 100f;
    }

    public float getBank() {
        return this.bank;
    }

    public Privileges getPrivileges() {
        return this.privileges;
    }

    public void setPrivileges(Privileges privileges) {
        this.privileges = privileges;
    }

    public boolean checkPassword(String password) {
        return this.Password.equals(password);
    }

    public String getPassword() { return this.Password; }

    public boolean setPassword(String old_password, String new_password) {
        if (!checkPassword(old_password))
            return false;
        this.Password = new_password;
        return true;
    }
}
