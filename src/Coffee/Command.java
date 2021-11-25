package Coffee;

import Coffee.Products.Product;
import Structures.CommandState;

import java.time.LocalDateTime;

public class Command {
    public LocalDateTime begin_date;
    public CommandState state;
    public Product product;
    public float temperature;
    public int progress;

    public Command(Product product) {
        this.begin_date = LocalDateTime.now();
        this.state = CommandState.WAITING;
        this.product = product;
        this.temperature = 20f;
        this.progress = 0;
    }
}
