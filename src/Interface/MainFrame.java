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
    private JLabel progressionCommande;
    private JTabbedPane tabbedPane1;
    private JPanel Commande;
    private JPanel Produit;
    private JPanel Machine;
    private JPanel Logs;
    private JTextField usernamefield;
    private JPasswordField passwordfield;
    private JButton login;
    private JButton signup;
    private JTextArea listProduit;
    private JButton recharge;
    private JFormattedTextField value_recharge;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JButton commanderButton;
    private JProgressBar progressCmd;
    private JList listCmd;
    private JTextPane erreurCmd;
    private JTextPane textPane1;
    private JPanel Apercu;
    private JPanel Taille;
    private JLabel labelRecharge;
    private JLabel ValueCmd;


    public MainFrame(){
        setContentPane(mainPanel);
        setTitle("Machine à café");
        setSize(800,500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);



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
