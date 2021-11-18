import Structures.Global_lists;
import Structures.Privileges;
import connexion.User;
import server.ServerThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        // Initialisation Global variables
        Global_lists lists = new Global_lists();

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

        Socket new_client = null;
        while (true) {
            try {
                new_client = server.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Socket accepted");
            if (new_client != null) {
                ServerThread client = new ServerThread(new_client, "log.txt");
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
