package connexion;

import Structures.Privileges;

public class User {
    private String Name;
    private String Password;
    private Privileges privileges;
    private float bank;


    public User(String name, String password, Privileges privileges) {
        this.Name = name;
        this.privileges = privileges;
        this.Password = password;
        this.bank = 0;
    }

    public User(User user) {
        this.Name = user.getName();
        this.privileges = user.getPrivileges();
        this.Password = user.getPassword();
        this.bank = user.getBank();
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

    public void recharge(float value) {
        this.bank += value;
    }

    public float getBank() {
        return this.bank;
    }

    public Privileges getPrivileges() {
        return this.privileges;
    }

    public void setPrivileges(Privileges privileges) {
        this.privileges = privileges;
    }

    public boolean checkPassword(String password) {
        return this.Password.equals(password);
    }

    public String getPassword() { return this.Password; }

    public boolean setPassword(String old_password, String new_password) {
        if (!checkPassword(old_password))
            return false;
        this.Password = new_password;
        return true;
    }
}
