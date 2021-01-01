import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class BinarySearchTree {
    class Node {
        int element;
        Node left;
        Node right;

        Node (int x) {
            element = x;
        }
    }

    Node root;

    void insert (int x) {
        root = insert (root, x);
    }

    Node insert (Node v, int x) {
        if (v == null) {
            return new Node (x);
        } else if (x <v.element) {
            v.left = insert (v.left, x);
        } else if (x> v.element) {
            v.right = insert (v.right, x);
        }
        return v;
    }

    int minHeight (Node v) {
        if (v == null) {
            return -1;
        }
        return 1 + Math.min (minHeight (v.left), minHeight (v.right));
    }

    int height (Node v) {
        if (v == null) {
            return -1;
        }
        return 1 + Math.max (height (v.left), height (v.right));
    }

    boolean isBalanced () {
        return height (root) - minHeight (root) <= 1;
    }
}

class BalanceChecker {
    public static void main (String [] args) throws IOException {
        BufferedReader br = new BufferedReader (new InputStreamReader (System.in));
        BinarySearchTree tree = new BinarySearchTree ();

        int arr[]={5,8,9,10,6,7,2,3,4,0,1}; //Skriv output
        for (int i=0;i<11;i++) {  // "i" tilsvarer lengden på output. Her må du endre 11 hvis du har en annen output som har en annen lengde.
            tree.insert (arr[i]);
        }


        if (tree.isBalanced ()) {
            System.out.println ("Dette treet ser balansert ut!!");
        } else {
            System.out.println ("Dette treet ser ikke helt balansert ut... prøv igjen!");
        }
    }
}
