package Interface;

public class Progress implements Runnable {
    private MainFrame mainFrame;
    public Progress(MainFrame mainFrame){
        this.mainFrame = mainFrame;

    }
    @Override
    public void run()
    {
        int i = 100;
        try {
            while (i >= 0) {
                // fill the menu bar
                mainFrame.getRestantCafe().setValue(i - 1);
                mainFrame.getRestantLait().setValue(i - 1);
                mainFrame.getRestantThé().setValue(i - 1);
                mainFrame.getStatutPreparation().setValue(100 - i);
                // delay the thread
                Thread.sleep(500);
                i -= 1;
            }
        }catch (Exception e) {
        }
    }
}
