import java.util.*;
import java.time.LocalDateTime;

public class Monitor {
    private List<String> OpenStatus;
    private String OpenStatusDescription;

    private int Level;
    private String LevelDescription;

    private List<String> Metric;
    private LocalDateTime LastCoffee;
    private int NumberCoffee;
    private float TempCoffee;
    private List<String> Errors;


    public Monitor() {
        this.OpenStatus = new ArrayList<>();
        this.OpenStatusDescription = "Default Status Description";
        this.Level = 0;
        this.LevelDescription = "Default Level Description";
        this.Metric = new ArrayList<>();
        this.LastCoffee = LocalDateTime.MIN;
        this.NumberCoffee = 0;
        this.TempCoffee = 0f;
        this.Errors = new ArrayList<>();
    }


    // OpenStatus
    public List<String> get_list_openstatus() {
        return this.OpenStatus;
    }

    public String get_int_openstatus(int i) {
        if (i < 0 || i > this.OpenStatus.size() - 1)
            return null;
        return this.OpenStatus.get(i);
    }

    public boolean set_openstatus(int i, String new_status) {
        if (i < 0 || i > this.OpenStatus.size() - 1 || new_status == null)
            return false;
        this.OpenStatus.set(i, new_status);
        return true;
    }

    public boolean add_openstatus(String new_status) {
        if (new_status == null)
            return false;
        this.OpenStatus.add(new_status);
        return true;
    }

    public boolean remove_openstatus(int i) {
        if (i < 0 || i > this.OpenStatus.size() - 1)
            return false;
        this.OpenStatus.remove(i);
        return true;
    }

    public String getOpenStatusDescription() {
        return this.OpenStatusDescription;
    }

    public boolean setOpenStatusDescription(String new_description) {
        if (new_description == null)
            return false;
        this.OpenStatusDescription = new_description;
        return true;
    }


    // Level
    public int getLevel() {
        return this.Level;
    }

    public boolean setLevel(int level) {
        if (level < 0)
            return false;
        this.Level = level;
        return true;
    }

    public String getLevelDescription() {
        return this.LevelDescription;
    }

    public boolean setLevelDescription(String new_description) {
        if (new_description == null)
            return false;
        this.LevelDescription = new_description;
        return true;
    }

    // Metric
    public List<String> get_list_Metric() {
        return this.Metric;
    }

    public String get_int_Metric(int i) {
        if (i < 0 || i > this.Metric.size() - 1)
            return null;
        return this.Metric.get(i);
    }

    public boolean set_Metric(int i, String new_metric) {
        if (i < 0 || i > this.Metric.size() - 1 || new_metric == null)
            return false;
        this.Metric.set(i, new_metric);
        return true;
    }

    public boolean add_Metric(String new_metric) {
        if (new_metric == null)
            return false;
        this.Metric.add(new_metric);
        return true;
    }

    // LastCoffee
    public LocalDateTime getLastCoffee() {
        return this.LastCoffee;
    }

    public void renewLastCoffee() {
        this.LastCoffee = LocalDateTime.now();
    }

    // Coffee Number
    public int getNumberCoffee() {
        return this.NumberCoffee;
    }

    public void addNumberCoffee() {
        this.NumberCoffee += 1;
    }

    // TempCoffee
    public float getTempCoffee() {
        return this.TempCoffee;
    }

    public boolean setTempCoffee(float temp) {
        if (temp < 0f)
            return false;
        this.TempCoffee = temp;
        return true;
    }

    // Errors
    public List<String> get_list_errors() {
        return this.Errors;
    }

    public String get_int_errors(int i) {
        if (i < 0 || i > this.Errors.size() - 1)
            return null;
        return this.Errors.get(i);
    }

    public boolean setError(int i, String new_error) {
        if (i < 0 || i > this.Errors.size() - 1 || new_error == null)
            return false;
        this.Errors.set(i, new_error);
        return true;
    }

    public boolean addError(String new_error) {
        if (new_error == null)
            return false;
        this.Errors.add(new_error);
        return true;
    }

    public boolean delError(int i) {
        if (i < 0 || i > this.Errors.size() - 1)
            return false;
        this.Errors.remove(i);
        return true;
    }
}

