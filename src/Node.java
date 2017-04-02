/**
 * Copyright (c) 2017. Aparecium Labs.  http://www.apareciumlabs.com
 *
 * @author brionsilva
 * @version 1.0
 * @since 27/03/2017
 */

public class Node {

    /**
     * row number of the node on the grid
     */
    private int i;

    /**
     * column number of the node on the grid
     */
    private int j;

    /**
     * Boolean to check if the node is blocked or not
     */
    private boolean notBlocked;

    /**
     * The parent node to the current node (used to find the path)
     */
    private Node parent;

    /**
     * The cost of getting from the previous node to current node.
     */
    private double g;

    /**
     * Heuristic that calculates the cost of the cheapest path from current node
     * to the goal node.
     */
    private double h;

    /**
     * cost for all unblocked nodes on our path will equal to one ( Spec instructs to assume it as one)
     */
    protected static final int COST = 1;

    /**
     * Constructor to build a node
     *
     * @param i Node's row number on the grid
     * @param j Node's column number on the grid
     * @param notBlocked Says if the node is blocked or not. ( true = not blocked , false = blocked)
     */
    public Node(int i, int j, boolean notBlocked)
    {
        this.i = i;
        this.j = j;
        this.notBlocked = notBlocked;
    }

    /**
     * Sets the G value based on the parent node's G value and the movement cost.
     *
     * @param parent The previous node to this node
     */
    public void setG(Node parent)
    {
        g = (parent.getG() + COST);
    }

    /**
     * Calculates and returns the G value
     *
     * @param parent The previous node to this node
     * @return This node's G value
     */
    public double calculateGValue(Node parent)
    {
        return (parent.getG() + COST);
    }

    /**
     * Sets the Heuristic based on the goal's position in three different metrics for calculating the distance
     * between starting node and destination node : Manhattan, Euclidean, Chebyshev.
     *
     * @param destination The destination node
     */
    public void setH(Node destination , String metric)
    {
        switch (metric) {
            case "Manhattan": {
                h = (Math.abs(getI() - destination.getI()) + Math.abs(getJ() - destination.getJ())) * COST;
                break;
            }
            case "Euclidean": {
                h =  (Math.sqrt( Math.pow((getI() - destination.getI()) , 2)
                        - Math.pow((getJ() - destination.getJ()) , 2)) * COST);
                break;
            }
            case "Chebyshev": {
                h = Math.max ( Math.abs(getI() - destination.getI()) ,
                        Math.abs(getJ() - destination.getJ())) * COST;
                break;
            }
        }

    }

    /**
     * @return The node's row number on the grid
     */
    public int getI()
    {
        return i;
    }

    /**
     * Sets the node's row number on the grid
     *
     * @param i row number of the node to be set
     */
    public void setI(int i)
    {
        this.i = i;
    }

    /**
     * @return The node's column number on the grid
     */
    public int getJ()
    {
        return j;
    }

    /**
     * Sets the node's column number on the grid
     *
     * @param j  column number of the node to be set
     */
    public void setJ(int j)
    {
        this.j = j;
    }

    /**
     * @return True if the node is not blocked and can be walked through, false if the node is blocked
     * and can't be walked
     */
    public boolean isNotBlocked()
    {
        return notBlocked;
    }

    /**
     * Sets if the node is not blocked and can be walked through
     *
     * @param notBlocked is this node blocked or not
     */
    public void setNotBlocked(boolean notBlocked)
    {
        this.notBlocked = notBlocked;
    }

    /**
     * @return The parent node to this node ( previous node )
     */
    public Node getParent()
    {
        return parent;
    }

    /**
     * Sets the node prior to this one.
     *
     * @param parent The node to be set as this node's parent
     */
    public void setParent(Node parent)
    {
        this.parent = parent;
    }

    /**
     * @return The F value. ( i.e G value + the H value )
     */
    public double getF()
    {
        return g + h;
    }

    /**
     * @return The cost of getting from the parent node to this node.
     */
    public double getG()
    {
        return g;
    }

    /**
     * @return A heuristic that estimates the cost of the cheapest path from
     *         this node to the goal node
     */
    public double getH()
    {
        return h;
    }

    /**
     * Overrides the default equals method .  To check if the current node is the same as the goal node.
     *
     * @param o The goal node
     * @return True if the node passed is same as the current node
     */
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
        if (n.getI() == i && n.getJ() == j && n.isNotBlocked() == notBlocked)
            return true;
        return false;
    }

    @Override
    public String toString() {
        return "Node (" +
                "i=" + i +
                ", j=" + j +
                //", notBlocked=" + notBlocked +
               //", g=" + g +
               // ", h=" + h +
                //", parent=" + parent +
                ')';
    }
}