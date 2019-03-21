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


    public static void addHexagon(TETile[][] world, int xPos, int yPos, int size){
        int PosScaler = 0;
        //int xPosScaler = 0;
        for(int i = size; i > 0; i--){
            for(int j = 0; j < size + 2*(i-1); j++){
                world[xPos + PosScaler + j][yPos+PosScaler] = Tileset.WALL;
                world[xPos + PosScaler + j][yPos-PosScaler - 1] = Tileset.WALL;
            }
            PosScaler++;
            //xPosScaler++;
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

        //world[30][30] = Tileset.MOUNTAIN;
        addHexagon(world, 15, 15, 5);
        addHexagon(world, 25, 25, 3);
        addHexagon(world, 40, 40, 2);

        ter.renderFrame(world);
    }



}
