package server;

import connexion.User;

import java.io.IOException;
import java.net.Socket;
import java.time.LocalDateTime;

public class ServerThread implements Runnable {
    private Socket client;
    private User user;
    private Thread thread;
    private IOCommandes file;

    public ServerThread(Socket client, String path) throws IOException {
        this.client = client;
        this.file = new IOCommandes(path);
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