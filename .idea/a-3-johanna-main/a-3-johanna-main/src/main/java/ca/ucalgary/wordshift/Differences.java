package ca.ucalgary.wordshift;

/**
 * CPSC 219 W26 Assignment 3
 * <p>
 * Differences
 * <p>
 * Differences between two words for WordShift game (The Model interior)
 * Takes two words and creates a boolean array indicating which letters were the same or different
 * True means different, false the same
 *
 * @author Jonathan Hudson
 * @version 1.0
 * @email jwhudson@ucalgary.ca
 */
public class Differences {
    /**
     * Array of booleans that holds the differences between words provided to constructor
     */
    protected enum Match { YES, NO}

    private Match[] differences;

    /**
     * Constructor differences boolean
     *
     * @param target The word to compare to actual
     * @param actual The word to compare to target
     */
    public Differences(String target, String actual) {
        // TODO: Replace with enum Match instead of boolean
        differences = new Match[target.length()];
        for (int i = 0; i < differences.length; i++) {
            if (target.charAt(i) != actual.charAt(i)) {
                differences[i] = Match.NO;
            } else {
                differences[i] = Match.YES;
            }
        }
    }
    public Differences(Differences.Match[] differences) {
        this.differences = new Differences.Match[differences.length];
        for (int i = 0; i < differences.length; i++) {
            this.differences[i] = differences[i];
        }
    }
    /**
     * Array of booleans that holds the differences
     *
     * @return Array of booleans that holds the differences
     */
    public Match[] getDifferences() {
        return differences;
    }

}
