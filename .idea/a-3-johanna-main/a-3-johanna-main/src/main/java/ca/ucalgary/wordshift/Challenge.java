package ca.ucalgary.wordshift;

/**
 * CPSC 219 W26 Assignment 3
 * <p>
 * Challenge
 * <p>
 * Start/End word combinations for WordShift game (The Model interior)
 * NO CHANGES NEEDED
 *
 * @author Jonathan Hudson, Johanna Potestas
 * @version 1.0
 * @email jwhudson@ucalgary.ca, johanna.potestas@ucalgary.ca
 */
public class Challenge {
    /**
     * The starting word
     */
    private final String starting_word;
    /**
     * The ending word
     */
    private final String ending_word;
    /**
     * How many shifts at minimum are expected to change start to end
     */
    private final int length;

    /**
     * Construct a Challenge
     *
     * @param ending_word   The starting word
     * @param starting_word The ending word
     * @param length        How many shifts at minimum are expected to change start to end
     */
    public Challenge(String ending_word, String starting_word, int length) {
        this.length = length;
        this.ending_word = ending_word;
        this.starting_word = starting_word;
    }

    /**
     * The starting word
     *
     * @return The starting word
     */
    public String getStartingWord() {
        return starting_word;
    }

    /**
     * The ending word
     *
     * @return The ending word
     */
    public String getEndingWord() {
        return ending_word;
    }

    /**
     * How many shifts at minimum are expected to change start to end
     *
     * @return How many shifts at minimum are expected to change start to end
     */
    public int getLength() {
        return length;
    }
}
