package Structures;

import Coffee.Machine;
import Coffee.Products.Product;
import Coffee.Products.Taille;
import connexion.Login;
import connexion.Signup;
import connexion.User;
import server.AJAXServer;
import server.ServerThread;

import java.util.ArrayList;
import java.util.List;

public class Globals {
    public List<User> list_user;
    public List<ServerThread> list_client;
    public List<Taille> list_taille;
    public List<Product> list_product;
    public Machine coffee;
    public Signup signup;
    public Login login;
    public int server_port;
    public AJAXServer ajaxServer;
    public Thread ajaxThread;
    public int nb_cafe;

    public Globals() {
        this.list_user = new ArrayList<>();
        this.list_client = new ArrayList<>();
        this.coffee = new Machine();
        this.signup = new Signup();
        this.login = new Login();
        this.server_port = 867;
        this.nb_cafe = 0;

        this.ajaxServer = new AJAXServer(this);
        this.ajaxThread = new Thread(this.ajaxServer);
        this.ajaxThread.start();
        this.ajaxServer.setThread(this.ajaxThread);

        this.list_taille = new ArrayList<>();
        this.list_taille.add(new Taille("Tasse", 2.9f, 1.5f, 1.0f, 1.5f));
        this.list_taille.add(new Taille("Demi-Tasse", 1.5f, 0.7f, 0.5f, 1.0f));
        this.list_taille.add(new Taille("Quart-Tasse", 0.7f, 0.35f, 0.2f, 0.5f));

        this.list_product = new ArrayList<>();
        this.list_product.add(new Product("Café", "Café Long", 10, 90f, 0.8f, 1f, 0f, 0f));
        this.list_product.add(new Product("Café", "Café au Lait", 15, 90f, 1f, 0.7f, 0.3f, 0f));
        this.list_product.add(new Product("Thé", "Vert", 15, 100f, 1.15f, 0f, 1f, 0f));
        this.list_product.add(new Product("Chocolat", "Normal", 20, 80f, 1.05f, 0f, 0f, 1f));
    }

    public Taille search_taille_name(String name) {
        for (Taille taille: this.list_taille) {
            if (taille.taille.equals(name)) {
                return taille;
            }
        }
        return null;
    }

}
