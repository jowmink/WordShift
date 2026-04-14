package ca.ucalgary.wordshift;

/**
 * CPSC 219 W26 Assignment 3
 * <p>
 * GuessResult
 * <p>
 * GuessResult between two words for WordShift game (The Model interior)
 * GuessResult has 4 booleans, only one or none can be true at a time
 * None -> guess moved game forward but wasn't the ending word
 * One -> Either guess was correct, or guess was not valid (not alphabetic, not one different, not in dictionary)
 *
 * @author Jonathan Hudson
 * @version 1.0
 * @email jwhudson@ucalgary.ca
 */
public class GuessResult {

    enum GuessStatus {
        GOOD_GUESS,
        CORRECT,
        NOT_ALPHABETIC,
        NOT_IN_DICTIONARY,
        NOT_ONE_DIFFERENT
    }
    GuessStatus status;
    Differences differences;

    public GuessResult(GuessStatus status, Differences differences) {
        this.status = status;
        this.differences = differences;
    }
    //TODO: Deep copy Constructor
    public GuessResult (GuessResult other) {
        this.status = other.status;
        this.differences = new Differences(other.differences.getDifferences());
    }
}
