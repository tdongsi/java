package my.learning.advanced.one;

/**
 * Created by tdongsi on 3/25/18.
 */
public interface Company {
    default String getName() {
        return "defaults.Company";
    }
}
