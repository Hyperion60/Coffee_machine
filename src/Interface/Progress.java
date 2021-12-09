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
        int i = 100;
        try {
            while (i >= 0) {
                // fill the menu bar
                mainFrame.getRestantCafe().setValue(stockCafe);
                mainFrame.getRestantLait().setValue(stockThea);
                mainFrame.getRestantThe().setValue(stockLait);
                mainFrame.getStatutPreparation().setValue(100 - i);
                // delay the thread
                Thread.sleep(500);
                i -= 1;
            }
        }catch (Exception e) {System.out.println("Interrupted exeption");
        }
    }
}
