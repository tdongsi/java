package my.learning.patterns.state;

/**
 * Created by tdongsi on 2/22/18.
 */
public interface State {
    void operation(StatefulObject object, String params);
}
