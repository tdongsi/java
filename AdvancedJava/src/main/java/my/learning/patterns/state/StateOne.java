package my.learning.patterns.state;

/**
 * Created by tdongsi on 2/22/18.
 */
public class StateOne implements State {
    @Override
    public void operation(StatefulObject object, String params) {
        System.out.println(params.toLowerCase());
        object.setState(new StateTwo());
    }
}
