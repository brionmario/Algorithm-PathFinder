import java.awt.*;
import java.util.*;
import java.util.List;

/**
 *
 * Student name - Brion Mario Piumal Silva
 * IIT No - 2015283
 * UOW ID - w1608482
 *
 * @author brionsilva
 * @version 1.0
 * @since 27/03/2017
 *
 */

public class Main {

    //2D array of Nodes
    private static Node[][] nodes;
    //An instance of Node to save the destination node's info
    private static Node destinationNode;
    //The size of the matrix ( one side )
    //private static final int N = 10;

    //colors object for styling
    static Colors colors = new Colors();


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
    public void show(boolean[][] a, boolean which) {
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

                if(N<15)
                    StdDraw.setFont(new Font("TimesRoman", Font.PLAIN , 10));
                else if (N<25)
                    StdDraw.setFont(new Font("TimesRoman", Font.PLAIN , 5));
                else
                    StdDraw.setFont(new Font("TimesRoman", Font.PLAIN , 0));
                StdDraw.text(j, N - i - 1, ("("+ i + "," + j + ")"));

                //System.out.print(" "+ nodes[i][j].isNotBlocked() + " ");
            }
            //System.out.println();
        }

    }

    // draw the N-by-N boolean matrix to standard draw, including the points A (x1, y1) and B (x2,y2) to be marked by a circle
    public void show(boolean[][] a, boolean which, int x1, int y1, int x2, int y2) {
        int N = a.length;
        StdDraw.setXscale(-1, N);
        StdDraw.setYscale(-1, N);


        for (int i = 0; i < N; i++){
            for (int j = 0; j < N; j++) {
                if (a[i][j] == which) {

                    if (i == x1 && j == y1) {
                        StdDraw.setPenColor(StdDraw.GREEN);
                        StdDraw.filledCircle(j, N - i - 1, .5);
                        StdDraw.setPenColor(StdDraw.BLACK);

                    }else if(i == x2 && j == y2){
                        StdDraw.setPenColor(StdDraw.RED);
                        StdDraw.filledCircle(j, N - i - 1, .5);
                        StdDraw.setPenColor(StdDraw.BLACK);
                    }else {

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

        Scanner in = new Scanner(System.in);

        //integer to store the matrix size
        int N = 0;
        //double to store the obstacle ratio
        double ob = 0;
        // boolean[][] open = StdArrayIO.readBoolean2D();

        System.out.println("\t█▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀█");
        System.out.println("\t█░░╦─╦╔╗╦─╔╗╔╗╔╦╗╔╗░░█");
        System.out.println("\t█░░║║║╠─║─║─║║║║║╠─░░█");
        System.out.println("\t█░░╚╩╝╚╝╚╝╚╝╚╝╩─╩╚╝░░█");
        System.out.println("\t█▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄█");
        System.out.println("");
        System.out.println("\t     TO THE " + colors.RED + "A STAR"+colors.RESET+"      ");
        System.out.println(colors.CYAN + "\t P A T H   F I N D E R  "+colors.RESET);

        //getting the grid size from the user
        System.out.println();
        System.out.println("Please enter your preferred grid size");

        try {

            do {

                N = in.nextInt();

            }while (N < 1);

        } catch (Exception e) {

            System.out.println(colors.RED + "\nIncorrect grid size. Please re-enter.\n\n"+colors.RESET);
            new Main().main(args);
        }

        //getting the obstacle ratio from the user
        System.out.println();
        System.out.println("Please enter your preferred obstacle ratio");

        try {

            do {

                ob = in.nextDouble();

            }while (ob <= 0.0);

        } catch (Exception e) {

            System.out.println(colors.RED + "\nIncorrect ratio. Please re-enter.\n\n"+colors.RESET);
            new Main().main(args);
        }




        // The following will generate a NxN squared grid with relatively few obstacles in it
        // The lower the second parameter, the more obstacles (black cells) are generated
        boolean[][] randomlyGenMatrix = random(N,ob);

        //printing the boolean array on the console
        //StdArrayIO.print(randomlyGenMatrix);

        //drawing the matrix
        new Main().show(randomlyGenMatrix, true);

        /*
        System.out.println();
        System.out.println("The system percolates: " + percolates(randomlyGenMatrix));

        System.out.println();
        System.out.println("The system percolates directly: " + percolatesDirect(randomlyGenMatrix));
        System.out.println();
        */

        System.out.println("\nThe start node (A) will be shown in "+colors.GREEN+"GREEN"+colors.RESET+
                " and the end node (B) will be shown in "+colors.RED+"RED"+colors.RESET);

        int Ai = 0 , Aj = 0, Bi =0, Bj = 0  ;

        try{
            do {

                System.out.println("\nEnter i for A (Row number) > ");
                Ai = in.nextInt();

                System.out.println("Enter j for A (Column number) > ");
                Aj = in.nextInt();

                System.out.println("Enter i for B (Row number) > ");
                Bi = in.nextInt();

                System.out.println("Enter j for B (Column number) > ");
                Bj = in.nextInt();



                // System.out.println(colors.RED + "Sorry! You cant have the starting or the end node inside a blocked cell.\n" +
                //       "Please select different co-ordinates" + colors.RESET);

            }while (!nodes[Ai][Aj].isNotBlocked() || !nodes[Bi][Bj].isNotBlocked());

        }catch(java.lang.ArrayIndexOutOfBoundsException e1) {

            System.out.println(colors.RED + "\nWrong co-ordinates. Please enter again !\n\n" + colors.RESET);
            new Main().main(args);
        }catch(InputMismatchException e2) {

            System.out.println(colors.RED + "\nInvalid co-ordinates. Please enter again !\n\n" + colors.RESET);
            new Main().main(args);

        }
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
        //System.out.println("\nGoal Node co-ordinates are ( i - "  + destinationNode.getI() + ") , ( j - " + destinationNode.getJ() + " )");

        //calculate the heuristic values and prints on the console
        //calculateHeuristic(nodes);

        //plots the two circles on the grid
        new Main().show(randomlyGenMatrix, true, Ai, Aj, Bi, Bj);

        int option;
        Stopwatch timerFlow = null;

        do {
            System.out.println("\nPlease select a preferred distance metric.\n");
            System.out.println(colors.YELLOW + "1. Manhattan Distance" + colors.RESET);
            System.out.println(colors.BLUE + "2. Euclidean Distance" + colors.RESET);
            System.out.println(colors.PURPLE + "3. Chebyshev Distance" + colors.RESET);
            System.out.println(colors.RED+"0. To Exit"+colors.RESET);


            String metricType;

            option = in.nextInt();
            switch (option) {
                case 1: {
                    //starts the stopwatch to calculate the time spent to find the shortest path
                    timerFlow = new Stopwatch();

                    StdDraw.clear();
                    new Main().show(randomlyGenMatrix, true);
                    new Main().show(randomlyGenMatrix, true, Ai, Aj, Bi, Bj);

                    metricType = "Manhattan";

                    //stores the list of nodes returned by the find path method
                    List<Node> finalPath = new AStar(N, nodes, metricType).findPath(Ai, Aj, Bi, Bj);

                    System.out.println("\n**************************************************");
                    System.out.println("*                                                *");
                    System.out.println("*         YOU SELECTED"+colors.YELLOW + " MANHATTAN "+colors.RESET+"DISTANCE        *");
                    System.out.println("*                                                *");
                    System.out.println("**************************************************");

                    if(finalPath.size()>0) {
                        System.out.println("\nA Path exists!");
                        System.out.println("\nTotal cost of the shortest path - " + new Main().calcTotalCost(finalPath));
                        System.out.println("\nPath followed - " + Arrays.toString(finalPath.toArray()));
                    }else {
                        System.out.println("\nSorry! A Path doesn't exist!");
                    }
                    StdOut.println("\nAlgorithm running time - " + timerFlow.elapsedTime());


                    //draws the shortest path on the grid
                    new Main().drawLine(N, finalPath, Color.YELLOW);

                    /*new Test(metricType).printH(nodes);
                    new Test(metricType).printG(nodes);
                    new Test(metricType).printF(nodes);*/

                    break;
                }

                case 2: {
                    //starts the stopwatch to calculate the time spent to find the shortest path
                    timerFlow = new Stopwatch();

                    StdDraw.clear();
                    new Main().show(randomlyGenMatrix, true);
                    new Main().show(randomlyGenMatrix, true, Ai, Aj, Bi, Bj);

                    metricType = "Euclidean";

                    //stores the list of nodes returned by the find path method
                    List<Node> finalPath = new AStar(N, nodes, metricType).findPath(Ai, Aj, Bi, Bj);

                    System.out.println("\n**************************************************");
                    System.out.println("*                                                *");
                    System.out.println("*         YOU SELECTED"+colors.BLUE + " EUCLIDEAN"+colors.RESET+" DISTANCE        *");
                    System.out.println("*                                                *");
                    System.out.println("**************************************************");

                    if(finalPath.size()>0) {
                        System.out.println("\nA Path exists! ");
                        System.out.println("\nTotal cost of the shortest path - " + new Main().calcTotalCost(finalPath));
                        System.out.println("\nPath followed - " + Arrays.toString(finalPath.toArray()));
                    }else{
                        System.out.println("\nSorry! A Path doesn't exist!");
                    }
                    StdOut.println("\nAlgorithm running time - " + timerFlow.elapsedTime());

                    //draws the shortest path on the grid
                    new Main().drawLine(N, finalPath, Color.BLUE);

                    /*new Test(metricType).printH(nodes);
                    new Test(metricType).printG(nodes);
                    new Test(metricType).printF(nodes);*/

                    break;
                }

                case 3: {
                    //starts the stopwatch to calculate the time spent to find the shortest path
                    timerFlow = new Stopwatch();

                    StdDraw.clear();
                    new Main().show(randomlyGenMatrix, true);
                    new Main().show(randomlyGenMatrix, true, Ai, Aj, Bi, Bj);

                    metricType = "Chebyshev";

                    //stores the list of nodes returned by the find path method
                    List<Node> finalPath = new AStar(N, nodes, metricType).findPath(Ai, Aj, Bi, Bj);

                    System.out.println("\n**************************************************");
                    System.out.println("*                                                *");
                    System.out.println("*         YOU SELECTED"+colors.PURPLE + " CHEBYSHEV"+colors.RESET+" DISTANCE        *");
                    System.out.println("*                                                *");
                    System.out.println("**************************************************");


                    if(finalPath.size()>0) {
                        System.out.println("\nA Path exists!  ");
                        System.out.println("\nTotal cost of the shortest path - " + new Main().calcTotalCost(finalPath));
                        System.out.println("\nPath followed - " + Arrays.toString(finalPath.toArray()));
                    }else{
                        System.out.println("\nSorry! A Path doesn't exist!");
                    }
                    StdOut.println("\nAlgorithm running time - " + timerFlow.elapsedTime());


                    //draws the shortest path on the grid
                    new Main().drawLine(N, finalPath, Color.MAGENTA);

                    /*new Test(metricType).printH(nodes);
                    new Test(metricType).printG(nodes);
                    new Test(metricType).printF(nodes);*/

                    break;
                }

                case 0: {

                    System.exit(0);

                    break;
                }
            }
        }while (option!=0);




    }

    /**
     * Draws the path on the grid
     * @param N Size of the matrix
     * @param nodes List of nodes that are in the path
     */
    public void drawLine( int N , List<Node> nodes , Color color){

        StdDraw.setXscale(-1,N);
        StdDraw.setYscale(-1,N);

        for(Node node : nodes){

            StdDraw.setPenRadius(0.01);
            StdDraw.setPenColor(color);
            //StdDraw.line( aj , N-ai-1 , bj , N-bi-1 );
            StdDraw.line( node.getParent().getJ() ,N - node.getParent().getI() -1, node.getJ() ,N- node.getI() -1 );
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.show(150);
            StdDraw.setPenRadius();

        }

    }

    public double calcTotalCost(List <Node> path){

        double totalCost = 0.0;

        /*for (Node node : path){

            totalCost = totalCost + node.getG();
            System.out.println(totalCost);
        }*/

        totalCost = path.get(path.size() - 1).getG();
        String.format( "%.2f", totalCost);

        return totalCost;
    }


}

