package Coffee.Products;

import javax.lang.model.type.NullType;

public class Product {
    private String type;
    private String name;
    private float temperature;
    private float price;
    private int duree; // Nombre de secondes
    // Consommation de base
    private float coffee_consumption;
    private float milk_consumption;
    private float thea_consumption;


    public Product(String name, String type, int duree, float temperature, float price,
                   float coffee_consumption, float thea_consumption, float milk_consumption) {
        if (name == null)
            throw new NullPointerException("Name cant be null");
        if (type == null)
            throw new NullPointerException("Type cant be null");
        if (duree <= 0)
            throw new NullPointerException("La durée doit être positive");
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

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) { this.name = name; }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getDuree() {
        return duree;
    }

    public void setCoffee_consumption(float coffee_consumption) {
        this.coffee_consumption = coffee_consumption;
    }

    public void setMilk_consumption(float milk_consumption) {
        this.milk_consumption = milk_consumption;
    }

    public void setThea_consumption(float thea_consumption) {
        this.thea_consumption = thea_consumption;
    }
}
