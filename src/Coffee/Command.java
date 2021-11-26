package Coffee;

import Coffee.Products.Product;
import Structures.CommandState;

import java.time.LocalDateTime;
import java.util.Date;

public class Command {
    public LocalDateTime cmd_date;
    public LocalDateTime begin_date;
    public CommandState state;
    public Product product;
    public float temperature;
    public int progress;

    public Command(Product product) {
        this.cmd_date = LocalDateTime.now();
        this.begin_date = LocalDateTime.now();
        this.state = CommandState.WAITING;
        this.product = product;
        this.temperature = 20f;
        this.progress = 0;
    }

    public void BeginPreparation() {
        this.begin_date = LocalDateTime.now();
        this.state = CommandState.PROGRESS;
    }
}
