package logicsim;

/**
 * An example of a ComponentDecorator implementation. This one just performs
 * simple logging of events.
 *
 * @author Allan McInnes
 */
public class LoggingDecorator extends ComponentDecorator {

    /**
     * Constructor
     * @param comp component to decorate
     * @throws IllegalArgumentException if comp is not a valid Component
     */
    public LoggingDecorator(Component comp) {
        super(comp);
    }

    /**
     * Do nothing.
     */
    @Override
    public void postEventAction() {
        // No post actions in this case
    }

    /**
     * Log the fact that this component has been updated.
     */
    @Override
    public void preEventAction() {
        System.out.printf("--> Component %s generated an event at time %f\n", comp, model.time());
    }
}
