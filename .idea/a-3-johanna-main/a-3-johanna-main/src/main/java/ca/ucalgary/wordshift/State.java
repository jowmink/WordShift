package ca.ucalgary.wordshift;

import java.util.ArrayList;
import java.util.List;

/**
 * CPSC 219 W26 Assignment 3
 * <p>
 * State
 * <p>
 * State for WordShift game (The Model interior)
 *
 * @author Jonathan Hudson, Johanna Potestas
 * @version 1.0
 * @email jwhudson@ucalgary.ca, Johanna Potestas
 */
public class State {

    //TODO: Visbility/Accessibility
    /**
     * The starting word
     */
    String starting_word;
    /**
     * The ending word
     */
    String ending_word;
    /**
     * The history of guesses
     */
    List<GuessResult> guessHistory;
    /**
     * The last word
     */
    String last_word;
    /**
     * The last guess_result (should also be last in history if it was a correct/good guess)
     */
    GuessResult lastGuessState;

    /**
     * Construct state storing the start/end words
     * The last word will be set to starting_word and the last guess will be considered to have been valid (uncounted of course)
     *
     * @param starting_word The starting word
     * @param ending_word   the ending word
     */
    public State(String starting_word, String ending_word) {
        this.starting_word = starting_word;
        this.ending_word = ending_word;
        this.last_word = this.starting_word;
        this.guessHistory = new ArrayList<>();
        this.lastGuessState = new GuessResult(
                GuessResult.GuessStatus.GOOD_GUESS,
                new Differences(this.starting_word, this.ending_word)
        );
    }

    /**
     * Are there remaining guesses
     *
     * @param MAX The maximum limit of guesses
     * @return If the history has reached or exceed the maximum, then no guesses remain (true), false otherwise
     */
    public boolean outOfGuesses(int MAX) {
        return guessHistory.size() >= MAX;
    }

    /**
     * Is the game last word the ending word
     *
     * @return
     */
    public boolean checkWin() {
        return lastGuessState.status == GuessResult.GuessStatus.CORRECT;
    }

    /**
     * What are length of words in this state of game
     *
     * @return The length of words (start/end/last) word will all be the same
     */
    public int wordLength() {
        return starting_word.length();
    }

    /**
     * Is this guess word of valid length
     *
     * @param guess The word that was guessed
     * @return True if same length as start/end/last word, false otherwise
     */
    public boolean wrongLength(String guess) {
        return guess.length() != wordLength();
    }

    /**
     * Is this the last word guessed
     *
     * @param guess The word to check
     * @return True if same as last word, false otherwise
     */
    public boolean isLastWord(String guess) {
        return guess.equals(last_word);
    }

    /**
     * Make a guess, create and store a GuessResult before returning it
     *
     * @param guess The guessed word
     * @return The result of guess
     */
    public GuessResult guess(String guess) {
        GuessResult guess_result;
        Differences differences = new Differences(ending_word, guess);
        if (guess.equals(ending_word)) {
            last_word = guess;
            guess_result = new GuessResult(GuessResult.GuessStatus.CORRECT, differences);
            guessHistory.add(guess_result);
        } else if (!Helper.isDictionaryWord(guess)) {
            guess_result = new GuessResult(GuessResult.GuessStatus.NOT_IN_DICTIONARY, differences);
        } else if (!Helper.isOneDifferent(guess, last_word)) {
            guess_result = new GuessResult(GuessResult.GuessStatus.NOT_ONE_DIFFERENT, differences);
        } else if (!Helper.isAlphabetic(guess)) {
            guess_result = new GuessResult(GuessResult.GuessStatus.NOT_ALPHABETIC, differences);
        } else {
            last_word = guess;
            guess_result = new GuessResult(GuessResult.GuessStatus.GOOD_GUESS, differences);
            guessHistory.add(guess_result);
        }
        lastGuessState = guess_result;
        return guess_result;
    }
    public int getGuessCount() {
        return guessHistory.size();
    }
}
