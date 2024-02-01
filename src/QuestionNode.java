/**
 * Represents a node with data (either question or answer) and its left and right children
 */
public class QuestionNode {

    public String data;
    public QuestionNode left;
    public QuestionNode right;

    public QuestionNode() {
        this.data = "";
    }

    public QuestionNode(String data) {
        this.data = data;
    }

}
