import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * Copyright (c) 2017. Aparecium Labs.  http://www.apareciumlabs.com
 *
 * @author brionsilva
 * @version 1.0
 * @since 27/03/2017
 */

public class Main {

    //2D array of Nodes
    private static Node[][] nodes;
    //An instance of Node to save the destination node's info
    private static Node destinationNode;
    //The size of the matrix ( one side )
    private static final int N = 10;


    // given an N-by-N matrix of open cells, return an N-by-N matrix
    // of cells reachable from the top
    public static boolean[][] flow(boolean[][] open) {
        int N = open.length;

        boolean[][] full = new boolean[N][N];
        for (int j = 0; j < N; j++) {
            flow(open, full, 0, j);
        }

        return full;
    }

    // determine set of open/blocked cells using depth first search
    public static void flow(boolean[][] open, boolean[][] full, int i, int j) {
        int N = open.length;

        // base cases
        if (i < 0 || i >= N) return;    // invalid row
        if (j < 0 || j >= N) return;    // invalid column
        if (!open[i][j]) return;        // not an open cell
        if (full[i][j]) return;         // already marked as open

        full[i][j] = true;

        flow(open, full, i + 1, j);   // down
        flow(open, full, i, j + 1);   // right
        flow(open, full, i, j - 1);   // left
        flow(open, full, i - 1, j);   // up
    }

    // does the system percolate?
    public static boolean percolates(boolean[][] open) {
        int N = open.length;

        boolean[][] full = flow(open);
        for (int j = 0; j < N; j++) {
            if (full[N - 1][j]) return true;
        }

        return false;
    }

    // does the system percolate vertically in a direct way?
    public static boolean percolatesDirect(boolean[][] open) {
        int N = open.length;

        boolean[][] full = flow(open);
        int directPerc = 0;
        for (int j = 0; j < N; j++) {
            if (full[N - 1][j]) {
                // StdOut.println("Hello");
                directPerc = 1;
                int rowabove = N - 2;
                for (int i = rowabove; i >= 0; i--) {
                    if (full[i][j]) {
                        // StdOut.println("i: " + i + " j: " + j + " " + full[i][j]);
                        directPerc++;
                    } else break;
                }
            }
        }

        // StdOut.println("Direct Percolation is: " + directPerc);
        if (directPerc == N) return true;
        else return false;
    }

    // draw the N-by-N boolean matrix to standard draw
    public static void show(boolean[][] a, boolean which) {
        int N = a.length;
        int boxCounter = 1;
        nodes = new Node[N][N];

        StdDraw.setXscale(-1, N);
        StdDraw.setYscale(-1, N);
        StdDraw.setPenColor(StdDraw.BLACK);


        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {

                //if which is true (i.e Boolean value is 1) create a white box
                if (a[i][j] == which) {

                    StdDraw.square(j, N - i - 1, .5);
                    nodes[i][j] = new Node(i,j, true );

                //else (i.e Boolean value is 0) create a black box
                } else {

                    StdDraw.filledSquare(j, N - i- 1, .5);
                    nodes[i][j] = new Node(i,j, false );
                }
                StdDraw.setPenColor(StdDraw.BLACK);
                StdDraw.text(j, N - i - 1, ("("+ i + "," + j + ")"));

                //System.out.print(" "+ nodes[i][j].isNotBlocked() + " ");
            }
            //System.out.println();
        }

    }

    // draw the N-by-N boolean matrix to standard draw, including the points A (x1, y1) and B (x2,y2) to be marked by a circle
    public static void show(boolean[][] a, boolean which, int x1, int y1, int x2, int y2) {
        int N = a.length;
        StdDraw.setXscale(-1, N);
        StdDraw.setYscale(-1, N);
        StdDraw.setPenColor(StdDraw.BLACK);

        for (int i = 0; i < N; i++){
            for (int j = 0; j < N; j++) {
                if (a[i][j] == which) {

                    if ((i == x1 && j == y1) || (i == x2 && j == y2)) {

                        StdDraw.circle(j, N - i - 1, .5);

                    } else {

                        StdDraw.square(j, N - i - 1, .5);
                    }
                } else {

                    StdDraw.filledSquare(j, N - i - 1, .5);

                }
            }
        }
    }

    // return a random N-by-N boolean matrix, where each entry is
    // true with probability p
    public static boolean[][] random(int N, double p) {
        boolean[][] a = new boolean[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                a[i][j] = StdRandom.bernoulli(p);
        return a;
    }


    // test client
    public static void main(String[] args) {
        //integer to store the matrix size
        int N = 10;
        // boolean[][] open = StdArrayIO.readBoolean2D();

        // The following will generate a NxN squared grid with relatively few obstacles in it
        // The lower the second parameter, the more obstacles (black cells) are generated
        boolean[][] randomlyGenMatrix = random(N,0.8);

        //printing the boolean array on the console
        StdArrayIO.print(randomlyGenMatrix);

        //drawing the matrix
        show(randomlyGenMatrix, true);

        System.out.println();
        System.out.println("The system percolates: " + percolates(randomlyGenMatrix));

        System.out.println();
        System.out.println("The system percolates directly: " + percolatesDirect(randomlyGenMatrix));
        System.out.println();


        Scanner in = new Scanner(System.in);
        System.out.println("Enter i for A (Row number) > ");
        int Ai = in.nextInt();

        System.out.println("Enter j for A (Column number) > ");
        int Aj = in.nextInt();

        System.out.println("Enter i for B (Row number) > ");
        int Bi = in.nextInt();

        System.out.println("Enter j for B (Column number) > ");
        int Bj = in.nextInt();


        //System.out.println("Coordinates for A: [" + Ai + "," + Aj + "]");
        //System.out.println("Coordinates for B: [" + Bi + "," + Bj + "]");


        // Checking the node co-ordinates
        //System.out.println("\n Node Co-Ordinates \n " );
        for(Node[] node : nodes){
            for(int i =0;i<nodes.length;i++) {

                //System.out.print(" " + node[i].getI() + " , " + node[i].getJ() + " | ");

                if(node[i].getI() == Bi && node[i].getJ()==Bj){
                    //setting the goal node
                    destinationNode = node[i];
                }
            }
            //System.out.println("");
        }


        //printing out the destination node co-odinates
        System.out.println("\n Goal Node co-ordinates are ( i - "  + destinationNode.getI() + ") , ( j - " + destinationNode.getJ() + " )");

        //calculate the heuristic values
        calcHeuristic(nodes);

        show(randomlyGenMatrix, true, Ai, Aj, Bi, Bj);


        Stopwatch timerFlow = new Stopwatch();
        List<Node> node = findPath(Ai, Aj, Bi, Bj);
        StdOut.println("Elapsed time = " + timerFlow.elapsedTime());
        System.out.println(Arrays.toString(node.toArray()));

        drawLine(Ai, Aj, Bi, Bj , N , node);


    }

    /**
     * @param ai X co-ordinate of the first circle
     * @param aj Y co-ordinate of the first circle
     * @param bi X co-ordinate of the second circle
     * @param bj Y co-ordinate of the second circle
     * @param N Size of the matrix
     */
    public static void drawLine(int ai , int aj , int bi , int bj , int N , List<Node> nodes){

        StdDraw.setXscale(-1,N);
        StdDraw.setYscale(-1,N);

        for(Node node : nodes){

            StdDraw.setPenRadius(0.01);
            StdDraw.setPenColor(StdDraw.RED);
            //StdDraw.line( aj , N-ai-1 , bj , N-bi-1 );
            StdDraw.line( node.getParent().getJ() ,N - node.getParent().getI() -1, node.getJ() ,N- node.getI() -1 );
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.show(150);

        }

    }

    /**
     * This method calculates the heuristic values according to the destination node
     * @param nodes The Array of nodes are passed here
     */
    public static void calcHeuristic(Node[][] nodes){

        System.out.println("\n Node H vales according to Manhattan distance \n " );
        for(Node[] node : nodes){
            for(int i =0;i<nodes.length;i++) {

                if (node[i].isNotBlocked() == true) {
                    node[i].setH(destinationNode, "Manhattan");
                    int h = node[i].getH();

                    if (h < 10)
                        System.out.print(" 0" + node[i].getH() + " ");
                    else
                        System.out.print(" " + node[i].getH() + " ");
                }else {
                    System.out.print(" " + "--" + " ");
                }
            }
            System.out.println("");
        }
    }

    /**
     * Tries to calculate a path from the start and end positions.
     *
     * @param startX
     *            The X coordinate of the start position.
     * @param startY
     *            The Y coordinate of the start position.
     * @param goalX
     *            The X coordinate of the goal position.
     * @param goalY
     *            The Y coordinate of the goal position.
     * @return A list containing all of the visited nodes if there is a
     *         solution, an empty list otherwise.
     */
    public static final List<Node> findPath(int startX, int startY, int goalX, int goalY)
    {
        // If our start position is the same as our goal position ...
        if (startX == goalX && startY == goalY)
        {
            // Return an empty path, because we don't need to move at all.
            return new LinkedList<Node>();
        }

        // The set of nodes already visited.
        List<Node> openList = new LinkedList<Node>();
        // The set of currently discovered nodes still to be visited.
        List<Node> closedList = new LinkedList<Node>();

        // Add starting node to open list.
        openList.add(nodes[startX][startY]);

        // This loop will be broken as soon as the current node position is
        // equal to the goal position.
        while (true)
        {
            // Gets node with the lowest F score from open list.
            Node current = lowestFInList(openList);
            // Remove current node from open list.
            openList.remove(current);
            // Add current node to closed list.
            closedList.add(current);

            // If the current node position is equal to the goal position ...
            if ((current.getI() == goalX) && (current.getJ() == goalY))
            {
                // Return a LinkedList containing all of the visited nodes.
                return calcPath(nodes[startX][startY], current);
            }

            List<Node> adjacentNodes = getAdjacent(current, closedList);
            for (Node adjacent : adjacentNodes)
            {
                // If node is not in the open list ...
                if (!openList.contains(adjacent))
                {
                    // Set current node as parent for this node.
                    adjacent.setParent(current);
                    // Set H costs of this node (estimated costs to goal).
                    adjacent.setH(nodes[goalX][goalY] , "Manhattan");
                    // Set G costs of this node (costs from start to this node).
                    adjacent.setG(current);
                    // Add node to openList.
                    openList.add(adjacent);
                }
                // Else if the node is in the open list and the G score from
                // current node is cheaper than previous costs ...
                else if (adjacent.getG() > adjacent.calculateGValue(current))
                {
                    // Set current node as parent for this node.
                    adjacent.setParent(current);
                    // Set G costs of this node (costs from start to this node).
                    adjacent.setG(current);
                }
            }

            // If no path exists ...
            if (openList.isEmpty())
            {
                // Return an empty list.
                return new LinkedList<Node>();
            }
            // But if it does, continue the loop.
        }
    }

    /**
     * @param start
     *            The first node on the path.
     * @param goal
     *            The last node on the path.
     * @return a list containing all of the visited nodes, from the goal to the
     *         start.
     */
    private static List<Node> calcPath(Node start, Node goal)
    {
        LinkedList<Node> path = new LinkedList<Node>();

        Node node = goal;
        boolean done = false;
        while (!done)
        {
            path.addFirst(node);
            node = node.getParent();
            if (node.equals(start))
            {
                done = true;
            }
        }
        return path;
    }

    /**
     * @param list
     *            The list to be checked.
     * @return The node with the lowest F score in the list.
     */
    private static Node lowestFInList(List<Node> list)
    {
        Node cheapest = list.get(0);
        for (int i = 0; i < list.size(); i++)
        {
            if (list.get(i).getF() < cheapest.getF())
            {
                cheapest = list.get(i);
            }
        }
        return cheapest;
    }

    /**
     * @param node
     *            The node to be checked for adjacent nodes.
     * @param closedList
     *            A list containing all of the nodes already visited.
     * @return A LinkedList with nodes adjacent to the given node if those
     *         exist, are walkable and are not already in the closed list.
     */
    private static List<Node> getAdjacent(Node node, List<Node> closedList)
    {
        List<Node> adjacentNodes = new LinkedList<Node>();
        int x = node.getI();
        int y = node.getJ();

        Node adjacent;

        // Check left node
        if (x > 0)
        {
            adjacent = getNode(x - 1, y);
            if (adjacent != null && adjacent.isNotBlocked() && !closedList.contains(adjacent))
            {
                adjacentNodes.add(adjacent);
            }
        }

        // Check right node
        if (x < N)
        {
            adjacent = getNode(x + 1, y);
            if (adjacent != null && adjacent.isNotBlocked() && !closedList.contains(adjacent))
            {
                adjacentNodes.add(adjacent);
            }
        }

        // Check top node
        if (y > 0)
        {
            adjacent = getNode(x, y - 1);
            if (adjacent != null && adjacent.isNotBlocked() && !closedList.contains(adjacent))
            {
                adjacentNodes.add(adjacent);
            }
        }

        // Check bottom node
        if (y < N)
        {
            adjacent = getNode(x, y + 1);
            if (adjacent != null && adjacent.isNotBlocked() && !closedList.contains(adjacent))
            {
                adjacentNodes.add(adjacent);
            }
        }
        return adjacentNodes;
    }

    /**
     * If the X and Y parameters are within the map boundaries, return the node
     * in the specific coordinates, null otherwise.
     *
     * @param x
     *            Desired node's X coordinate.
     * @param y
     *            Desired node's Y coordinate.
     * @return The desired node if the parameters are valid, null otherwise.
     */
    public static Node getNode(int x, int y)
    {
        if (x >= 0 && x < N && y >= 0 && y < N)
        {
            return nodes[x][y];
        }
        else
        {
            return null;
        }
    }

}

