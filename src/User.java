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

    public void setName(String name) {
        this.Name = name;
    }

    public boolean paiement(int sum) {
        if (sum > this.bank)
            return false;
        this.bank -= sum;
        return true;
    }

    public void recharge(int value) {
        this.bank += value;
    }

    public Privileges getPrivileges() {
        return this.privileges;
    }

    public void setPrivileges(Privileges privileges) {
        this.privileges = privileges;
    }
}
