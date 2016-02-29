package logicsim;

/**
 * Scheduler executes a simulation by firing scheduled events in time order. The
 * simulation time advances in variable steps, jumping from one event
 * execution time to the next. This is more efficient and more accurate
 * than moving the execution forward in fixed-size steps as a discrete-time
 * simulation would do.
 *
 * The scheduler maintains a queue of future events, ordered by the time of
 * their execution. As events are pulled from the top of the queue and fired,
 * the simulation time is advanced. As a result of firing an event, one or
 * more new events may be scheduled for the future. The simulation continues
 * until either no new events are scheduled, or some defined upper bound
 * on the simulation time is reached.
 *
 * The simulation time is measured in nanoseconds.
 *
 * @author Allan McInnes
 */

import java.util.PriorityQueue;

public class Scheduler implements SimulationModel {
    private double current_time;         // The current simulation time in ns
    private double stop_time;            // The upper bound on simulation time
    private boolean is_bounded;          // Is there a bound on sim time?
    private PriorityQueue<Event> events = new PriorityQueue<Event>(); // Scheduled events
    private static final double EPSILON = 0.001; // Times accurate to 0.1% are good enough

    // Singleton scheduler
    private static final Scheduler sched = new Scheduler();

    // Constants
    /** Default stop time when no bound is set */
    public static final double NO_BOUND = -1.0;
    /** Simulation start time */
    public static final double START_TIME = 0.0;

    // Creation
    /** Default constructor. */
    private Scheduler() {
        current_time = START_TIME;
        stop_time = NO_BOUND;
        is_bounded = false;
    }

    /**
     * Provides access the Scheduler.
     * @return an instance of a Scheduler
     */
    public static Scheduler getScheduler() {
        return sched;
    }

    // Queries
    /**
     * @return the current simulation time in nanoseconds
     */
    public double time() { return current_time; }

    // Commands
    /**
     * Execute a simulation. Execution proceeds until there are no
     * future events scheduled, or until an optional upper bound on the
     * simulation time is reached.
     */
    public void run() {
        Event event;

        // Keep executing events until the queue there aren't any more to execute
        // or the simulation time will exceed the stopping time.
        while(!events.isEmpty()
                && (!is_bounded || events.peek().time() < stop_time)) {
            // Get the next event
            event = events.poll();

            // Advance the simulation time and execute the event
            //System.out.println(event);
            current_time = event.time();
            event.execute();
        }

        assert (events.isEmpty() || (is_bounded && events.peek().time() >= stop_time));
    }

    /**
     * Sets the upper bound on simulation time, in nanoseconds
     * @param time the maximum simulation time (must be > 0)
     */
    public void setStopTime(double time) {
        assert (time >= START_TIME);

        stop_time = time;
        is_bounded = true;  // Must be, since there's now a stop time

        assert (Math.abs(stop_time - time) < EPSILON*Math.abs(time));
        assert (is_bounded);
    }

    /**
     * Removes any upper bound on simulation time. A running simulation
     * will instead terminate when no future events are scheduled.
     */
    public void clearStopTime() {
        is_bounded = false;
        stop_time = NO_BOUND; // Set to a default value, just in case
    }

    /**
     * Removes any remaining events in the schedule.
     */
    public void  clearSchedule() {
        events.clear();
    }

    /**
     * Removes any remaining events in the schedule, and resets the
     * simulation time back to the starting time.
     */
    public void  reset() {
        current_time = START_TIME;
        clearSchedule();
        clearStopTime();
    }

    /**
     * Adds a new future event to the schedule.
     * @param event a new event (which must have a time later
     *  than the current time).
     * @throws IllegalArgumentException if event delay <= 0.0
     */
    public void newEvent(Event event) {
        if (event.time() > current_time) {
            // The new event should sometime in the future...
            events.add(event);
        } else {
            throw new IllegalArgumentException("The delay of an event must be > 0.0.");
        }

    }
}
