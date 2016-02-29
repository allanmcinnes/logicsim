package logicsim;

/**
 * TwoInputGate is an abstract base class for two input logic gates.
 * Derived classes must implement the <code>output()</code> method to specify
 * the operation performed by the gate.
 * @author Allan McInnes
 */
public abstract class TwoInputGate extends Component {
    protected Wire inA;  // One input
    protected Wire inB;  // The other input
    protected Wire out;  // Output

    protected static final double GATE_DELAY = 5.0; // Propagation delay in nanoseconds

    // Creation
    /**
     * Constructor.
     * @param inA an input wire
     * @param inB an input wire
     * @param out the output wire
     */
    public TwoInputGate(Wire inA, Wire inB, Wire out) {
        // Currently assumes that components are always constructed with
        // connections to wires. This works fine, but would be problematic
        // for dynamic circuit editing in (for example) a schematic editor.
        assert inA != null && inB != null && out != null;
        
        this.inA = inA;
        this.inB = inB;
        this.out = out;

        // Set up driving and driven wires
        this.inA.attachDrivenComponent(this);
        this.inB.attachDrivenComponent(this);
        this.out.setDrivingComponent(this);
    }

    /**
     * Queries the current gate output.
     * @return the output signal level of the gate, based on the current inputs
     */
    protected abstract Signal outputValue();

    // Commands
    /**
     * Updates the component's state based on the new inputs that have
     * resulted from a transition in one or more input signals.
     */
    @Override
    public void update() {
        model.newEvent(new Event(model.time() + GATE_DELAY,
                                 out, outputValue()));
    }
}
