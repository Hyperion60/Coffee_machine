package server;

import Structures.Globals;

public class AJAXServer implements Runnable {
    private Globals lists;
    private Thread thread;

    public AJAXServer(Globals lists) {
        this.lists = lists;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }

    @Override
    public void run() {
        while (true) {
            // System.out.println("[LOG] Begin AJAX sleep !");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // System.out.println("[LOG] End AJAX sleep !");
            // Refresh machine variables each second
            lists.coffee.RefreshCommand();
            // System.out.println("[LOG] Server Refresh !");
        }
    }
}
