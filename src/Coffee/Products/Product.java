package Coffee.Products;

import java.util.List;

public class Product {
    private String type;
    private String name;
    private float temperature;
    private float price;
    private int duree; // Nombre de secondes
    // Consommation de base
    private float coffee_consumption;
    private float thea_consumption;
    private float milk_consumption;

    public Product(String name, String type, int duree, float temperature, float price,
                   float coffee_consumption, float thea_consumption, float milk_consumption) {
        this.type = type;
        this.name = name;
        this.duree = duree;
        this.temperature = temperature;
        this.price = price;
        this.coffee_consumption = coffee_consumption;
        this.thea_consumption = thea_consumption;
        this.milk_consumption = milk_consumption;
    }

    public String getType() {
        return this.type;
    }

    public String getName() {
        return this.name;
    }

    public float getPrice() {
        return price;
    }

    public int getDuree() {
        return duree;
    }

}
