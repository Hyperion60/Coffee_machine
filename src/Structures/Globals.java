package Structures;

import Coffee.Machine;
import connexion.Login;
import connexion.Signup;
import connexion.User;
import server.ServerThread;

import java.util.ArrayList;
import java.util.List;

public class Globals {
    public List<User> list_user;
    public List<ServerThread> list_client;
    public Machine coffee;
    public Signup signup;
    public Login login;
    public int server_port;

    public Globals() {
        this.list_user = new ArrayList<>();
        this.list_client = new ArrayList<>();
        this.coffee = new Machine();
        this.signup = new Signup();
        this.login = new Login();
        this.server_port = 867;
    }


}
