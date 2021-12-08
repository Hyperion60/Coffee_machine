package Interface;

import javax.swing.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerCmd {
    Socket server;
    MainFrame mainFrame;
    IOCommand ioCommand;

    public ServerCmd(Socket server, MainFrame mainFrame) {
        this.server = server;
        this.mainFrame = mainFrame;
        this.ioCommand = new IOCommand(server);
    }

    private void refreshBank() {
        this.ioCommand.ecrireReseau("Bank");
        String response = this.ioCommand.lireReseau();
        if (response.split(" : ")[0].equals("Solde actuel")) {
            this.mainFrame.getBank().setText("Compte bancaire : " + response.split(" : ")[1]);
        } else {
            this.mainFrame.Errors.add("Echec de la récupération du compte bancaire");
            this.mainFrame.refreshErreur();
        }
    }

    private void refreshListProduct() {
        this.ioCommand.ecrireReseau("ProductList");
        String response = this.ioCommand.lireReseau();
        if (response.split(":").length == 1) {
            this.mainFrame.Errors.add("Echec de la récupération de la liste des produits");
            this.mainFrame.refreshErreur();
            return;
        }
        List<String> products = new ArrayList<>();
        for (String product: response.split(":")[1].split(";")) {
            if (product.length() != 0) {
                products.add(product.split(",")[0] + " - " + product.split(",")[1]);
            }
        }
        if (products.size() == 0) {
            products.add("Aucun produit !");
        }
        String[] list_product = new String[products.size()];
        for (int i = 0; i < products.size(); ++i) {
            list_product[i] = products.get(i);
        }
        this.mainFrame.getProductList().setModel(new DefaultComboBoxModel<>(list_product));
    }

    private void refreshListSize() {
        this.ioCommand.ecrireReseau("TailleList");
        String response = this.ioCommand.lireReseau();
        if (response.split(":").length == 1) {
            this.mainFrame.Errors.add("Echec de la récupération de la liste des produits");
            this.mainFrame.refreshErreur();
            return;
        }

        List<String> tailles = new ArrayList<>();
        for (String taille: response.split(":")[1].split(";")) {
            if (taille.length() != 0) {
                tailles.add(taille.split(",")[0]);
            }
        }
        if (tailles.size() == 0) {
            tailles.add("Aucune taille !");
        }
        String[] list_taille = new String[tailles.size()];
        for (int i = 0; i < tailles.size(); ++i) {
            list_taille[i] = tailles.get(i);
        }
        this.mainFrame.getSizeList().setModel(new DefaultComboBoxModel<>(list_taille));
    }

    public void Login_client() {
        String login = mainFrame.getUsernamefield().getText();
        String pass = mainFrame.getPasswordfield().getText();
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
            if (response.split(":").length == 1) {
                // Connexion réussie
                // Solde
                this.refreshBank();
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
                } else {
                    this.mainFrame.getListCmd().setText("<html><b>Aucune commande !</b></html>");
                }
                // Liste des produits
                this.refreshListProduct();
                this.refreshListSize();
            } else {
                this.mainFrame.Errors.add("Login ou mot de passe invalide");
                this.mainFrame.refreshErreur();
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    public void Recharge_client() {
        String recharge = this.mainFrame.getValue_recharge().getText();
        if (recharge.length() == 0) {
            mainFrame.Errors.add("Champ de recharge vide !");
            mainFrame.refreshErreur();
        } else {
            this.ioCommand.ecrireReseau("Bank:" + recharge);
            String response = this.ioCommand.lireReseau();
            if (response.split(" : ").length == 1) {
                mainFrame.Errors.add("Echec du rechargement !");
                mainFrame.refreshErreur();
            }
            this.refreshBank();
        }
    }
}
