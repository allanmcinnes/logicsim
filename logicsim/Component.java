package logicsim;

/**
 * Component is an abstract base class for digital logic components.
 * It implements the SimulationModel interface to allow recursive
 * construction of models.
 *
 * Components acts as Observers of Wires.
 *
 * @author aim
 */

public abstract class Component implements SimulationModel {
    protected SimulationModel model; // Simulation model

    /**
     * Default constructor
     */
    public Component() {
        this.model = null;
    }

    /**
     * Constructor
     * @param model the parent model of this component
     */
    public Component(SimulationModel model) {
        this.model = model;
    }

    // Queries

    /**
     * @return the current simulation time in nanoseconds
     */
    public double time() {
        return model.time();
    }

    // Commands
    /**
     * Define the parent model to be used when simulating the component.
     * @param model the parent model of this component
     */
    public void setSimulationModel(SimulationModel model) {
        this.model = model;
    }

    /**
     * This method is in the interface to support Blocks.
     * The other option would be to only implement methods like this at
     * the Block level. See GoF Design Patterns book.
     * @param comp
     * @throws UnsupportedOperationException
     */
    public void addComponent(Component comp) {
        throw new UnsupportedOperationException("Only supported by Blocks.");
    }

    /**
     * This method is in the interface to support Blocks.
     * The other option would be to only implement methods like this at
     * the Block level. See GoF Design Patterns book.
     * @param comp
     * @throws UnsupportedOperationException
     */
    public void removeComponent(Component comp) {
        throw new UnsupportedOperationException("Only supported by Blocks.");
    }

    /**
     * Updates the component's state based on the new inputs that have
     * resulted from a transition in one or more input signals.
     */
    public abstract void update();

    /**
     * Adds a new future event to the model.
     * @param event a new event (which must have a time later
     *  than the current time).
     * @throws IllegalArgumentException if event delay <= 0.0
     */
    public void newEvent(Event event) {
        model.newEvent(event);
    }
}
