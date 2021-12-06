package Interface;

import com.sun.tools.javac.Main;

import java.net.Socket;
import java.util.ArrayList;

public class ServerCmd {
    Socket server;
    MainFrame mainFrame;
    IOCommand ioCommand;

    public ServerCmd(Socket server, MainFrame mainFrame) {
        this.server = server;
        this.mainFrame = mainFrame;
        this.ioCommand = new IOCommand(server);
    }

    public void Login_client() {
        String login = mainFrame.getUsernamefield().toString();
        String pass = mainFrame.getPasswordfield().toString();
        if (login.length() == 0 || pass.length() == 0) {
            mainFrame.Errors.add("Login ou mot de passe vide !");
            mainFrame.refreshErreur();
            return;
        }
        int status = this.ioCommand.ecrireReseau("Login:" + login + "," + pass);
        if (status == 1) {
            this.mainFrame.Errors.add("Echec de la communication avec le serveur");
            this.mainFrame.refreshErreur();
            return;
        }
        String response = this.ioCommand.lireReseau();
        try {
            if (response.split(":").length == 0) {
                // Connexion réussie
                // Solde
                status = this.ioCommand.ecrireReseau("Bank");
                response = this.ioCommand.lireReseau();
                if (response.split(" : ")[0].equals("Solde actuel")) {
                    this.mainFrame.getBank().setText("Compte bancaire : " + response.split(" : ")[1]);
                } else {
                    this.mainFrame.Errors.add("Echec de la récupération du compte bancaire");
                    this.mainFrame.refreshErreur();
                }
                // Liste des commandes
                status = this.ioCommand.ecrireReseau("ListCmd");
                response = this.ioCommand.lireReseau();
                if (response.split(":")[0].equals("Liste")) {
                    for (String cmd: response.split(":")[1].split(";")) {
                        if (cmd.length() != 0) {
                            // Format : name,type,taille,etat
                            this.mainFrame.refreshListCmd(response);
                        }
                    }
                }
            } else {
                this.mainFrame.Errors.add("Login ou mot de passe invalide");
                this.mainFrame.refreshErreur();
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }
}
