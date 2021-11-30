package Interface;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainFrame extends JFrame{
    private JProgressBar restantCafe;
    private JProgressBar restantLait;
    private JProgressBar restantThe;
    private JProgressBar statutPreparation;
    private JList<String> listeProduits;
    private JPanel mainPanel;
    private JPanel prepaPanel;
    private JLabel progressionCommande;



    public MainFrame(){
        setContentPane(mainPanel);
        setTitle("Machine à café");
        setSize(500,500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);


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


    public static void main(String[] args){
        MainFrame myFrame = new MainFrame();
        Progress progress = new Progress(myFrame);
        Thread cafeThread = new Thread(progress);


        cafeThread.start();

    }
}
