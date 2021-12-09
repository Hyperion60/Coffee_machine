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
    private JButton rafraichirButton;
    private JLabel capacity;
    private JLabel server_title;
    private JProgressBar milk_bar;
    private JProgressBar thea_bar;
    private JProgressBar coffee_bar;
    private JLabel coffee_label;
    private JLabel milk_label;
    private JLabel thea_label;
    private JTextField coffee_add;
    private JButton recharge_coffee;
    private JButton recharge_thea;
    private JButton recharge_milk;
    private JTextField thea_add;
    private JTextField milk_add;
    private JLabel recharge_coffee_label;
    private JLabel machine_error;
    private JLabel ProductPrice;

    public List<String> Errors;
    public List<String> MachineErrors;
    public ServerCmd serverCmd;


    public MainFrame(String ip, int port) {
        setContentPane(mainPanel);
        setTitle("Machine à café");
        setSize(800,500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        this.Errors = new ArrayList<>();
        this.MachineErrors = new ArrayList<>();
        this.connect_server.addActionListener(this);
        this.connexion.addActionListener(this);
        this.signup.addActionListener(this);
        this.recharge.addActionListener(this);
        this.commanderButton.addActionListener(this);
        this.rafraichirButton.addActionListener(this);
        this.recharge_coffee.addActionListener(this);
        this.recharge_thea.addActionListener(this);
        this.recharge_milk.addActionListener(this);

        this.erreurCmd.setBackground(new Color(255, 206, 206));
        this.panel_com.setBackground(new Color(196,240, 255));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.connexion) {
            this.serverCmd.Login_client();
        } else if (e.getSource() == this.signup) {
            this.serverCmd.Signup_client();
        } else if (e.getSource() == this.rafraichirButton) {
            this.init_machine(false);
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
            this.init_machine(true);
        } else if (e.getSource() == this.recharge) {
            this.serverCmd.Recharge_client();
        } else if (e.getSource() == this.commanderButton) {
            this.serverCmd.NewCommande();
        } else if (e.getSource() == this.recharge_coffee) {
            this.serverCmd.ioCommand.ecrireReseau("StockAdd:coffee," + this.coffee_add.getText());
            String response = this.serverCmd.ioCommand.lireReseau();
            System.out.println(response);
            if (response.split(":")[0].equals("Erreur") || response.split(" : ")[0].equals("Erreur")) {
                this.MachineErrors.add(response);
                this.refreshMachineErreur();
            }
            this.init_machine(false);
        } else if (e.getSource() == this.recharge_thea) {
            this.serverCmd.ioCommand.ecrireReseau("StockAdd:thea," + this.thea_add.getText());
            String response = this.serverCmd.ioCommand.lireReseau();
            if (response.split(":")[0].equals("Erreur")) {
                this.MachineErrors.add(response);
                this.refreshMachineErreur();
            }
            this.init_machine(false);
        } else if (e.getSource() == this.recharge_milk) {
            this.serverCmd.ioCommand.ecrireReseau("StockAdd:milk," + this.milk_add.getText());
            String response = this.serverCmd.ioCommand.lireReseau();
            if (response.split(":")[0].equals("Erreur")) {
                this.MachineErrors.add(response);
                this.refreshMachineErreur();
            }
            this.init_machine(false);
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

    public void refreshMachineErreur() {
        while (this.MachineErrors.size() > 3) {
            this.MachineErrors.remove(0);
        }
        StringBuilder ErreurRendu = new StringBuilder("<html><b>Erreurs:</b>");
        for (String err:this.MachineErrors) {
            ErreurRendu.append("<br>").append(err);
        }
        ErreurRendu.append("</html>");
        this.machine_error.setText(ErreurRendu.toString());
    }


    // Bank
    public JLabel getBank() {
        return this.Bank;
    }

    public JFormattedTextField getValue_recharge() {
        return this.value_recharge;
    }

    // Commandes
    /*
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
    }*/

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


    // Machine
    protected void init_machine(boolean init) {
        if (init) {
            this.info_label.setText("<html><b>Informations :</b></html>");
            this.location_label.setText("<html><b>Localisation :</b></html>");
            this.capacity_label.setText("<html><b>Capacité de la machine :</b></html>");
            this.state_label.setText("<html><b>Etat de la machine :</b></html>");
            this.client_nb_label.setText("<html><b>Nombre de clients servis :</b></html>");
            this.cafe_nb_label.setText("<html><b>Nombre de cafés servis :</b></html>");
        }

        this.serverCmd.ioCommand.ecrireReseau("Machine_infos");
        this.info.setText("<html><b>" + this.serverCmd.ioCommand.lireReseau() + "</b></html>");
        this.serverCmd.ioCommand.ecrireReseau("Location");
        this.location.setText("<html><b>" + this.serverCmd.ioCommand.lireReseau() + "</b></html>");
        this.serverCmd.ioCommand.ecrireReseau("Capacity");
        this.capacity.setText("<html><b>" + this.serverCmd.ioCommand.lireReseau() + " kg</b></html>");
        this.serverCmd.ioCommand.ecrireReseau("MachineState");
        this.state.setText("<html><b>" + this.serverCmd.ioCommand.lireReseau() + "</b></html>");
        this.serverCmd.ioCommand.ecrireReseau("ClientStat");
        String nb_client = this.serverCmd.ioCommand.lireReseau().split(":")[1];
        this.client_nb.setText("<html><b>" + nb_client + "</b></html>");
        this.serverCmd.ioCommand.ecrireReseau("CafeNb");
        this.nb_cafe.setText("<html><b>" + this.serverCmd.ioCommand.lireReseau().split(":")[1] + "</b></html>");

        this.serverCmd.ioCommand.ecrireReseau("StockGet");
        String response = this.serverCmd.ioCommand.lireReseau();
        try {
            String metric = response.split(":")[1];
            int i = 0;
            for (String bar: metric.split(";")) {
                if (bar.length() != 0) {
                    float remain = Float.parseFloat(bar.split(",")[1].split("/")[0]);
                    float total = Float.parseFloat(bar.split(",")[1].split("/")[1]);
                    if (i == 0) {
                        this.coffee_bar.setValue((int)((remain * 100) / total));
                        this.coffee_label.setText("<html><b>(" + (int)remain + "/" + (int)total + ") Réserve de café</b></html>");
                        i++;
                    } else if (i == 1) {
                        this.thea_bar.setValue((int)((remain * 100) / total));
                        this.thea_label.setText("<html><b>(" + (int)remain + "/" + (int)total + ") Réserve de thé</b></html>");
                        i++;
                    } else {
                        this.milk_bar.setValue((int)((remain * 100) / total));
                        this.milk_label.setText("<html><b>(" + (int)remain + "/" + (int)total + ") Réserve de lait</b></html>");
                    }
                }
            }
        } catch (NumberFormatException e) {
            this.Errors.add("Erreur: Format invalide !");
            this.refreshErreur();
        }
    }

    public static void main(String[] args){
        if (args.length != 2) {
            System.out.println("Usage:\n UI_client.exe <ip serveur> <port serveur>");
            return;
        }
        MainFrame myFrame = new MainFrame(args[0], Integer.parseInt(args[1]));
    }

}

