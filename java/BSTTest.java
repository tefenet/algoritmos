import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

import static org.junit.Assert.*;


public class BSTTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;


    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }
    @Test
    public void testSomething() throws IOException {
        List<Integer> args= List.of(1, 2, 4, 5, 8, 7);
        assertFalse(bstValidator.isValidBST(treeRoot(args)));
        args= List.of(4,2,5,1,3,4,6);
        assertFalse(bstValidator.isValidBST(treeRoot(args)));
        args= List.of(30,25,40,22,27,31,42,10,24,26,28,-10,-10,-10,50,7,15,-10,-10,-10,-10,-10,-10,-10,-10,-10,-10,-10,-10,-10,51);
        assertTrue(bstValidator.isValidBST(treeRoot(args)));

    }

    //receive a List of Values of the nodes of a binary Tree in Breadth First order
    //creates the tree recursively and returns the root Node
    //if the tree is incomplete, the places without nodes are represented by -10
    private static bstValidator.Node array2Tree(List<Integer> data, bstValidator.Node node, int index){
        if(index >= data.size() || data.get(index) == -10){
            return null;
        }
        node.val=data.get(index);
        node.left = array2Tree(data,new bstValidator.Node(),2*index+1);
        node.right = array2Tree(data,new bstValidator.Node(),2*(index+1));
        return node;
    }

    public bstValidator.Node treeRoot(List<Integer> values) {
        return array2Tree(values, new bstValidator.Node(), 0);
    }
}
