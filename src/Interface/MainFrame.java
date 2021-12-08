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
    private int stockCafe, stockThea,stockLait;
    private server.IOCommandes ioCommandes;


    public MainFrame(Socket socket){
        this.ioCommandes = new IOCommandes(socket);
        Vector stock = new Vector();

        setContentPane(mainPanel);
        setTitle("Machine à café");
        setSize(500,500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        // Récupération des stocks
        this.ioCommandes.ecrireReseau("StockGet");
        String stocks = this.ioCommandes.lireReseau();
        System.out.println("StockGet => " + stocks);


        String[] strs = stocks.split("[:\\;\\,\\/]");
        System.out.println("Substrings length:" + strs.length);
        for (int i = 0; i < strs.length; i++) {
            System.out.println("Str[" + i + "]:" + strs[i]);
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




        //Remplissage de produits
        DefaultListModel produits = new DefaultListModel();
        produits.addElement("café long");
        produits.addElement("thé vert");
        produits.addElement("thé rouge");

        listeProduits.setModel(produits);

        ImageIcon gif = new ImageIcon("U:/Interface graphique/src/Interface/cafe.gif");

        progressionCommande.setIcon(gif);


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
