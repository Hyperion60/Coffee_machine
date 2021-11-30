package Coffee;

import Coffee.Products.Product;
import Coffee.Products.Taille;
import Structures.CommandState;

import java.time.LocalDateTime;
import java.util.Date;

public class Command {
    public LocalDateTime cmd_date;
    public LocalDateTime begin_date;
    public CommandState state;
    public Product product;
    public Taille taille;
    public float temperature;
    public int progress;

    public Command(Product product, Taille taille) {
        this.cmd_date = LocalDateTime.now();
        this.begin_date = LocalDateTime.now();
        this.state = CommandState.WAITING;
        this.product = product;
        this.taille = taille;
        this.temperature = 20f;
        this.progress = 0;
    }

    public void BeginPreparation() {
        this.begin_date = LocalDateTime.now();
        this.state = CommandState.PROGRESS;
    }
}
