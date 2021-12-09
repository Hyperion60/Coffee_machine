package Interface;

import server.IOCommandes;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;



public class MainFrame extends JFrame{
    private JProgressBar restantCafe;
    private JProgressBar restantLait;
    private JProgressBar restantThe;
    private JProgressBar statutPreparation;
    private JList<String> listeProduits;
    private JPanel mainPanel;
    private JPanel prepaPanel;
    private JLabel progressionCommande;
    private JLabel nbClients;
    private JLabel nbCafe;
    private JPanel dateCafe;
    private JLabel Etat;
    private JLabel ip_port;
    private JLabel commandeEnCours;
    private int stockCafe, stockThea,stockLait;
    private server.IOCommandes ioCommandes;


    public MainFrame(Socket socket){
        this.ioCommandes = new IOCommandes(socket);

        setContentPane(mainPanel);
        setTitle("Machine à café");
        setSize(1000,750);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        //Récupération état de la machine
        this.ioCommandes.ecrireReseau("MachineState");
        String etatMachine = this.ioCommandes.lireReseau();
        System.out.println("état de la machine => " + etatMachine);

        //Récupération état de la machine
        this.ioCommandes.ecrireReseau("Statuscmd");
        String commande = this.ioCommandes.lireReseau();
        System.out.println("état de la machine => " + etatMachine);
        String[] cmd = commande.split(":");

        // Récupération de la liste des produits
        this.ioCommandes.ecrireReseau("ProductList");
        String nouveauProduits = this.ioCommandes.lireReseau();
        System.out.println("Liste des produits => " + nouveauProduits);
        //Parse
        String[] listeProd = nouveauProduits.split("[:\\;\\,]");
        System.out.println("Substrings length:" + listeProd.length);
        for (int i = 0; i < listeProd.length; i++) {
            System.out.println("Str[" + i + "]:" + listeProd[i]);
        }


        // Récupération des stocks
        this.ioCommandes.ecrireReseau("StockGet");
        String stocks = this.ioCommandes.lireReseau();
        System.out.println("StockGet => " + stocks);
        //Parse
        String[] strs = stocks.split("[:\\;\\,\\/]");
        System.out.println("Substrings length:" + strs.length);
        for (int i = 0; i < strs.length; i++) {
            System.out.println("Str[" + i + "]:" + strs[i]);
        }
        // Récupération du nombre de clients
        this.ioCommandes.ecrireReseau("ClientStat");
        String nbreClients = this.ioCommandes.lireReseau();
        System.out.println("ClientStat => " + nbreClients);
        String[] nbrClients = nbreClients.split(":");

        // Récupération du nombre de café
        this.ioCommandes.ecrireReseau("CafeNb");
        String nbreCafe = this.ioCommandes.lireReseau();
        System.out.println("ClientStat => " + nbreCafe);
        String[] nbrCafe = nbreCafe.split(":");



        //Récupération IP port
        this.ioCommandes.ecrireReseau("MachineNetwork");
        String reseau = this.ioCommandes.lireReseau();
        System.out.println("IP et port => " + reseau);
        //Parse
        String[] ipport =reseau.split("[:\\,]");
        System.out.println("Substrings length:" + ipport.length);
        for (int i = 0; i < ipport.length; i++) {
            System.out.println("Str[" + i + "]:" + ipport[i]);
        }

        stockCafe = (int)Float.parseFloat(strs[2]);
        stockThea = (int)Float.parseFloat(strs[5]);
        stockLait = (int)Float.parseFloat(strs[8]);
        restantCafe.setValue(100);
        restantCafe.setStringPainted(true);
        restantLait.setValue(100);
        restantLait.setStringPainted(true);
        restantThe.setValue(100);
        restantThe.setStringPainted(true);
        statutPreparation.setValue(0);
        statutPreparation.setStringPainted(true);
        Etat.setText("La Machine est "+etatMachine);
        ip_port.setText("Adresse IP : " +ipport[1] +' '+ " , Port :" + ipport[3]  );
        nbClients.setText(nbrClients[0]+" : "+nbrClients[1]);
        nbCafe.setText(nbrCafe[0]+" servis : "+ nbrCafe[1]);
        commandeEnCours.setText(cmd[0]+" : "+ cmd[1]);




        //Remplissage de produits
        DefaultListModel produits = new DefaultListModel();
        produits.addElement(listeProd[1] + " - " +listeProd[2] + "  :  " + listeProd[3]+ ' ' + '\u20AC' );
        produits.addElement(listeProd[4] + " - " + listeProd[5] + "  :  " + listeProd[6]+ ' ' + '\u20AC');
        produits.addElement(listeProd[7] + " - " + listeProd[8] + "  :  " + listeProd[9]+ ' ' + '\u20AC');
        listeProduits.setModel(produits);


        ImageIcon gif = new ImageIcon("U:/Interface graphique/src/Interface/cafe.gif");
        progressionCommande.setIcon(gif);

        /*if (etatMachine == "Au repos")
            progressionCommande.setText("pas de commande en cours");
        else progressionCommande.setIcon(gif);*/


    }
    //Progressbar
    public JProgressBar getRestantCafe(){
        return restantCafe;
    }

    public JProgressBar getRestantLait() {
        return restantLait;
    }

    public JProgressBar getRestantThe() {
        return restantThe;
    }

    public JProgressBar getStatutPreparation() {
        return statutPreparation;
    }

    public int getStockCafe() {
        return stockCafe;
    }

    public int getStockThea() {
        return stockThea;
    }

    public int getStockLait() {
        return stockLait;
    }

    //socket pour la recupération des informations à partir du server
    public static void main(String[] args) throws IOException {
        String ip = "192.168.1.196";
        int port = 867;
        Socket socket = new Socket(ip, port);
        MainFrame myFrame = new MainFrame(socket);
        Progress progress = new Progress(myFrame);
        Thread cafeThread = new Thread(progress);


        cafeThread.start();

    }
}
