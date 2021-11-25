package Coffee.Products;

public class Taille {
    public String taille;
    // Coeficients
    public float coffee_consumption;
    public float thea_consumption;
    public float milk_consumption;

    public Taille(String taille, float coffee, float thea, float milk) {
        this.taille = taille;
        this.coffee_consumption = coffee;
        this.thea_consumption = thea;
        this.milk_consumption = milk;
    }
}
