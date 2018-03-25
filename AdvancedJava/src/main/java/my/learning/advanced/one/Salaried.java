package my.learning.advanced.one;

/**
 * Created by tdongsi on 3/25/18.
 */
public class Salaried extends Employee {
    public static final double DEFAULT_SALARY = 120_000;

    private double salary = DEFAULT_SALARY;

    public Salaried() {
        // NOTE: this means parent's default constructor is invoked
        // Equivalent to Employee()
    }

    public Salaried(String name) {
        this(name, DEFAULT_SALARY);
    }

    public Salaried(String name, double salary) {
        super(name);
        this.salary = salary;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public double getPay() {
        return salary / 24;
    }
}
