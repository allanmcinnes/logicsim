package logicsim;

/**
 * Implements a half-adder model.
 * @author Allan McInnes
 */
class HalfAdder extends Component {
    private Block halfAdderImpl = new Block(); // How we'll implement
    private Wire internalA = new Wire(); // Internal wiring
    private Wire internalB = new Wire(); // Internal wiring

    /**
     * Constructor.
     * @param in1 an input wire
     * @param in2 an input wire
     * @param sum the output wire that will contain the sum of the inputs
     * @param carry the output wire that will contain the carry output
     */
    public HalfAdder(Wire in1, Wire in2, Wire sum, Wire carry) {
        // Wire up all of the components to each other.
        halfAdderImpl.setSimulationModel(this);
        halfAdderImpl.addComponent(new TwoInputAndGate(in1, in2, carry));
        halfAdderImpl.addComponent(new TwoInputAndGate(internalA, internalB, sum));
        halfAdderImpl.addComponent(new TwoInputOrGate(in1, in2, internalA));
        halfAdderImpl.addComponent(new Inverter(carry, internalB));
    }

    /**
     * Update component state.
     */
    @Override
    public void update() {
        halfAdderImpl.update();
    }
}
