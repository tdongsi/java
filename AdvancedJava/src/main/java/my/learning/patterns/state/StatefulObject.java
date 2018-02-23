package my.learning.patterns.state;

/**
 * Created by tdongsi on 2/22/18.
 */
public class StatefulObject {
    private State state;

    public StatefulObject() {
        setState(new StateOne());
    }

    public void setState(final State newState) {
        this.state = newState;
    }

    public void doOperation(String params) {
        this.state.operation(this, params);
    }
}
