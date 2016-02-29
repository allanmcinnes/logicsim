package logicsim;

import java.util.HashSet;
import java.util.Set;

/**
 * Wire is connector between components.
 * A wire may have one driving component, which sets the signal carried by
 * the wire, and one or more driven components, which take the signal level
 * on the wire as an input.
 *
 * The Wire acts as the Subject in the Observer pattern, with Components
 * as Observers.
 *
 * @author Allan McInnes
 */
public class Wire {
    private String label;             // The name of the wire
    private Component driver = null;  // Driving component
    private Set<Component> driven = new HashSet<Component>(); // Connected non-driving components
    private Signal state = Signal.X;  // Current wire signal level

    // Number of wires that have been created. Used to generate labels.
    private static int n_wires = 0;

    /** Default constructor. */
    public Wire() {
        label = String.format("Wire%d", n_wires++);
    }

    /**
     * Construct a wire with a specified name.
     * @param label the name of the wire.
     */
    public Wire(String label) {
        this.label = label;
    }


    // Queries
    /**
     * Queries the name associated with the wire.
     * @return a string containing the name of the wire. */
    public String name() { return label; }

    /**
     * Queries the signal value currently on the wire.
     * @return the current signal level being driven on the wire. */
    public Signal getSignal() { return state; }

    /**
     * Checks whether a given component is connected to the wire as
     * a driven component (i.e. one that uses the wires as a source of input).
     * @param comp a component
     * @return true if comp is connected to the wire as a driven component.
     */
    public boolean isDrivenComponent(Component comp) {
        return driven.contains(comp);
    }

    /**
     * Checks whether a given component is the driving component of
     * the wire.
     * @param comp a component
     * @return true if comp is connected to the wire as the driver.
     */
    public boolean isDrivingComponent(Component comp) {
        return (comp != null && comp == driver);
    }

     /**
      * Checks whether a given component is connect to the wire, either
      * as the driving component or as one of the driven components.
      * @param comp a component
      * @return true if comp is connected to the wire.
      */
    public boolean isConnected(Component comp) {
        return (isDrivenComponent(comp) || isDrivingComponent(comp));
    }

     /**
      * Checks whether a driving component has been defined for the wire.
      * @return true if the wire has been connected to a driving component
      */
    public boolean hasDrivingComponent() {
        return driver != null;
    }

    // Commands
    /**
     * Defines a component as the driving component of the wire (the
     * component responsible for setting the signal level on the wire).
     * In this simple model, a wire can only have one driving component.
     * A more complex model would allow multiple drivers, but would then
     * require a way of resolving the signal level when different components
     * drive different levels on to the wire.
     *
     * @param comp the component to use as the driver
     */
    public void setDrivingComponent(Component comp) {
        driver = comp;
    }

    /**
     * Connects a driven component to the wire. A driven component
     * treats the signal on the wire as an input.
     *
     * @param comp the component to add
     */
    public void attachDrivenComponent(Component comp) {
        driven.add(comp);
    }

    /**
     * Removes a driven component from the wire.
     *
     * @param comp the component to remove
     */
    public void detachDrivenComponent(Component comp) {
        driven.remove(comp);
    }

    /**
     * Sets the signal level on the wire.
     * @param new_state the new signal level
     */
    public void setSignal(Signal new_state) {
        if (state != new_state) {
            state = new_state;
            notifyDrivenComponents();
        }
    }

    /**
     * Notifies all observers of a change in the signal
     */
    private void notifyDrivenComponents() {
        for (Component c : driven) {
            c.update();
        }
    }

}
