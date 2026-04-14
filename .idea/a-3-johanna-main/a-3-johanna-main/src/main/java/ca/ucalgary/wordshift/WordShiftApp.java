package ca.ucalgary.wordshift;

import javafx.application.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.stage.*;

import java.io.*;

/**
 * CPSC 219 W26 Assignment 3
 * <p>
 * WordShiftApp
 * <p>
 * Checks arguments and loads/shows View/Controller
 * Parameters passed into Controller to set up Model
 *
 * @author Jonathan Hudson
 * @version 1.0
 * @email jwhudson@ucalgary.ca
 */
public class WordShiftApp extends Application {
    static void main(String[] args) {
        launch(args);
    }

    /**
     * When launched this is what happens
     *
     * @param stage the primary stage for this application, onto which
     *              the application scene can be set.
     *              Applications may create other stages, if needed, but they will not be
     *              primary stages.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(WordShiftApp.class.getResource("word-shift.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 480, 600);
        if (checkArguments()) {
            WordShiftController controller = fxmlLoader.getController();
            controller.initialize(getParameters());
            stage.setTitle("Word Shift Game!");
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * Arguments are Strings length [4,7] <starting_word> <ending_word>
     * A single argument "help" gives more info before exit
     * A single argument of integer type set RNG seed
     * No arguments runs default game with unknown RNG seed
     * Other argument amounts print quick message before exiting
     *
     * @return Either a default game with no arguments, or a game with the given arguments as words (words have not been validated yet)
     */
    private boolean checkArguments() {
        Parameters parameters = getParameters();
        if (parameters.getRaw().isEmpty()) {
            System.out.println("Random puzzle will be chosen!");
            return true;
        } else if (parameters.getRaw().size() == 2) {
            System.out.println("Puzzle being loaded from program arguments!");
            return true;
        } else if (parameters.getRaw().size() == 1 && parameters.getRaw().getFirst().equals("help")) {
            System.out.println("Word shift game.");
            System.out.println("Users change one letter each guess to change a starting word into an ending word.");
            System.err.println("Usage: java WordShift\nUsage: java WordShift help\nUsage: java WordShift <seed>\nUsage: java WordShift <starting_word> <ending_word>");
            System.out.println("If arguments are not provided a default starting and ending word are used");
            return false;
        } else if (parameters.getRaw().size() == 1) {
            try {
                Integer.parseInt(parameters.getRaw().getFirst());
            } catch (NumberFormatException nfe) {
                System.err.println("Cannot parse RNG seed from single first argument!");
                System.err.println("Usage: java WordShift\nUsage: java WordShift help\nUsage: java WordShift <seed>\nUsage: java WordShift <starting_word> <ending_word>");
                return false;
            }
            return true;
        } else {
            System.err.println("Cannot parse arguments!");
            System.err.println("Usage: java WordShift\nUsage: java WordShift help\nUsage: java WordShift <seed>\nUsage: java WordShift <starting_word> <ending_word>");
            return false;
        }
    }
}
