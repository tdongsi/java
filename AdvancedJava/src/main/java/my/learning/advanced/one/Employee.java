package my.learning.advanced.one;

import java.time.LocalDate;

/**
 * Created by tdongsi on 3/25/18.
 */
public abstract class Employee {
    public static final String DEFAULT_NAME = "UNKNOWN";
    private static int nextId;

    private Integer id;
    private String name;
    private LocalDate hireDate;

    public Employee() {
        this(DEFAULT_NAME);
    }

    public Employee(String name) {
        id = nextId++;
        this.name = name;
        hireDate = LocalDate.now();
    }

    public Integer getId() {
        return id;
    }

    private void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    private void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public abstract double getPay();

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", hireDate=" + hireDate +
                '}';
    }
}
