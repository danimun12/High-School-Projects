// Name: Alice Rau
// Lab: 8-Puzzle Part 1
// Enjoy!

import java.util.*;

public class EightPuzzle 
{
    public static void main(String[] args) 
    {
        Scanner input = new Scanner(System.in);
        //Input of puzzle
        System.out.println("Enter length of one side of puzzle:");
        byte l = input.nextByte();
        
        System.out.println("Enter current puzzle state. Seperate numbers with spaces2.");
        byte[] curr = new byte[l*l];
        
        for (byte i = 0; i < l*l; i++) 
        {
            curr[i] = input.nextByte();
        }
        System.out.println("What is the goal state of the puzzle? ");
        byte[] g = new byte[l*l];
        
        for (byte i = 0; i < l*l; i++)
        {
            g[i] = input.nextByte();
        }   
        
        Populate tree = new Populate(l,g);
        
        Node temp = findNode(tree.populated, curr);
        
        if (temp != null) //if proposed puzzle start condition exists somewhere in tree
            System.out.println(findPath(temp));
        else
            System.out.println("There is no solution for this puzzle.");

    }


    public static Node findNode(Node start, byte[] goal) {
        Queue<Node> queue = new LinkedList<>();
        int counter = 0;
        queue.add(start);
        while (!queue.isEmpty()) {
            Node current = queue.remove();
            if (current.equals(new Node(goal))) //if search is complete
                return current;
            queue.addAll(current.children);
        }
        return null;
    }

    public static String findPath(Node start) {
        String result = "";
        Node current = start;
        int counter = 0;
        while (current.parent != null) { //only searching valid puzzle states 
            result += Populate.printPuzzle(current.node) + "\n";
            current = current.parent;
            if (current.parent == null)
                result += Populate.printPuzzle(current.node) + "\n";
        }
        return result;
    }

   
}



class Populate {
    public static byte[] DEFAULT;
    public Node populated;
    public static byte n = 0;
    
    public Populate(byte n, byte[] goal) {
        Populate.n = n;
        Populate.DEFAULT = goal;
        this.populated = makeTotalTree();
    }
    
    public static Node makeTotalTree() {
            int counter = 0;
            Node current = new Node(DEFAULT);
            HashSet<String> explored = new HashSet<>();
            Queue<Node> currentDepth = new LinkedList<>();
            currentDepth.add(current);
            Node result = null;
            while (!currentDepth.isEmpty()) {
                current = currentDepth.remove();
                explored.add(current.toString());
                ArrayList<Node> moves = possibleMoves(current);
                ArrayList<Node> removes = new ArrayList<>();
                for (Node n: moves) {
                    if (!explored.add(n.toString()))
                        removes.add(n);
                }
                moves.removeAll(removes);
                current.children = moves;
                if (current.equals(new Node(DEFAULT)))
                    result = current;
                currentDepth.addAll(moves);
                
                counter++;
        }
        System.out.println(counter);
        return result;
    }

    private static byte indexOf(byte[] node, byte look) {
        byte index = 0;
        for (byte b : node) {
            if (b == look)
                return index;
            index++;
        }
        return -1;
    }

    public static ArrayList<Node> possibleMoves(Node no) { //populates tree by determining possible board states from current state
        byte[] node = no.node;
        byte i = indexOf(node, (byte)0);
        byte up = (byte) (i - n);
        byte down = (byte) (i + n);
        byte left = (byte) (i - 1);
        byte right = (byte) (i + 1);
        if (i == 0) //formulas for determining position of bytes
            return corner(no, i, right, down);
        else if (i == (n - (byte) 1))
            return corner(no, i, left, down);
        else if (i == n*n - n)
            return corner(no, i, up, right);
        else if (i == (n*n - (byte) 1))
            return corner(no, i, left, up);
        else if (i / n == 0)
            return sides(no, i, left, right, down);
        else if (i % n == 0)
            return sides(no, i, up, down, right);
        else if ((i + (byte) 1) % n == 0)
            return sides(no, i,up, down, left);
        else if ((i + n) > n*n)
            return sides(no, i, up, left, right);
        else
            return middle(no, i, up, down, left, right);
    }
    private static Node switchArr(Node no, byte loc1, byte loc2) {
        byte[] node = no.node;
        byte[] tempArr = node.clone();
        byte temp = tempArr[loc1];
        tempArr[loc1] = tempArr[loc2];
        tempArr[loc2] = temp;
        return new Node(tempArr, no);
    }

    private static ArrayList<Node> corner(Node no, byte org, byte new1, byte new2) {
        ArrayList<Node> res = new ArrayList<>();
        res.add(switchArr(no, org, new1));
        res.add(switchArr(no, org, new2));
        return res;
    }

    private static ArrayList<Node> sides(Node no, byte org, byte new1, byte new2, byte new3) {
        ArrayList<Node> res = new ArrayList<>();
        res.add(switchArr(no, org, new1));
        res.add(switchArr(no, org, new2));
        res.add(switchArr(no, org, new3));
        return res;
    }

    private static ArrayList<Node> middle(Node no, byte org, byte new1, byte new2, byte new3, byte new4) {
        ArrayList<Node> res = new ArrayList<>();
        res.add(switchArr(no, org, new1));
        res.add(switchArr(no, org, new2));
        res.add(switchArr(no, org, new3));
        res.add(switchArr(no, org, new4));
        return res;
    }

    public static String printPuzzle(byte[] b) {
        String result = "";
        for (byte k = 0; k < n; k++) {
            result += ("| ");
            for (byte i = 0; i < n; i++) {
                result += b[i+k*n] + " ";
            }
            result += "|\n";
        }
        return result;
    }

}

class NodeComparator implements Comparator<Node> {

    @Override
    public int compare(Node n1, Node n2) {
        for (int i = 0; i < n1.node.length; i++) {
            if (n1.node[i] != n2.node[i])
                return -1;
        }
        return 0;
    }
}


class Node {
    Node parent;
    byte[] node;
    ArrayList<Node> children;
    int depth;

    public Node(byte[] curr) {
        this.node  = curr;
        this.children = new ArrayList<>();
        depth = 0;

    }
    public Node(byte[] curr, Node par) {
        this.node  = curr;
        this.parent = par;
        this.children = new ArrayList<>();
        depth = 0;
    }

    

    @Override
    public boolean equals(Object n2) {
        for (byte i = 0; i < this.node.length; i++) {
            if (!(this.node[i] == ((Node) n2).node[i]))
                return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        String result = "";
        for (byte b : node) {
            result += b;
        }
        return Integer.parseInt(result);
    }
    

    @Override
    public String toString() { //prints board as 2D array
        String result = "";
        for (byte b : this.node)
            result += b;
        return result;
    }
}