
public class bstValidator {
  static class Node { int val; Node left; Node right; }
  public static boolean isValidBST(Node root) {
    return isValidBST(root, Integer.MIN_VALUE, Integer.MAX_VALUE);    
  }

  public static boolean isValidBST(Node p, int min, int max){
    if(p==null) 
        return true;

    if(p.val <= min || p.val >= max)
        return false;

    return isValidBST(p.left, min, p.val) && isValidBST(p.right, p.val, max);
  }
}
