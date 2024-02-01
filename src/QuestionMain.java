import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * This application engages the user in a question-guessing game, offering the option to read a pre-existing tree from a file. After each session, it
 * consistently saves the resulting tree to a file, facilitating future use by the user.
 */
public class QuestionMain {

    private static final String QUESTION_DATA = "data/data.txt";

    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("Welcome to the object guessing name");
        System.out.println();

        QuestionTree questionTree = new QuestionTree();
        if (questionTree.promptForYesOrNo("Would you like to load the previously stored tree?")) {
            questionTree.read(new Scanner(new File(QUESTION_DATA)));
        }
        System.out.println();

        do {
            System.out.println("Kindly consider an object for me to make a guess.");
            questionTree.askQuestion();
            System.out.println();
        } while (questionTree.promptForYesOrNo("Would you like to continue?"));

        questionTree.write(new PrintStream(QUESTION_DATA));
    }
}
