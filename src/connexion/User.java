package connexion;

import Coffee.Command;
import Coffee.Products.Product;
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

    public boolean newCommand(Globals lists, ServerThread client, String name, String type) {
        Product selected = null;
        for (Product product: lists.list_product) {
            if (product.getType().equals(type) && product.getName().equals(name)) {
                selected = product;
                break;
            }
        }
        if (selected != null) {
            if (this.bank < selected.getPrice()) {
                client.stream.ecrireReseau("Erreur: Solde insuffisant");
                return false;
            }
            this.commands.add(new Command(selected));
            client.stream.ecrireReseau("Commande de " + this.commands.get(this.commands.size() - 1).product.getName() + this.commands.get(this.commands.size() - 1).product.getType() + "effectuée succès");
            return true;
        }
        client.stream.ecrireReseau("Erreur: Le produit n'existe pas.");
        return false;
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
