package Coffee.Products;

public class Taille {
    public String taille;
    // Coeficients
    public float coffee_consumption;
    public float thea_consumption;
    public float milk_consumption;
    public float price_coef;

    public Taille(String taille, float coffee, float thea, float milk, float price) {
        this.taille = taille;
        if (coffee < 0f || thea < 0f || milk < 0f || price < 0f)
            throw new ArithmeticException();
        this.coffee_consumption = coffee;
        this.thea_consumption = thea;
        this.milk_consumption = milk;
        this.price_coef = price;
    }
}
