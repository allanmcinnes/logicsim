package logicsim;

/**
 * Events represent transitions in the signal level on a wire at
 * a specific time. Events are queued in the scheduler, and when the event
 * is "executed", the corresponding wire signal level transition occurs. The
 * event time is measured in nanoseconds.
 *
 * Event can be viewed as a Command in the Command pattern, with a Wire as the
 * Receiver. It could easily be extended to allow multiple Wires to be modified
 * by a single Event.
 *
 * @author Allan McInnes
 */
public class Event implements Comparable<Event> {
    private final double event_time;   // Time at which the event should occur
    private final Wire wire;           // Wire the event occurs on
    private final Signal level;        // The event is a transition to this level

    // Creation
    /**
     * Constructor.
     * @param t the time the event occurs, measured in nanoseconds
     * @param w the wire the event occurs on
     * @param l the new signal level that will appear on the wire when
     * the event occurs
     */
    public Event(double t, Wire w, Signal l) {
        event_time = t;
        wire = w;
        level = l;
    }

    /** Copy constructor. */
    public Event(Event other) {
        event_time = other.event_time;
        wire = other.wire;
        level = other.level;
    }

    // Queries
    /**
     * Queries the time of the event.
     * @return the event time in nanoseconds
     */
    public double time() { return event_time; }

    // Commands
    /**
     * Make the transition defined by the event.
     */
    public void execute() {
        wire.setSignal(level);
    }

    /**
     * Events are ordered in the scheduler strictly based on their time
     * and not on any other value.
     *
     * @param other the Event to be compared
     *
     * @return a negative integer occurs earlier than the specified Event, otherwise
     * return a positive integer.
     */
    public int compareTo(Event other) {
        if (this.time() < other.time()) { return -1; }
        return 1; 
    }

    /**
     * Returns a string representation of an event. The string consists of
     * the event time, the wire that the event occurs on, and the new signal
     * level, all wrapped in curly braces. For example:
     *
     * "{time: 1.3, wire: Wire0, level: LOW}"
     */
    @Override
    public String toString() {
        return String.format("{time: %f, wire: %s, level: %s}", time(), wire.name(), level);
    }
}
