package my.learning.patterns.state;

/**
 * Created by tdongsi on 2/22/18.
 */
public class Demo {
    public static void main(String[] args) {
        final StatefulObject sc = new StatefulObject();

        sc.doOperation("Monday");
        sc.doOperation("Tuesday");
        sc.doOperation("Wednesday");
        sc.doOperation("Thursday");
        sc.doOperation("Friday");
        sc.doOperation("Saturday");
        sc.doOperation("Sunday");
    }
}
