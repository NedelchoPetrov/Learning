package byog.lab5;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final int WIDTH = 50;
    private static final int HEIGHT = 50;

    private static final long SEED = 287359;
    //287357 is problematic seed??? Ah yes... the initial coordinates are out of
    private static final Random RANDOM = new Random(SEED);

    /**
     *Draws a Hexagon of the given size. The x and y positions should be of the most left, most top tile
     * of the longest two rows in the middle. The Hexagon is drawn from left to right.
     */
    public static TETile chooseRandomTile(){
        int tileNum = RANDOM.nextInt(5);
        switch(tileNum){
            case 0: return Tileset.WALL;
            case 1: return Tileset.FLOWER;
            case 2: return Tileset.GRASS;
            case 3: return Tileset.SAND;
            case 4: return Tileset.WATER;
            default: return Tileset.NOTHING;
        }
    }

    public static void addHexagon(TETile[][] world, int xPos, int yPos, int size){
        TETile tile = chooseRandomTile();
        int PosScaler = 0;
        //int xPosScaler = 0;
        for(int i = size; i > 0; i--){
            for(int j = 0; j < size + 2*(i-1); j++){
                world[xPos + PosScaler + j][yPos+PosScaler] = tile;
                world[xPos + PosScaler + j][yPos-PosScaler - 1] = tile;
            }
            PosScaler++;
            //xPosScaler++;
        }
    }

    public static void spreadHexagons(TETile[][] world, int xPos, int yPos, int size){
        if(xPos > 0 && yPos > 0 && xPos + 3*size - 3 < WIDTH
                && yPos + size < HEIGHT && yPos - size > 0 && world[xPos][yPos] == Tileset.NOTHING &&  world[xPos + 3*size - 3][yPos] == Tileset.NOTHING){
            addHexagon(world, xPos, yPos, size);

            spreadHexagons(world, xPos, yPos + 2*size, size);                   //up
            spreadHexagons(world, xPos + 2*size -1, yPos + size, size);    //up right
            spreadHexagons(world, xPos + 2*size -1, yPos - size, size);     //down right
            spreadHexagons(world, xPos, yPos - 2*size, size);                   //down
            spreadHexagons(world, xPos - 2*size +1, yPos - size, size);     //down left
            spreadHexagons(world, xPos - 2*size +1, yPos + size, size);     //up left
        }else{
            return;
        }
    }


    public static void main(String args[]){
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] world = new TETile[WIDTH] [HEIGHT];

        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        spreadHexagons(world, RANDOM.nextInt(WIDTH - 15), RANDOM.nextInt(HEIGHT - 10), 5);

        //int randomXPos = RANDOM.nextInt(WIDTH);
        //int randomYPos = RANDOM.nextInt(HEIGHT);

        //addHexagon(world, randomXPos, randomXPos, 5);

        //world[30][30] = Tileset.MOUNTAIN;
        //addHexagon(world, 15, 15, 5);
        //addHexagon(world, 25, 25, 3);
        //addHexagon(world, 40, 40, 2);

        ter.renderFrame(world);
    }



}
