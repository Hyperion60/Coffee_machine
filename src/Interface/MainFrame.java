package Interface;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class MainFrame extends JFrame{
    private JProgressBar restantCafe;
    private JProgressBar restantLait;
    private JProgressBar restantThé;
    private JProgressBar statutPreparation;
    private JList listeProduits;
    private JPanel mainPanel;
    private JPanel prepaPanel;


    public MainFrame(){
        setContentPane(mainPanel);
        setTitle("Machine à café");
        setSize(500,300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        restantCafe.setValue(100);
        restantCafe.setStringPainted(true);
        restantLait.setValue(100);
        restantLait.setStringPainted(true);
        restantThé.setValue(100);
        restantThé.setStringPainted(true);
        statutPreparation.setValue(0);
        statutPreparation.setStringPainted(true);

    }
    //Progressbar
    public JProgressBar getRestantCafe(){
        return restantCafe;
    }

    public JProgressBar getRestantLait() {
        return restantLait;
    }

    public JProgressBar getRestantThé() {
        return restantThé;
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
