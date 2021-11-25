package server;

import connexion.User;
import parser.main_parser;

import java.io.IOException;
import java.net.Socket;
import java.time.LocalDateTime;

public class ServerThread implements Runnable {
    private Socket client;
    private Structures.Globals Globals;
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
        IOCommandes my_stream = new IOCommandes(this.client);
        while (true) {
            line = my_stream.lireReseau();
            System.out.println("client>" + line);
            // Parser
            // Return string
            this.Parser = new main_parser();
            this.Parser.main_parser_line(this.Globals, line, this);
            if (this.user == null)
                System.out.println("User : null");
            else
                System.out.println("User : " + this.user.getName());
            if (line != null) {
                my_stream.ecrireEcran("echo>" + line);
                try {
                    this.file.lireFichier();
                } catch (IOException exception) {
                    continue;
                }
                this.file.ecrireFichier("IP:" + this.client.getInetAddress().toString() + " - " + LocalDateTime.now());
                this.file.ecrireFichier(line);
                my_stream.ecrireReseau("echo>" + line);
                if (line.equals("quit")) {
                    break;
                }
            } else {
                try {
                    this.client.close();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
                return;
            }
        }
    }
    public boolean status() {
        return this.thread.getState() != Thread.State.TERMINATED;
    }
}