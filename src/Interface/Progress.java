package Interface;

public class Progress implements Runnable {
    private MainFrame mainFrame;
    public Progress(MainFrame mainFrame){
        this.mainFrame = mainFrame;

    }


    @Override
    public void run()
    {   int stockCafe = mainFrame.getStockCafe();
        int stockThea = mainFrame.getStockThea();
        int stockLait = mainFrame.getStockLait();
        try {
            while (true) {
                mainFrame.ioCommandes.ecrireReseau("CmdStatus");
                String status = mainFrame.ioCommandes.lireReseau();
                System.out.println(status);
                try {
                    int percent = Integer.parseInt(status.split(":")[0]);
                    String product = status.split(":")[1];
                    mainFrame.getStatutPreparation().setValue(percent);
                    mainFrame.getcommandeEnCours().setText(product);
                } catch (NumberFormatException e) {
                    System.out.println(status);
                    mainFrame.getStatutPreparation().setValue(0);
                    mainFrame.getcommandeEnCours().setText("Aucune commande en cours");
                }
                // fill the menu bar
                mainFrame.refreshStock();
                mainFrame.getRestantCafe().setValue(mainFrame.getStockCafe());
                mainFrame.getRestantLait().setValue(mainFrame.getStockLait());
                mainFrame.getRestantThe().setValue(mainFrame.getStockThea());
                // mainFrame.getStatutPreparation().setValue(100 - i);
                // delay the thread
                Thread.sleep(500);
            }
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println("Interrupted exeption");
        }
    }
}
