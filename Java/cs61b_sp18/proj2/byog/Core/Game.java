package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdOut;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;

    private static final int SEED = 1111345; //1111345 is cool
    public static final Random RANDOM = new Random(SEED);
    private static final int roomsCount = generateRandomCountOfRooms();

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        // TODO: Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().

        TETile[][] finalWorldFrame = null;
        return finalWorldFrame;
    }

    /**
     * Generates number of rooms between hardcoded 5 and 20.
     * @return
     */
    private static int generateRandomCountOfRooms(){
        int roomsCount = 0;

        while(roomsCount < 5){
            roomsCount = RANDOM.nextInt(20);
        }
        return roomsCount;
    }

    /**
     * Generates a list of random XYCoords that will be the starting points for rooms.
     * @return
     */
    public ArrayList<XYCoords> generateRandomCoordinates(){
        ArrayList<XYCoords> coords = new ArrayList<>(roomsCount);

        for(int i = 0; i < roomsCount; i+=1) {
            XYCoords xy = new XYCoords(RANDOM.nextInt(WIDTH - 1),  RANDOM.nextInt(HEIGHT - 1));
             if(xy.xPos == 0){xy.xPos += 1;}
             if(xy.yPos == 0){xy.yPos += 1;}

            coords.add(xy);
        }
        return coords;
    }

    public static void main(String[] args){
        Game game = new Game();
        game.ter.initialize(WIDTH, HEIGHT);
        ArrayList<XYCoords> coords = game.generateRandomCoordinates();


        //Fill grid with !null
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        //TODO
        ArrayList<Room> rooms = new ArrayList<>();

        for(int i = 0; i < roomsCount; i += 1){
            Room newRoom = new Room(coords.get(i));
            newRoom.roomExpandRandomizer();
            //System.out.println(newRoom.bottomLeftCorner.xPos + ", " + newRoom.bottomLeftCorner.yPos);

            newRoom.drawRoomFloor(world);
            newRoom.drawRoomWalls(world);

            world[newRoom.center.xPos][newRoom.center.yPos] = Tileset.FLOWER;
            rooms.add(newRoom);
        }

        rooms.sort(Room.xPosComparator);

        for(int i = 0; i < rooms.size() - 1; i ++){
            rooms.get(i).drawPath(world, rooms.get(i+1));
        }

        for(int i = 0; i < rooms.size(); i ++){
            world[rooms.get(i).center.xPos][rooms.get(i).center.yPos] = Tileset.FLOWER;
        }


        game.ter.renderFrame(world);
        //System.out.println("ti si moe lubimo heksagonche");
    }
}
