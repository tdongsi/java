package my.learning.advanced.one;

/**
 * Created by tdongsi on 3/25/18.
 */
public interface Contractor {
    String getFirst();
    String getLast();
    void doWork();

    default String getName() {
        return String.format("%s %s", getFirst(), getLast());
    }
}
