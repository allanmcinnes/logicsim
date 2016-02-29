package logicsim;

/**
 * A set of symbols for representing signal states.
 * @author Allan McInnes
 */
public enum Signal {
    LOW,  // Logic low
    HIGH, // Logic high
    X;    // Don't know or don't care

    /**
     * Checks if a signal is a valid logic level.
     * @return true if the signal is 'low' or 'high'
     */
    public boolean isValid() {
        return (this == HIGH || this == LOW);
    }

    /**
     * AND a pair of signals
     * @return Logical AND of two signals, or X if the result
     * cannot be determined.
     */
    public static Signal and(Signal a, Signal b) {
        if (a == HIGH && b == HIGH) {
            return HIGH;
        }
        if (a.isValid() && b.isValid()) {
            return LOW;
        }
        return X;
    }

    /**
     * OR a pair of signals
     * @return Logical OR of two signals, or X if the result
     * cannot be determined.
     */
    public static Signal or(Signal a, Signal b) {
        if (a == LOW && b == LOW) {
            return LOW;
        }
        if (a.isValid() && b.isValid()) {
            return HIGH;
        }
        return X;
    }

    /**
     * Signal negation.
     * @return Logical NOT of a signal, or X if the result
     * cannot be determined.
     */
    public static Signal not(Signal s) {
        switch (s) {
            case LOW: return HIGH;
            case HIGH: return LOW;
            default: return X;
        }
    }
}
