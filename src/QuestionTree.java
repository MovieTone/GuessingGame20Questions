import java.io.PrintStream;
import java.util.Scanner;

/**
 * QuestionTree class is used to play the game, ask questions, write and read the tree to/from a file
 */
public class QuestionTree {

    private QuestionNode root;
    private final Scanner scanner;

    private static final String FIRST_WORD = "computer";
    private static final String GUESS_QUESTION = "Would your object happen to be ";
    private static final String WIN = "Great, I got it right!";
    private static final String WHAT_WORD = "What is the name of your object?";
    private static final String WHAT_QUESTION = """
        Please give me a yes/no question that\r
        distinguishes between your object\r
        and mine-->\s""";
    private static final String WHAT_ANSWER = "And what is the answer for your object?";
    private static final String ONLY_YES_NO = "Please answer y or n.";
    private static final String YES_NO_CLUE = " (y/n)? ";

    public QuestionTree() {
        scanner = new Scanner(System.in);
        root = new QuestionNode(FIRST_WORD);
    }

    /**
     * Invoked when the client intends to substitute the current tree by reading another one from a file.
     *
     * @param input - a Scanner that is connected to the file to substitute the current tree with a new tree by incorporating the information present
     *              in the file.
     */
    public void read(Scanner input) {
        if (input == null) {
            throw new IllegalArgumentException();
        }
        read(root, input);
    }

    /**
     * Invoked when the client desires to save the current tree to an output file.
     *
     * @param output - provided PrintStream, which will be accessible for writing
     */
    public void write(PrintStream output) {
        if (output == null) {
            throw new IllegalArgumentException();
        }
        write(root, output);
    }

    /**
     * Engages the user in a succession of yes/no questions until it accurately identifies their object or until the attempt concludes in failure
     */
    public void askQuestion() {
        QuestionNode answer = getData(root);
        String data = answer.data;
        if (promptForYesOrNo(GUESS_QUESTION + data + "?")) {
            System.out.println(WIN);
        } else {
            System.out.print(WHAT_WORD);
            String newData = scanner.nextLine();
            System.out.print(WHAT_QUESTION);
            answer.data = scanner.nextLine();
            if (promptForYesOrNo(WHAT_ANSWER)) {
                answer.left = new QuestionNode(newData);
                answer.right = new QuestionNode(data);
            } else {
                answer.left = new QuestionNode(data);
                answer.right = new QuestionNode(newData);
            }
        }
    }

    /**
     * returns true if the answer was yes, returns false otherwise
     *
     * @param prompt - question asked
     * @return - true if yes, false otherwise
     */
    public boolean promptForYesOrNo(String prompt) {
        System.out.print(prompt + YES_NO_CLUE);
        String response = scanner.nextLine().trim().toLowerCase();
        while (!response.equals("y") && !response.equals("n")) {
            System.out.println(ONLY_YES_NO);
            System.out.print(prompt + YES_NO_CLUE);
            response = scanner.nextLine().trim().toLowerCase();
        }

        return response.equals("y");
    }

    /**
     * reads a question tree from a file in the preorder traversal
     *
     * @param node  - current node
     * @param input - file as a Scanner object
     */
    private void read(QuestionNode node, Scanner input) {
        String line = input.nextLine();
        if (line == null) {
            return;
        }
        node.data = input.nextLine();
        if (line.startsWith("A")) {
            return;
        }
        node.left = new QuestionNode();
        node.right = new QuestionNode();
        read(node.left, input);
        read(node.right, input);
    }

    /**
     * writes a question tree in the preorder traversal
     *
     * @param node   - current node
     * @param output - file as a PrintStream object
     */
    private void write(QuestionNode node, PrintStream output) {
        if (node == null) {
            return;
        }
        if (node.left == null && node.right == null) {
            output.println("A:");
            output.println(node.data);
        } else {
            output.println("Q:");
            output.println(node.data);
            write(node.left, output);
            write(node.right, output);
        }
    }

    /**
     * seeks for the answer or the next question
     *
     * @param node - get data at this node
     * @return the next question or the answer if node is a leaf
     */
    private QuestionNode getData(QuestionNode node) {
        if (node.left == null && node.right == null) {
            return node;
        }
        if (promptForYesOrNo(node.data)) {
            return getData(node.left);
        }
        return getData(node.right);
    }

}