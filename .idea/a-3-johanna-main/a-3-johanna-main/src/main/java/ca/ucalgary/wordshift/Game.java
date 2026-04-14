package ca.ucalgary.wordshift;

import java.util.List;

/**
 * CPSC 219 W26 Assignment 3
 * <p>
 * Game
 * <p>
 * Game state for WordShift game (The Model)
 *
 * @author Jonathan Hudson, Johanna Potestas
 * @version 1.0
 * @email jwhudson@ucalgary.ca, johanna.potestas@ucalgary.ca
 */
public class Game {

    //TODO: Visbility/Accessibility
    /**
     * Smallest allowed words
     */
    static int MIN_WORD_LENGTH = 4;
    /**
     * Largest allowed words
     */
    static int MAX_WORD_LENGTH = 7;
    /**
     * Maximum amount of guesses in a game
     */
    static int MAX_GUESSES = 10;
    /**
     * The state of the current game
     */
    private State state;

    /**
     * Construct game
     *
     * @param starting_word Start words of game
     * @param ending_word   End word of game
     */
    public Game(String starting_word, String ending_word) {
        state = new State(starting_word, ending_word);
    }

    /**
     * Make a guess
     *
     * @param guess The next word guessed (should be right length and alphabetic)
     * @return The result of guess
     */
    public GuessResult makeGuess(String guess) {
        return state.guess(guess.toUpperCase());
    }

    /**
     * Was the last guess invalid -> not alphabetic or not in dictionary or not one different
     *
     * @return
     */
    public boolean wasInvalidGuess() {
        GuessResult.GuessStatus status = getLastGuess().status;

        return status == GuessResult.GuessStatus.NOT_ALPHABETIC ||
        status == GuessResult.GuessStatus.NOT_IN_DICTIONARY || status == GuessResult.GuessStatus.NOT_ONE_DIFFERENT;
    }
    /**
     * Was the last guess valid -> alphabetic and in dictionary and one different
     *
     * @return
     */
    public boolean wasValidGuess() {
        return !wasInvalidGuess();
    }

    /**
     * The game is over if won, or maximum guesses was reached
     *
     * @return If the game is over
     */
    public boolean isGameOver() {
        return state.outOfGuesses(MAX_GUESSES) || state.checkWin();
    }

    /**
     * The game is won if the last word is the ending_word (this should be known in state)
     *
     * @return If the game was won
     */
    public boolean wasWon() {
        return state.checkWin();
    }

    /**
     * The amount of guesses made so far
     *
     * @return The amount of gusses made so far
     */
    public int getGuessCount() {
        return state.getGuessCount();
    }

    /**
     * What is the word length of words in this game
     *
     * @return Word length of word in this game
     */
    public int wordLength() {
        return state.wordLength();
    }
    public GuessResult getLastGuess() {
        return new GuessResult(state.lastGuessState);
    }

    public String getLastWord(){
        return state.last_word;
    }
    public String getEndingWord(){
        return state.ending_word;
    }

}
