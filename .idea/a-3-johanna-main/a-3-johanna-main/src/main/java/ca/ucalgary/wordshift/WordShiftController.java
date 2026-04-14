package ca.ucalgary.wordshift;

import javafx.application.*;
import javafx.fxml.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.text.*;

import java.io.*;
import java.util.*;

/**
 * CPSC 219 W26 Assignment 3
 * <p>
 * WordShiftController
 * <p>
 * The Controller end of View/Controller
 * word-shift.xml will be the parallel View definition
 * The Model is the Game
 * This class is responsible for load the dictionary file and list of valid challenges (between 5-10 letter changes)
 *
 * @author Jonathan Hudson
 * @version 1.0
 * @email jwhudson@ucalgary.ca
 */
public class WordShiftController {

    /**
     * The dictionary file, assumed local
     */
    private static final String DICTIONARY_FILENAME = "dictionary.txt";
    /**
     * The challenges file, assumed local
     */
    private static final String CHALLENGES_FILENAME = "challenges.txt";
    /**
     * The dictionary of valid words, loaded when initialized. Global for program.
     */
    protected static HashSet<String> dictionary;
    /**
     * Used to choose next game randomly, seed can be set from arguments
     */
    private static Random random;
    /**
     * Different possible start/end word combinations (loaded when initialized), there is one list per key (word length) 4,5,6,7 stored in this map.
     */
    private final HashMap<Integer, List<Challenge>> challenges;
    /**
     * The Model of the game, holds state
     */
    private Game game;

    /**
     * Result of guess attempt
     */
    @FXML
    private Label guessStatus;
    /**
     * Enter guesses here
     */
    @FXML
    private TextField guess_textfield;
    /**
     * Show state here
     */
    @FXML
    private GridPane wordGrid;
    /**
     * Pick new random game size here
     */
    @FXML
    private TextField gameSize;
    /**
     * Result of game size choice attempt here
     */
    @FXML
    private Label sizeStatus;

    /**
     * Construct a controller by reading in dictionary and challenges
     */
    public WordShiftController() {
        WordShiftController.dictionary = readDictionary();
        challenges = readChallenges();
    }

    /**
     * Read in dictionary from file
     *
     * @return The words in the file
     */
    private HashSet<String> readDictionary() {
        HashSet<String> dictionary = new HashSet<>();
        try (FileReader file_reader = new FileReader(DICTIONARY_FILENAME); BufferedReader reader = new BufferedReader(file_reader)) {
            for (String line : reader.readAllLines()) {
                String word = line.trim().toUpperCase();
                if (Helper.invalidLengthWord(word)) {
                    //System.out.printf("Word %s in dictionary was not a valid length and was skipped!%n", word);
                    continue;
                }
                dictionary.add(word);
            }
        } catch (IOException ioE) {
            System.err.printf("There seems to be an issue with dictionary %s%n", DICTIONARY_FILENAME);
            System.exit(1);
        }
        return dictionary;
    }


    /**
     * Read in challenges from file
     *
     * @return The Map of challenges where 4,5,6,7 store list of challenges with words that length from the file
     */
    private HashMap<Integer, List<Challenge>> readChallenges() {
        HashMap<Integer, List<Challenge>> challenges = new HashMap<>();
        for (int i = 4; i < 8; i++) {
            challenges.put(i, new ArrayList<>());
        }
        try (FileReader file_reader = new FileReader(CHALLENGES_FILENAME); BufferedReader reader = new BufferedReader(file_reader)) {
            for (String line : reader.readAllLines()) {
                String[] parts = line.trim().split(",");
                if (!dictionary.contains(parts[0].toUpperCase())) {
                    //System.out.printf("Word %s was not in dictionary so challenge was skipped!%n", parts[0].toUpperCase());
                    continue;
                }
                if (!dictionary.contains(parts[1].toUpperCase())) {
                    //System.out.printf("Word %s was not in dictionary so challenge was skipped!%n", parts[1].toUpperCase());
                    continue;
                }
                challenges.get(parts[0].length()).add(new Challenge(parts[0].toUpperCase(), parts[1].toUpperCase(), Integer.parseInt(parts[2])));
            }

        } catch (IOException ioE) {
            System.err.printf("There seems to be an issue with challenges %s%n", CHALLENGES_FILENAME);
            System.exit(1);
        }
        return challenges;
    }

    /**
     * Initialize the Controller (pass in arguments to set up first  game)
     *
     * @param parameters The command line arguments
     */
    protected void initialize(Application.Parameters parameters) {
        List<String> params = parameters.getRaw();
        //Two args means words are passed in (check them before beginning)
        if (params.size() == 2) {
            valid(params.get(0), params.get(1));
            game = new Game(params.get(0), params.get(1));
            return;
        }
        //One arg means RNG seed is being set
        else if (params.size() == 1) {
            random = new Random(Integer.parseInt(params.getFirst()));
        }
        //Otherwise RNG seed will be controlled by Java
        else {
            random = new Random();
        }
        //Set a random word length, and then a challenge for the list of challenges (start/end words) at that length
        List<Challenge> challenge_list = challenges.get(4 + random.nextInt(4));
        Challenge challenge = challenge_list.get(random.nextInt(challenge_list.size()));
        //Create game around those words
        game = new Game(challenge.getStartingWord(), challenge.getEndingWord());
        //Setup game does the GUI window setup
        setupGame();
    }

    /**
     * Setup wordGrid
     */
    private void setupGame() {
        //If prior game has happened, we are lazy and dump all prior labels
        wordGrid.getChildren().clear();
        //Setup grid with labels for each letter for each guess (start/end word as well)
        for (int j = 0; j < Game.MAX_GUESSES + 2; j++) {
            //Then for each letter store one label
            for (int i = 0; i < game.wordLength(); i++) {
                Label label = new Label();
                label.setFont(new Font("Arial", 20));
                label.setAlignment(Pos.CENTER);
                label.setMinWidth(30);
                label.setMinHeight(30);
                setupLabel(label, "#FFFFFF", "#787C7F");
                label.setTextFill(Color.BLACK);
                label.setText("");
                GridPane.setHalignment(label, HPos.CENTER);
                wordGrid.add(label, i, j);
            }
        }
        //Now go through and set letter character for the start/end words
        for (int j = 0; j < game.wordLength(); j++) {
            //Start word
            int guess_row = 0;
            Label label = getLabel(guess_row, j);
            String letter = game.getLastWord().charAt(j) + "";
            label.setText(letter.toUpperCase());
            setupLabel(label, "#FFFFFF", "#000000");
            //End word
            int guess_row2 = Game.MAX_GUESSES + 1;
            Label label2 = getLabel(guess_row2, j);
            String letter2 = game.getEndingWord().charAt(j) + "";
            label2.setText(letter2.toUpperCase());
            setupLabel(label2, "#FFFFFF", "#000000");
        }
        guessStatus.setText("Make your first guess!");
    }

    /**
     * Helper function so we can modify labels to have difference background and text colours for desired visualization
     *
     * @param label  The label to edit
     * @param text   The text colour as hex string (#FFFFFF)
     * @param border The border colour as hex string (#FFFFFF)
     */
    private void setupLabel(Label label, String text, String border) {
        label.setBackground(new Background(new BackgroundFill(Color.web(text), CornerRadii.EMPTY, Insets.EMPTY)));
        label.setBorder(new Border(new BorderStroke(Color.web(border), BorderStrokeStyle.SOLID, null, new BorderWidths(1))));
    }

    /**
     * When new game size is picked, parse to integer and if valid create new game of that size, otherwise show error
     */
    @FXML
    public void onPickSize() {
        int size;
        try {
            size = Integer.parseInt(gameSize.getText().trim());
        } catch (NumberFormatException nfe) {
            sizeStatus.setText("Input was not valid integer");
            return;
        }
        if (size < 4) {
            sizeStatus.setText("Input was too small");
            return;
        }
        if (size > 7) {
            sizeStatus.setText("Input was too big");
            return;
        }
        Challenge challenge = challenges.get(size).get(random.nextInt(challenges.get(size).size()));
        game = new Game(challenge.getStartingWord(), challenge.getEndingWord());
        setupGame();
    }

    /**
     * Helper function to get letter labels at row (guess) and column (letter) indices
     *
     * @param rowIndex    Row index (guess)
     * @param columnIndex Column index (letter)
     * @return The label from the gridRow at those locations (holds a single letter or empty)
     */
    private Label getLabel(int rowIndex, int columnIndex) {
        for (Node node : wordGrid.getChildren()) {
            if (node instanceof Label && GridPane.getRowIndex(node) == rowIndex && GridPane.getColumnIndex(node) == columnIndex) {
                return (Label) node;
            }
        }
        return null;
    }

    /**
     * When a guess is made
     */
    @FXML
    public void onGuess() {
        String guess = guess_textfield.getText();
        //Trim whitespace and uppercase so we don't have to worry about case
        guess = guess.trim();
        guess = guess.toUpperCase();
        //Ignore guesses that are the last guess and the wrong length
        //TODO: More helpful user information
        if (game.getLastWord().equals(guess)|| guess.length() != game.wordLength()) {
            guessStatus.setText("Ignored guess!");
            return;
        }
        // game is over popup
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if (game.wasWon()){
            alert.setTitle("You Won!");
            alert.setHeaderText("Congratulations!");
            alert.setContentText("You reached the final word!");
            guessStatus.setText("You Won!");
        } else { // what the player sees if they lost the game
            alert.setTitle("Game Over!");
            alert.setHeaderText("You lost!");
            alert.setContentText("Better luck next time!");
            guessStatus.setText("You lost!");
        }
        alert.showAndWait();

        GuessResult result = game.makeGuess(guess);
        //TODO: More helpful user information
        if (game.wasInvalidGuess()) {
            guessStatus.setText("Invalid guess!");
            return;
        }
        GuessResult lastGuess = game.getLastGuess();
        Differences.Match[] diffs = lastGuess.differences.getDifferences();

        guessStatus.setText("Good guess!");
        guess_textfield.clear();
        for (int j = 0; j < game.wordLength(); j++) {
            int guess_row = game.getGuessCount();
            Label label = getLabel(guess_row, j);
            String letter = guess.charAt(j) + "";
            label.setText(letter.toUpperCase());

            if (diffs[j] == Differences.Match.YES){
                // correct letter
                setupLabel(label, "#6AAA64", "#000000"); // looked up color code in https://htmlcolorcodes.com/
            } else {
                // incorrect letter
                setupLabel(label, "#787C7F", "#000000"); // looked up color code in https://htmlcolorcodes.com/
            }
        }
        //Or if guess is correct indicate that and end game
        if (!game.isGameOver()) {
            guessStatus.setText("Guess again!");
            return;
        }
        guessStatus.setText("Game Over!");
        //TODO: More interesting user information (pop-up) for won/lost
    }

    /**
     * Helper function to check if a user argument start/end word are actually valid
     *
     * @param starting_word The starting word
     * @param ending_word   The ending word
     * @return True if valid words chosen (however unknown if winnable), otherwise false
     */
    private boolean valid(String starting_word, String ending_word) {
        if (Helper.invalidLengthWord(starting_word)) {
            System.err.printf("Invalid starting word %s (not length 4,5,6,7)%n", starting_word);
            System.exit(1);
        }
        if (!Helper.isAlphabetic(starting_word)) {
            System.err.printf("Invalid starting word %s (not alphabetic)%n", starting_word);
            System.exit(1);
        }
        if (!Helper.isDictionaryWord(starting_word)) {
            System.err.printf("Invalid starting word %s (not in dictionary)%n", starting_word);
            System.exit(1);
        }
        if (Helper.invalidLengthWord(ending_word)) {
            System.err.printf("Invalid ending word %s (not length 4,5,6,7)%n", ending_word);
            System.exit(1);
        }
        if (!Helper.isAlphabetic(ending_word)) {
            System.err.printf("Invalid ending word %s (not alphabetic)%n", ending_word);
            System.exit(1);
        }
        if (!Helper.isDictionaryWord(ending_word)) {
            System.err.printf("Invalid ending word %s (not in dictionary)%n", ending_word);
            System.exit(1);
        }
        if (Helper.invalidLengthWord(ending_word, starting_word.length())) {
            System.err.println("Invalid ending word length (not same as starting word)");
            System.exit(1);
        }
        return true;
    }
}
