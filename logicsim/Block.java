package logicsim;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A Block is a Composite Component.
 *
 * @author Allan McInnes
 */
public class Block extends Component {

    private Collection<Component> comps = new ArrayList<Component>();

    /**
     * Default constructor
     */
    public Block() {
        super();
    }

    /**
     * Constructor
     * @param model the parent model of this component
     */
    public Block(SimulationModel model) {
        super(model);
    }

    /**
     * Add a component to the Block.
     * @param comp the component to add
     */
    @Override
    public void addComponent(Component comp) {
        comp.setSimulationModel(this);
        comps.add(comp);
    }

    /**
     * Remove a component from the Blocks.
     * @param comp the component to remove.
     */
    @Override
    public void removeComponent(Component comp) {
        comps.remove(comp);
    }

    /**
     * Define the parent model to be used when simulating the component.
     * @param model the parent model
     */
    @Override
    public void setSimulationModel(SimulationModel model) {
        super.setSimulationModel(model);
        for (Component comp : comps) {
            comp.setSimulationModel(this);
        }
    }

    /**
     * Updates the block's state based on the new inputs that have
     * resulted from a update in one or more input signals.
     */
    @Override
    public void update() {
        // Events act directly on individual components, so no need
        // to do anything here.
    }
}
