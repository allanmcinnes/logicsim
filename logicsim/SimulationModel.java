package logicsim;

/**
 * A SimulationModel has a current time, and can have events added to it.
 *
 * @author Allan McInnes
 */
public interface SimulationModel {
    /**
     * @return the current simulation time in nanoseconds
     */
    public double time();

    /**
     * Adds a new future event to the model.
     * @param event a new event (which must have a time later
     *  than the current time).
     * @throws IllegalArgumentException if event delay <= 0.0
     */
    public void newEvent(Event event);
}
