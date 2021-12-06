package Interface;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MainFrame extends JFrame implements ActionListener {
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
    private JTextPane erreurCmd;
    private JTextPane textPane1;
    private JPanel Apercu;
    private JPanel Taille;
    private JLabel labelRecharge;
    private JLabel ValueCmd;
    private JLabel Bank;
    private JLabel ListCmd;

    public List<String> Errors;
    private ServerCmd serverCmd;


    public MainFrame(){
        setContentPane(mainPanel);
        setTitle("Machine à café");
        setSize(800,500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        this.add(login);
        this.Errors = new ArrayList<>();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.login) {
            this.serverCmd.Login_client();
        }
    }


    public JProgressBar getStatutPreparation() {
        return statutPreparation;
    }


    // Login-Signup
    public JButton getLogin() {
        return this.login;
    }

    public JButton getSignup() {
        return this.signup;
    }

    public JTextField getUsernamefield() {
        return this.usernamefield;
    }

    public JPasswordField getPasswordfield() {
        return this.passwordfield;
    }

    // Erreurs
    public JTextPane getErreurCmd() {
        return this.erreurCmd;
    }

    public void refreshErreur() {
        while (this.Errors.size() > 3) {
            this.Errors.remove(this.Errors.size() - 1);
        }
        StringBuilder ErreurRendu = new StringBuilder("Erreurs:");
        for (String err:this.Errors) {
            ErreurRendu.append("\n").append(err);
        }
        this.erreurCmd.setText(ErreurRendu.toString());
    }

    // Bank
    public JLabel getBank() {
        return this.Bank;
    }

    // Commandes
    public void refreshListCmd(@NotNull String line) {
        try {
            // Format : name,type,taille,etat
            // Debut de l'html
            StringBuilder list_cmd = new StringBuilder("<html>");

            // Ajout du titre
            list_cmd.append("<b>Liste des commandes</b><br>");

            // Ajout des commandes
            for (String cmd : line.split(";")) {
                if (cmd.length() != 0) {
                    list_cmd.append(cmd.split(",")[0]).append(", ").append(cmd.split(",")[1]);
                    list_cmd.append(" - ").append(cmd.split(",")[2]).append(" : ");
                    if (cmd.split(",")[3].equals("Waiting")) {
                        list_cmd.append("<font color='orange'>En attente</font>");
                    } else if (cmd.split(",")[3].equals("Finish")) {
                        list_cmd.append("<font color='green'>Finie</font>");
                    } else if (cmd.split(",")[3].equals("Cancelled")) {
                        list_cmd.append("<font color='red'>Annulée</font>");
                    } else {
                        list_cmd.append("<font color='grey'>Inconnue</font>");
                    }
                }
            }

            // Fin de l'html
            list_cmd.append("</html>");
            this.ListCmd.setText(list_cmd.toString());
        } catch (ArrayIndexOutOfBoundsException e) {
            this.ListCmd.setText("<html><font color='red'><b>Echec de la lecture !</b></font></html>");
        }
    }

    public void main(String[] args){
        MainFrame myFrame = new MainFrame();
        if (args.length != 3) {
            System.out.println("Usage:\n" + args[0] + " <ip serveur> <port serveur>");
            return;
        }
        Socket server = null;
        try {
            server = new Socket(args[1], Integer.parseInt(args[2]));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        this.serverCmd = new ServerCmd(server, this);
    }
}
