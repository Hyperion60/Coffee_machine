package Interface;

import javax.swing.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerCmd {
    Socket server;
    MainFrame mainFrame;
    IOCommand ioCommand;
    List<ProgressCmd> progressCmdList;
    List<Thread> threadList;
    ProgressCmd progressCmd;
    Thread threadCmd;

    public ServerCmd(Socket server, MainFrame mainFrame) {
        this.server = server;
        this.mainFrame = mainFrame;
        this.ioCommand = new IOCommand(server);
        this.progressCmdList = new ArrayList<>();
        this.threadList = new ArrayList<>();
    }

    private void refreshBank(boolean err) {
        this.ioCommand.ecrireReseau("Bank");
        String response = this.ioCommand.lireReseau();
        if (response.split(" : ")[0].equals("Solde actuel")) {
            float account = Float.parseFloat(response.split(" : ")[1].split("€")[0]);
            int round = (int)(account * 100);
            account = ((float)round) / 100;
            if (!err)
                this.mainFrame.getBank().setText("Compte bancaire : " + account + " €");
            else
                this.mainFrame.getBank().setText("<html><font color='red'><b>Compte bancaire : " + account + " €</b></font></html>");
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
                this.refreshBank(false);
                // Liste des commandes
                this.ioCommand.ecrireReseau("ListCmd");
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

    public void Signup_client() {
        String login = mainFrame.getUsernamefield().getText();
        String pass = mainFrame.getPasswordfield().getText();
        if (login.length() == 0 || pass.length() == 0) {
            mainFrame.Errors.add("Login ou mot de passe vide !");
            mainFrame.refreshErreur();
            return;
        }
        this.ioCommand.ecrireReseau("Signup:" + login + "," + pass);
        String response = this.ioCommand.lireReseau();
        if (response.split(" : ")[0].equals("Erreur")) {
            this.mainFrame.Errors.add(response);
            this.mainFrame.refreshErreur();
            return;
        }
        if (response.equals("Compte crée avec succès !")) {
            this.Login_client();
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
            this.refreshBank(false);
        }
    }

    public void NewCommande() {
        String produit = this.mainFrame.getProductList().getItemAt(this.mainFrame.getProductList().getSelectedIndex()).toString();
        String taille = this.mainFrame.getSizeList().getItemAt(this.mainFrame.getSizeList().getSelectedIndex()).toString();
        this.ioCommand.ecrireReseau("Cmd:" + produit.split(" - ")[0] + "," + produit.split(" - ")[1] + "," + taille);
        String response = this.ioCommand.lireReseau();
        if (response.split(" : ")[0].equals("Erreur")) {
            this.mainFrame.Errors.add(response);
            this.mainFrame.refreshErreur();
            return;
        }
        if (response.equals("Erreur: Solde insuffisant")) {
            this.mainFrame.Errors.add(response);
            this.mainFrame.refreshErreur();
            this.refreshBank(true);
            return;
        }
        this.refreshBank(false);
        // Lancer le thread
        this.progressCmd = new ProgressCmd(this.ioCommand, this.mainFrame);
        this.progressCmdList.add(this.progressCmd);
        this.threadCmd = new Thread(this.progressCmd);
        this.threadList.add(this.threadCmd);
        this.threadCmd.start();
    }
}
