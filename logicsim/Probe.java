package logicsim;

// TODO: Could define a Probe interface, and make this a TextProbe implementation

/**
 * A probe is a sink component that outputs signal information. This version of
 * the probe produces text console output.
 * @author Allan McInnes
 */
public class Probe extends Component {
    private Wire in;  // The wire that is being probed

    /**
     * Constructor.
     * @param in the wire being probed
     */
    public Probe(Wire in) {
        this.in = in;
        in.attachDrivenComponent(this);
    }

    /**
     * Update the probe display
     */
    @Override
    public void update() {
        System.out.printf("[%4f] %s\t%s\n", model.time(), in.name(), in.getSignal());
    }

}
