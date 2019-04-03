package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;
import edu.princeton.cs.introcs.StdOut;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.AreaAveragingScaleFilter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;

    private static final int SEED = 11113456; //1111345, 11113457 are cool
    public static final Random RANDOM = new Random(SEED);
    private static final int roomsCount = generateRandomCountOfRooms();
    private Player player = new Player();





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
        TETile[][] world  = setupGame();
        ArrayList<XYCoords> roomCoords = generateRandomCoordinates();
        ArrayList<Room> rooms = generateRandomRooms(roomCoords);

        rooms.sort(Room.xPosComparator);

        for(int i = 0; i < rooms.size(); i ++){
            rooms.get(i).drawRoomFloor(world);
            rooms.get(i).drawRoomWalls(world);
        }

        for(int i = 0; i < rooms.size()-1; i ++){
            rooms.get(i).drawPath(world, rooms.get(i+1));
        }

        //Put FLOWER on each room center
        for(int i = 0; i < rooms.size(); i ++){
            world[rooms.get(i).center.xPos][rooms.get(i).center.yPos] = Tileset.FLOWER;
        }

        placeGoldenDoor(world, rooms);
        placePlayer(world, rooms);



        TETile[][] finalWorldFrame = world;
        return finalWorldFrame;
    }

    /**
     * Method for displaying mouseover infos.
     */
    public void showMousePointerInfo(String string){
        Font font = new Font("Monaco", Font.BOLD, 14);
        StdDraw.setFont(font);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.textLeft(3, HEIGHT + 2, "You are looking at " + string);
        StdDraw.show();
    }

    //
    public void followMouse(TETile[][] world){
        int xPos = (int)Math.floor(StdDraw.mouseX());
        int yPos = (int)Math.floor(StdDraw.mouseY());
        if(xPos < this.WIDTH && yPos < this.HEIGHT){
            String tile = world[xPos][yPos].description();
            StdDraw.clear();
            this.ter.renderFrame(world);
            this.showMousePointerInfo(tile);
        }
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

    /**
     * Takes a list with room centers and generates rooms of random sizes aorund them.
     * @param coords
     * @return
     */
    public ArrayList<Room> generateRandomRooms(ArrayList<XYCoords> coords){
        ArrayList<Room> rooms = new ArrayList<>();

        for(int i = 0; i < roomsCount; i += 1){
            Room newRoom = new Room(coords.get(i));
            newRoom.roomExpandRandomizer();
            rooms.add(newRoom);
        }
        return rooms;
    }

    /**
     *
     * @param world
     * @param rooms
     */
    public void placeGoldenDoor(TETile[][] world, ArrayList<Room> rooms){
        int randomRoom = RANDOM.nextInt(roomsCount);
        boolean goodSpot = false;
        XYCoords current = new XYCoords(rooms.get(randomRoom).center.xPos,rooms.get(randomRoom).center.yPos);
        //TODO: check pass by value
          int xIncrement = 0;
          int yIncrement = 0;

          switch (randomRoom % 4){
              case 0: yIncrement = 1;
              case 1: xIncrement = 1;
              case 2: yIncrement = -1;
              case 3: xIncrement = -1;
          }

          while(!goodSpot){
              if(world[current.xPos][current.yPos] != Tileset.WALL){
                  current.xPos += xIncrement;
                  current.yPos += yIncrement;
              }else if(world[current.xPos + 1][current.yPos] == Tileset.NOTHING ||
                       world[current.xPos][current.yPos + 1] == Tileset.NOTHING ||
                       world[current.xPos - 1][current.yPos] == Tileset.NOTHING ||
                       world[current.xPos][current.yPos - 1] == Tileset.NOTHING ){
                  goodSpot = true;
                  world[current.xPos][current.yPos] = Tileset.LOCKED_DOOR;
              }
          }
    }
    //TODO: implement
    public void placePlayer(TETile[][] world, ArrayList<Room> rooms){
        int startingRoomIndex = RANDOM.nextInt(roomsCount);
        Room startingRoom = rooms.get(startingRoomIndex);
        int xBound = startingRoom.topRightCorner.xPos - startingRoom.bottomLeftCorner.xPos + 1;
        int yBound = startingRoom.topRightCorner.yPos - startingRoom.bottomLeftCorner.yPos + 1;

        int playerXPos = RANDOM.nextInt(xBound) + startingRoom.bottomLeftCorner.xPos;
        int playerYPos = RANDOM.nextInt(yBound) + startingRoom.bottomLeftCorner.yPos;

        if(world[playerXPos][playerYPos].equals(Tileset.FLOOR)||world[playerXPos][playerYPos].equals(Tileset.FLOWER)){
            player.position.xPos = playerXPos;
            player.position.yPos = playerYPos;
            player.previousTile = world[playerXPos][playerYPos];
            world[playerXPos][playerYPos] = Tileset.PLAYER;
        }else{
            System.out.println("Error by placing player");
        }

        //world[startingRoom.center.xPos][startingRoom.center.yPos] = Tileset.PLAYER;
    }

    public TETile[][] fillWorldWithNothing(TETile[][] world){
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        return world;
    }

    //TODO: rename and remake function so that it's not misleading
    public TETile[][] setupGame() {
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        this.ter.initialize(WIDTH, HEIGHT + 3);
        world = fillWorldWithNothing(world);
        return world;
    }

    //Main function for testing
/*
    public static void main(String[] args){
        Game game = new Game();
        game.ter.initialize(WIDTH, HEIGHT);
        //Fill grid with !null
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }


        ArrayList<Room> rooms = new ArrayList<>();

        for(int i = 0; i < roomsCount; i += 1){
            Room newRoom = new Room(coords.get(i));
            newRoom.roomExpandRandomizer();

            world[newRoom.center.xPos][newRoom.center.yPos] = Tileset.FLOWER;
            rooms.add(newRoom);
        }

        rooms.sort(Room.xPosComparator);

        for(int i = 0; i < rooms.size(); i ++){
            rooms.get(i).drawRoomFloor(world);
            rooms.get(i).drawRoomWalls(world);
        }

        for(int i = 0; i < rooms.size()-1; i ++){
            rooms.get(i).drawPath(world, rooms.get(i+1));
        }

        for(int i = 0; i < rooms.size(); i ++){
            world[rooms.get(i).center.xPos][rooms.get(i).center.yPos] = Tileset.FLOWER;
        }

        game.placeGoldenDoor(world, rooms);
        game.ter.renderFrame(world);

    }
    */
}
