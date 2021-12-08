package Interface;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MainFrame extends JFrame implements ActionListener {
    private JPanel mainPanel;
    private JLabel progressionCommande;
    private JTabbedPane tabbedPane1;
    private JPanel Commande;
    private JPanel Produit;
    private JPanel Machine;
    private JPanel Logs;
    private JTextField usernamefield;
    private JPasswordField passwordfield;
    private JButton signup;
    private JButton recharge;
    private JFormattedTextField value_recharge;
    private JComboBox ProductList;
    private JComboBox SizeList;
    private JButton commanderButton;
    private JProgressBar progressCmd;
    private JTextPane erreurCmd;
    private JPanel Taille;
    private JLabel labelRecharge;
    private JLabel ValueCmd;
    private JLabel Bank;
    private JLabel ListCmd;
    private JButton connexion;
    private JPanel Connect;
    private JButton connect_server;
    private JTextField ip_addr_field;
    private JTextField port_field;
    private JLabel ip_addr;
    private JLabel port;
    private JLabel server_err;
    private JLabel Etat;
    private JLabel ProductName;
    private JPanel panel_com;
    private JPanel cmd_panel;
    private JLabel info_label;
    private JLabel info;
    private JLabel location_label;
    private JLabel location;
    private JLabel capacity_label;
    private JLabel state_label;
    private JLabel state;
    private JLabel client_nb_label;
    private JLabel client_nb;
    private JLabel cafe_nb_label;
    private JLabel nb_cafe;
    private JLabel ProductPrice;

    public List<String> Errors;
    public ServerCmd serverCmd;


    public MainFrame(String ip, int port) {
        setContentPane(mainPanel);
        setTitle("Machine à café");
        setSize(800,500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        this.Errors = new ArrayList<>();
        this.connect_server.addActionListener(this);
        this.connexion.addActionListener(this);
        this.signup.addActionListener(this);
        this.recharge.addActionListener(this);
        this.commanderButton.addActionListener(this);

        this.erreurCmd.setBackground(new Color(255, 206, 206));
        this.panel_com.setBackground(new Color(196,240, 255));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.connexion) {
            this.serverCmd.Login_client();
        } else if (e.getSource() == this.signup) {
            this.serverCmd.Signup_client();
        } else if (e.getSource() == this.connect_server) {
            try {
                String ip = this.ip_addr_field.getText();
                int port = Integer.parseInt(this.port_field.getText());
                Socket server = new Socket(ip, port);
                this.serverCmd = new ServerCmd(server, this);
                this.server_err.setText("<html><font color='green'>Connexion réussie</font></html>");
            } catch (ConnectException err) {
                try {
                    throw err;
                } catch (ConnectException ex) {
                    System.out.println(ex);
                    this.server_err.setText("<html><ul><li><font color='red'>Echec de la connexion !</font></li></ul></html>");
                }
            } catch (IOException err) {
                err.printStackTrace();
            } catch (NumberFormatException err) {
                this.server_err.setText("<html><ul><li><font color='red'>Port invalide !</font></li></ul></html>");
            }
        } else if (e.getSource() == this.recharge) {
            this.serverCmd.Recharge_client();
        } else if (e.getSource() == this.commanderButton) {
            this.serverCmd.NewCommande();
        }
    }

    // Login-Signup
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
            this.Errors.remove(0);
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

    public JFormattedTextField getValue_recharge() {
        return this.value_recharge;
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

    public JLabel getListCmd() {
        return this.ListCmd;
    }

    public JLabel getEtat() {
        return this.Etat;
    }

    public JProgressBar getProgressCmd() {
        return this.progressCmd;
    }

    public JLabel getValueCmd() {
        return this.ValueCmd;
    }

    // Produits
    public javax.swing.JComboBox getProductList() {
        return ProductList;
    }

    public JLabel getProductName() {
        return ProductName;
    }

    public JLabel getProductPrice() {
        return this.ProductPrice;
    }

    // Tailles
    public javax.swing.JComboBox getSizeList() {
        return this.SizeList;
    }

    public static void main(String[] args){
        if (args.length != 2) {
            System.out.println("Usage:\n UI_client.exe <ip serveur> <port serveur>");
            return;
        }
        MainFrame myFrame = new MainFrame(args[0], Integer.parseInt(args[1]));
    }

}
