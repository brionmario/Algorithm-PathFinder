import java.util.LinkedList;
import java.util.List;

/**
 * Copyright (c) 2017. Aparecium Labs.  http://www.apareciumlabs.com
 *
 * @author brionsilva
 * @version 1.0
 * @since 27/03/2017
 */
public class Map {

    /**
     * Creates a map based on a two dimensional array, where each zero is a
     * walkable node and any other number is not.
     *
     * @param map
     *            The map array used to creating the map.
     */
    int count = 1;
    int [][] num;

    public Map(boolean[][] map)
    {
        num = new int[10][10];

        for (int j = 0; j < map.length; j++)
        {
            for (int i = 0; i < map.length; i++)
            {
                //printing the values on the console
                if(count<10){
                    System.out.print("0" + count++ + " ");
                }else{
                    System.out.print(count++ + " ");
                }

                //assigning the node number to the boxes
               // map[i][j] = num[i][j];

            }
            System.out.print("\n");
        }
    }

}
