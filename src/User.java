public class User {
    private String Name;
    private Privileges privileges;
    private float bank;

    public User(String name, Privileges privileges) {
        this.Name = name;
        this.privileges = privileges;
        this.bank = 0;
    }

    // Name
    public String getName() {
        return this.Name;
    }
}
