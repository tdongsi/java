package my.learning.patterns.state;

/**
 * Created by tdongsi on 2/22/18.
 */
public class StateTwo implements State {
    private int count = 0;
    @Override
    public void operation(StatefulObject object, String params) {
        System.out.println(params.toUpperCase());

        if (++count > 1) {
            object.setState(new StateOne());
        }
    }
}
