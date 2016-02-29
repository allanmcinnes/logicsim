package logicsim;
/**
 * A simple example of a discrete-event digital logic simulator.
 * The only components included at the moment are a TwoInputAndGate,
 * TwoInputOrGate, Inverter, HalfAdder, and a Probe for viewing
 * signal traces as text. The main program is a demo that puts
 * a HalfAdder through its paces, and runs a 3-Inverter ring
 * oscillator for 100 ns.
 *
 * @author Allan McInnes
 */

import java.util.Collection;
import java.util.ArrayList;

public class LogicSim {

    /**
     * Execute some example simulations.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Scheduler sim = Scheduler.getScheduler(); // The simulation kernel

        // Example 1: a simple half-adder
        // Half-adder inputs and outputs
        Wire in1 = new Wire("In1");
        Wire in2 = new Wire("In2");
        Wire sum = new Wire("Sum");
        Wire carry = new Wire("Carry");

        // The half-adder, plus probes on its inputs and outputs is all wrapped
        // up in a Block that manages the overall simulation.
        Block adderSimulation = new Block(sim);
        adderSimulation.addComponent(new LoggingDecorator(
                                        new HalfAdder(in1, in2, sum, carry)));
        adderSimulation.addComponent(new Probe(sum));
        adderSimulation.addComponent(new Probe(carry));
        adderSimulation.addComponent(new Probe(in1));
        adderSimulation.addComponent(new Probe(in2));

        // Toggle the outputs, and see what happens
        System.out.println(" time  signal \tlevel");
        System.out.println("------------------------------");
        in1.setSignal(Signal.LOW);
        in2.setSignal(Signal.LOW);
        sim.run();
        in2.setSignal(Signal.HIGH);
        sim.run();
        in1.setSignal(Signal.HIGH);
        sim.run();
        in2.setSignal(Signal.LOW);
        sim.run();

        System.out.println("------------------------------");
        sim.reset(); // Clear out the schedule

        // Note that at this point the half-adder is still associated with the
        // scheduler, but won't produce any output events because we won't
        // drive any more input transitions. 

        // Example 2: A simple 3-inverter ring oscillator
        Wire osc0 = new Wire();
        Wire osc1 = new Wire();
        Wire osc2 = new Wire("Osc");

        // Again, we assemble the simulation as a Block
        Block ringOscillator = new Block(sim);
        ringOscillator.addComponent(new Inverter(osc0, osc1));
        ringOscillator.addComponent(new Inverter(osc1, osc2));
        ringOscillator.addComponent(new Inverter(osc2, osc0));
        ringOscillator.addComponent(new Probe(osc2));

        // Note that the nature of the feedback loop in the oscillator is such
        // that it will produce an infinite stream of events. So in this case we
        // have to explicitly set an upper bound on the simulation running time.
        sim.setStopTime(100.0);
        osc0.setSignal(Signal.LOW); // The initial event that kicks things off
        sim.run();
    }

}
