package Coffee.Products;

import java.util.List;

public class Product {
    private String type;
    private String name;
    private float temperature;
    private int duree; // Nombre de secondes
    // Consommation de base
    private float coffee_consumption;
    private float thea_consumption;
    private float milk_consumption;

    public Product(String name, String type, int duree, float temperature,
                   float coffee_consumption, float thea_consumption, float milk_consumption) {
        this.type = type;
        this.name = name;
        this.duree = duree;
        this.temperature = temperature;
        this.coffee_consumption = coffee_consumption;
        this.thea_consumption = thea_consumption;
        this.milk_consumption = milk_consumption;
    }
}
