import java.util.LinkedList;
import java.util.List;

/**
 * Copyright (c) 2017. Aparecium Labs.  http://www.apareciumlabs.com
 *
 * @author brionsilva
 * @version 1.0
 * @since 30/03/2017
 */
public class AStar {

    private int N; //size of the matrix
    private String metric; //stores the distance measurement type
    private Node[][] nodes; //2D array to store the nodes on the grid

    private double hvCost;
    private double diagonalCost;


    public AStar(int N , Node[][] nodes , String metric){
        this.N = N;
        this.nodes = nodes;
        this.metric = metric;

        if(metric.equals("Manhattan")){
            hvCost = 1.0;
            diagonalCost = 2.0;
        }else if(metric.equals("Euclidean")){
            hvCost = 1.0;
            diagonalCost = 1.4;
        }else if(metric.equals("Chebyshev")){
            hvCost = 1.0;
            diagonalCost = 1.0;
        }
    }

    /**
     * This method calculates the shortest path using the starting node and the ending node
     *
     * @param startI Row number of the starting node
     * @param startJ Column number of the starting node
     * @param goalI Row number of the goal node
     * @param goalJ Column number of the goal node
     * @return A list of nodes to follow which gives the shortest distance to the goal
     */
    public final List<Node> findPath(int startI, int startJ, int goalI, int goalJ )
    {
        // If our start position is the same as our goal position returns an empty list
        if (startI == goalI && startJ == goalJ)
        {
            // Return an empty path. No need to move
            return new LinkedList<Node>();
        }

        // The set of nodes already visited.
        List<Node> openList = new LinkedList<Node>();
        // The set of currently discovered nodes still to be visited.
        List<Node> closedList = new LinkedList<Node>();

        // Add starting node to open list.
        openList.add(nodes[startI][startJ]);

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
            if ((current.getI() == goalI) && (current.getJ() == goalJ))
            {
                // Return a LinkedList containing all of the visited nodes.
                return calcPath(nodes[startI][startJ], current);
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
                    adjacent.setH(nodes[goalI][goalJ] , metric);
                    // Set G costs of this node (costs from start to this node).
                    //adjacent.setG(current);
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
                    //adjacent.setG(current);
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
    private List<Node> calcPath(Node start, Node goal)
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
    private Node lowestFInList(List<Node> list)
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
    private List<Node> getAdjacent(Node node, List<Node> closedList )
    {
        List<Node> adjacentNodes = new LinkedList<Node>();
        int i = node.getI();
        int j = node.getJ();

        Node adjacent;

        // Check top nodes
        if (i > 0)
        {
            //Top node
            adjacent = getNode(i - 1, j );
            if (adjacent != null && adjacent.isNotBlocked() && !closedList.contains(adjacent))
            {
                adjacent.setG(node , hvCost);
                adjacentNodes.add(adjacent);
            }

            // Top Left
            if (j - 1 >= 0) {
                adjacent = getNode(i - 1, j - 1 );
                if (adjacent != null && adjacent.isNotBlocked() && !closedList.contains(adjacent))
                {
                    adjacent.setG(node , diagonalCost);
                    adjacentNodes.add(adjacent);
                }
            }

            // Top Right
            if (j + 1 < N) {
                adjacent = getNode(i - 1, j + 1 );
                if (adjacent != null && adjacent.isNotBlocked() && !closedList.contains(adjacent))
                {
                    adjacent.setG(node , diagonalCost);
                    adjacentNodes.add(adjacent);
                }
            }
        }

        // Check bottom nodes
        if (i < N)
        {
            //bottom node
            adjacent = getNode(i + 1, j);
            if (adjacent != null && adjacent.isNotBlocked() && !closedList.contains(adjacent))
            {
                adjacent.setG(node , hvCost);
                adjacentNodes.add(adjacent);
            }

            //bottom left node
            if (j - 1 >= 0) {
                adjacent = getNode(i + 1, j-1);
                if (adjacent != null && adjacent.isNotBlocked() && !closedList.contains(adjacent))
                {
                    adjacent.setG(node , diagonalCost);
                    adjacentNodes.add(adjacent);
                }
            }

            //bottom right node
            if (j + 1 > N) {
                adjacent = getNode(i + 1, j+1);
                if (adjacent != null && adjacent.isNotBlocked() && !closedList.contains(adjacent))
                {
                    adjacent.setG(node , diagonalCost);
                    adjacentNodes.add(adjacent);
                }
            }

        }

        // Check left node
        if (j > 0)
        {

            adjacent = getNode(i, j - 1);
            if (adjacent != null && adjacent.isNotBlocked() && !closedList.contains(adjacent))
            {
                adjacent.setG(node , hvCost);
                adjacentNodes.add(adjacent);
            }


        }

        // Check right node
        if (j < N)
        {
            adjacent = getNode(i, j + 1);
            if (adjacent != null && adjacent.isNotBlocked() && !closedList.contains(adjacent))
            {
                adjacent.setG(node , hvCost);
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

    public Node getNode(int x, int y)
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
