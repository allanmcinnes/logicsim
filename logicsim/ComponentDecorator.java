package logicsim;

/**
 * Wraps a component with some kind of additional behaviour that
 * occurs whenever the component generates an event.
 *
 * This is an instance of the Decorator pattern.
 * 
 * @author Allan McInnes
 */
public abstract class ComponentDecorator extends Component {

    protected Component comp; // The decorated component

    /**
     * Constructor
     * @param comp component to decorate
     * @throws IllegalArgumentException if comp is not a valid Component
     */
    public ComponentDecorator(Component comp) {
        if (comp != null) {
            this.comp = comp;
            comp.setSimulationModel(this);
        } else {
            throw new IllegalArgumentException("Cannot decorate null Components.");
        }
    }

    /**
     * Updates the component's state based on the new inputs that have
     * resulted from a update in one or more input signals.
     */
    @Override
    public void update() {
        // Events act directly on individual components, so no need
        // to do anything here since the decorated component will
        // be directly accessed any Events that change it.
    }

    /**
     * Add a new event to the model
     * @param event the new event
     */
    @Override
    public void newEvent(Event event) {
        preEventAction();
        model.newEvent(event);
        postEventAction();
    }

    /**
     * The action to perform prior to posting a new event.
     */
    public abstract void preEventAction();

    /**
     * The action to perform after posting a new event.
     */
    public abstract void postEventAction();
}
