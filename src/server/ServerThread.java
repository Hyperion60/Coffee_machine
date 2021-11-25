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
        while (true) {
            line = stream.lireReseau();
            System.out.println("client>" + line);
            // Parser
            // Return string
            this.Parser = new main_parser();
            this.Parser.main_parser_line(this.Globals, line, this);
            for (User user: this.Globals.list_user) {
                System.out.println("User : " + user.getName());
                System.out.println("Bank : " + user.getBank());
            }

            stream.ecrireEcran("echo>" + line);
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