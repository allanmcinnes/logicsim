package logicsim;

/**
 * A logic inverter.
 * @author Allan McInnes
 */
public class Inverter extends Component {
    private Wire in;
    private Wire out;

    private static final double GATE_DELAY = 2.0; // Propagation delay in nanoseconds

    // Creation
    /**
     * Constructor.
     * @param in the input wire
     * @param out the output wire
     */
    public Inverter(Wire in, Wire out) {        
        this.in = in;
        this.out = out;
        
        // Set up driving and driven wires
        this.in.attachDrivenComponent(this);
        this.out.setDrivingComponent(this);
    }

    /**
     * Updates the component's state based on the new inputs that have
     * resulted from a update in one or more input signals.
     */
    @Override
    public void update() {
        model.newEvent(new Event(model.time() + GATE_DELAY,
                                 out, Signal.not(in.getSignal())));
    }
}
