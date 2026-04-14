package ca.ucalgary.wordshift;

/**
 * CPSC 219 W26 Assignment 3
 * <p>
 * Helper
 * <p>
 * Static functions for WordShift game (The Model interior)
 * NO CHANGES NEEDED
 *
 * @author Jonathan Hudson
 * @version 1.0
 * @email jwhudson@ucalgary.ca
 */
public class Helper {

    /**
     * Is a word of valid length [4,7]
     *
     * @param word The word to check
     * @return True if length in range [4,7], false otherwise
     */
    protected static boolean invalidLengthWord(String word) {
        if (word == null) {
            throw new IllegalArgumentException("Word was null");
        }
        return word.length() < Game.MIN_WORD_LENGTH || word.length() > Game.MAX_WORD_LENGTH;
    }

    /**
     * Is a word of valid length compared to size given
     *
     * @param word The word to check
     * @param size The size to compare to
     * @return True if size matched required size, false otherwise
     */
    protected static boolean invalidLengthWord(String word, int size) {
        return word.length() != size;
    }

    /**
     * Is a word in the dictionary
     *
     * @param word The word to check
     * @return True if in dictionary, false otherwise
     */
    protected static boolean isDictionaryWord(String word) {
        return WordShiftController.dictionary.contains(word);
    }

    /**
     * Is a guess word only one letter different than other
     *
     * @param word  The word to check
     * @param other The other word to check
     * @return True if only on letter different, false otherwise
     */
    protected static boolean isOneDifferent(String word, String other) {
        if (word.length() != other.length()) {
            return false;
        }
        boolean diff = false;
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) != other.charAt(i)) {
                if (diff) {
                    return false;
                }
                diff = true;
            }
        }
        return diff;
    }

    /**
     * Is a word alphabetic
     *
     * @param word The word to check
     * @return True if word is made of only alphabetic characters, false otherwise
     */
    protected static boolean isAlphabetic(String word) {
        for (int i = 0; i < word.length(); i++) {
            if (!Character.isAlphabetic(word.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
