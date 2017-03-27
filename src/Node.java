/**
 * Copyright (c) 2017. Aparecium Labs.  http://www.apareciumlabs.com
 *
 * @author brionsilva
 * @version 1.0
 * @since 27/03/2017
 */

public class Node {

    /**
     * cost for all unblocked cells on our path will equal to one (1)
     */
    protected static final int COST = 1;

    /**
     * X position of the node on the grid
     */
    private int x;

    /**
     * Y position of the node on the grid
     */
    private int y;

    /**
     * Boolean to check if the cell is blocked
     */
    private boolean isBlocked;

    /**
     * Used for when getting the resulting path. The node prior to this node.
     */
    private Node parent;

    /**
     * The cost of getting from the first node to this node.
     */
    private int g;

    /**
     * A heuristic that estimates the cost of the cheapest path from this node
     * to the goal.
     */
    private int h;

    /**
     * Creates a simple node.
     *
     * @param x
     *            The node's X position on the map.
     * @param y
     *            The node's Y position on the map.
     * @param isBlocked
     *            If the node is not a wall and can be walked through.
     */
    public Node(int x, int y, boolean isBlocked)
    {
        this.x = x;
        this.y = y;
        this.isBlocked = isBlocked;
    }

    /**
     * Sets the G score based on the parent's G score and the movement cost.
     *
     * @param parent
     *            The node prior to this one.
     */
    public void setG(Node parent)
    {
        g = (parent.getG() + COST);
    }

    /**
     * Calculates and return the G score, without changing it on the class.
     *
     * @param parent
     *            The node prior to this one.
     * @return This node's G score.
     */
    public int calculateG(Node parent)
    {
        return (parent.getG() + COST);
    }

    /**
     * Sets the H score based on the goal's position in three different metrics for calculating the distance
     * between starting node and destination node : Manhattan, Euclidean, Chebyshev.
     *
     * @param destination The destination node
     */
    public void setH(Node destination)
    {
        h = (Math.abs(getX() - destination.getX()) + Math.abs(getY() - destination.getY())) * COST;
        /*
        switch (metric) {
            case "Manhattan": {
                h = (Math.abs(getX() - destination.getX()) + Math.abs(getY() - destination.getY())) * COST;
                break;
            }
            case "Euclidean": {
                h = (int) (Math.sqrt( Math.pow((getX() - destination.getX()) , 2)
                                        - Math.pow((getY() - destination.getY()) , 2)) * COST);
                break;
            }
            case "Chebyshev": {
                h = Math.max ( Math.abs(getX() - destination.getX()) ,
                        Math.abs(getY() - destination.getY())) * COST;
                break;
            }
        }
        */

    }

    /**
     * @return The node's X position on the map.
     */
    public int getX()
    {
        return x;
    }

    /**
     * Sets the node's X position on the map;
     *
     * @param x
     *            The X position to be set.
     */
    public void setX(int x)
    {
        this.x = x;
    }

    /**
     * @return The node's Y position on the map.
     */
    public int getY()
    {
        return y;
    }

    /**
     * Sets the node's Y position on the map;
     *
     * @param y
     *            The Y position to be set.
     */
    public void setY(int y)
    {
        this.y = y;
    }

    /**
     * @return True if the node is not a wall and can be walked through, false
     *         otherwise.
     */
    public boolean isBlocked()
    {
        return isBlocked;
    }

    /**
     * Sets if the node is not a wall and can be walked through.
     *
     * @param blocked
     *            Can you walk through this node?
     */
    public void setBlocked(boolean blocked)
    {
        this.isBlocked = blocked;
    }

    /**
     * @return The node prior to this node.
     */
    public Node getParent()
    {
        return parent;
    }

    /**
     * Sets the node prior to this one.
     *
     * @param parent
     *            The node to be set as this node's parent.
     */
    public void setParent(Node parent)
    {
        this.parent = parent;
    }

    /**
     * @return The G score + the H score.
     */
    public int getF()
    {
        return g + h;
    }

    /**
     * @return The cost of getting from the first node to this node.
     */
    public int getG()
    {
        return g;
    }

    /**
     * @return A heuristic that estimates the cost of the cheapest path from
     *         this node to the goal.
     */
    public int getH()
    {
        return h;
    }

    @Override
    public boolean equals(Object o)
    {
        if (o == null)
            return false;
        if (!(o instanceof Node))
            return false;
        if (o == this)
            return true;

        Node n = (Node) o;
        if (n.getX() == x && n.getY() == y && n.isBlocked() == isBlocked)
            return true;
        return false;
    }

}
