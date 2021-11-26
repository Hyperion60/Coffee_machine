import Structures.Globals;
import Structures.Privileges;
import connexion.User;
import server.AJAXServer;
import server.ServerThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws IOException {
        // Initialisation Global variables
        Globals lists = new Globals();

        // Create Admin user
        lists.list_user.add(new User("admin", "secret", Privileges.MAINTAINER));

        // Server thread
        ServerSocket server = null;
        try {
            server = new ServerSocket(lists.server_port);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        AJAXServer ajaxServer = new AJAXServer(lists);
        Thread ajaxThread = new Thread(ajaxServer);
        ajaxThread.start();
        ajaxServer.setThread(ajaxThread);
        Socket new_client = null;
        while (true) {
            try {
                new_client = server.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Socket accepted");
            if (new_client != null) {
                ServerThread client = new ServerThread(new_client, "log.txt", lists);
                lists.list_client.add(client);
                Thread t = new Thread(client);
                t.start();
                client.setThread(t);
                System.out.println("Thread lancé");
                new_client = null;
            }
        }
    }

}
