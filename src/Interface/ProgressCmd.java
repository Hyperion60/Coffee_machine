package Interface;

public class ProgressCmd implements Runnable {
    IOCommand ioCommand;
    MainFrame mainFrame;

    ProgressCmd(IOCommand ioCommand, MainFrame mainFrame) {
        this.ioCommand = ioCommand;
        this.mainFrame = mainFrame;
    }

    @Override
    public void run() {
        String response;
        while (true) {
            this.ioCommand.ecrireReseau("Progress");
            response = this.ioCommand.lireReseau();
            if (response.equals("Commande terminée ou non traitée") || response.equals("Commande terminée") || response.equals("Erreur : Toutes vos commandes sont terminées")
            || response.equals("Toutes vos commandes sont terminées")) {
                this.mainFrame.getEtat().setText("<html><font color='green'>Terminée</font></html>");
                this.mainFrame.getProgressCmd().setValue(100);
                return;
            }
            if (response.split(":")[0].equals("En cours de préparation, temps restant")) {

                try {
                    int remain = Integer.parseInt(response.split(":")[1].split("/")[0]);
                    int total = Integer.parseInt(response.split(":")[1].split("/")[1].split(" ")[0]);
                    int percent = (remain * 100)/total;
                    System.out.println("[LOG]" + percent);
                    if (percent >= 0) {
                        this.mainFrame.getProgressCmd().setValue(100 - percent);
                        this.mainFrame.getValueCmd().setText("<html><b>" + (100 - percent) + " %</b></html>");
                        this.mainFrame.getEtat().setText("<html><font color='green'>En cours</font></html>");
                    }
                } catch (NumberFormatException e) {
                    this.mainFrame.Errors.add("Impossible de lire le temps restant !");
                    this.mainFrame.refreshErreur();
                } catch (ArrayIndexOutOfBoundsException e) {
                    this.mainFrame.Errors.add("Format de données invalide !");
                    this.mainFrame.refreshErreur();
                }
            } else if (response.split(" : ")[0].equals("Commande en attente, place dans la file d'attente")) {
                try {
                    int place = Integer.parseInt(response.split(" : ")[1]);
                    this.mainFrame.getProgressCmd().setValue(0);
                    this.mainFrame.getEtat().setText("<html><font color='orange'>En attente (" + place + ")");
                } catch (NumberFormatException e) {
                    this.mainFrame.Errors.add("Impossible de lire le temps restant !");
                    this.mainFrame.refreshErreur();
                } catch (ArrayIndexOutOfBoundsException e) {
                    this.mainFrame.Errors.add("Format de données invalide !");
                    this.mainFrame.refreshErreur();
                }
            } else {
                this.mainFrame.getProgressCmd().setValue(0);
                this.mainFrame.getEtat().setText("<html><font color='black'>----------</font></html>");
                this.mainFrame.Errors.add("Format de données invalide !");
                this.mainFrame.refreshErreur();
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            }
        }
    }
}
