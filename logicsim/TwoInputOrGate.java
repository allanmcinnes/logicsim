package logicsim;

/**
 * A 2-input AND gate
 * @author Allan McInnes
 */
public class TwoInputOrGate extends TwoInputGate {

    /**
     * Constructor.
     * @param inA an input wire
     * @param inB an input wire
     * @param out the output wire
     */
    public TwoInputOrGate(Wire inA, Wire inB, Wire out) {
        super(inA, inB, out);
    }

    /**
     * Generates an output signal that is the logical
     *  OR of the inputs.
     * @return the output signal level of the gate, based on the current inputs
     */
    @Override
    protected Signal outputValue() {
        return Signal.or(inA.getSignal(), inB.getSignal());
    }
}
