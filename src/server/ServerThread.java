package server;

import connexion.User;
import parser.main_parser;

import java.io.IOException;
import java.net.Socket;
import java.time.LocalDateTime;

public class ServerThread implements Runnable {
    private Socket client;
    private Structures.Globals Globals;
    public IOCommandes stream;
    public User user;
    private main_parser Parser;
    private Thread thread;
    private IOCommandes file;

    public ServerThread(Socket client, String path, Structures.Globals Lists) throws IOException {
        this.Globals = Lists;
        this.client = client;
        this.file = new IOCommandes(path);
        this.user = null;
    }


    public void setThread(Thread thread) {
        this.thread = thread;
    }

    @Override
    public void run() {
        String line;
        stream = new IOCommandes(this.client);
        this.Parser = new main_parser();
        while (true) {
            line = stream.lireReseau();
            System.out.println("client>" + line);
            // Parser
            // Return string
            if (line.length() == 0) {
                this.stream.ecrireReseau("Erreur: Commande inconnue !");
            }
            this.Parser.main_parser_line(this.Globals, line, this);
            System.out.println("Stock café : " + this.Globals.coffee.Remain_Coffee + "/" + this.Globals.coffee.Capacity_Coffee);
            System.out.println("Stock thé : " + this.Globals.coffee.Remain_Thea + "/" + this.Globals.coffee.Capacity_Thea);
            System.out.println("Stock lait : " + this.Globals.coffee.Remain_Milk + "/" + this.Globals.coffee.Capacity_Milk);

            try {
                this.file.lireFichier();
            } catch (IOException exception) {
                continue;
            }
            this.file.ecrireFichier("IP:" + this.client.getInetAddress().toString() + " - " + LocalDateTime.now());
            this.file.ecrireFichier(line);
            if (line.equals("quit")) {
                break;
            }
        }
    }
    public boolean status() {
        return this.thread.getState() != Thread.State.TERMINATED;
    }
}