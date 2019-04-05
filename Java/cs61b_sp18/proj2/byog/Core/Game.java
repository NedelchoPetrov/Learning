package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;
import edu.princeton.cs.introcs.StdOut;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class Game implements Serializable {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;

    private static int SEED = 11113453; //1111345, 11113457 are cool
    public static Random RANDOM = null;
    private static int roomsCount = -1;
    public Player player = new Player();
    public TETile[][] gameWorld = null;


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
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public static TETile[][] playWithInputString(String input, Game game) {
        // TODO: Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().
        game.setupGame();


        boolean gameStarted = false;
        boolean showStartMenu = true;
        boolean loadGame = false;
        //----------------------------------------------------------------------------------------------
        while (!gameStarted) {
            if (showStartMenu) {
                game.displayStartMenu();
                showStartMenu = false;
            }
            if (StdDraw.hasNextKeyTyped()) {
                char c = StdDraw.nextKeyTyped();
                if (c == 'n' || c == 'q') {
                    gameStarted = true;
                    //TODO: Implement full function
                    game.menuControls(c);
                }else if(c == 'l'){
                    game = loadGame();
                    loadGame = true;
                    gameStarted = true;
                }
            }
        }
        //----------------------------------------------------------------------------------------
        if(!loadGame) {
            RANDOM = new Random(SEED);
            roomsCount = generateRandomCountOfRooms();
            ArrayList<XYCoords> roomCoords = game.generateRandomCoordinates();
            ArrayList<Room> rooms = game.generateRandomRooms(roomCoords);

            rooms.sort(Room.xPosComparator);

            for (int i = 0; i < rooms.size(); i++) {
                rooms.get(i).drawRoomFloor(game.gameWorld);
                rooms.get(i).drawRoomWalls(game.gameWorld);
            }

            for (int i = 0; i < rooms.size() - 1; i++) {
                rooms.get(i).drawPath(game.gameWorld, rooms.get(i + 1));
            }

            //Put FLOWER on each room center
            for (int i = 0; i < rooms.size(); i++) {
                game.gameWorld[rooms.get(i).center.xPos][rooms.get(i).center.yPos] = Tileset.FLOWER;
            }

            game.placeGoldenDoor(rooms);
            game.placePlayer(rooms);
        }
            game.waitForInput();
            TETile[][] finalWorldFrame = game.gameWorld;
            return finalWorldFrame;

    }

    /**
     * Method for displaying mouseover infos.
     */
    public void showMousePointerInfo(String string) {
        Font font = new Font("Monaco", Font.BOLD, 14);
        StdDraw.setFont(font);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.textLeft(3, HEIGHT + 2, "You are looking at " + string);
    }

    public String followMouse(int xPos, int yPos) {
        //int xPos = (int)Math.floor(StdDraw.mouseX());
        //int yPos = (int)Math.floor(StdDraw.mouseY());
        String tile = "";
        if (xPos < this.WIDTH && yPos < this.HEIGHT) {
            tile = gameWorld[xPos][yPos].description();
        }
        return tile;
    }

    /**
     * Generates number of rooms between hardcoded 5 and 20.
     *
     * @return
     */
    private static int generateRandomCountOfRooms() {
        int roomsCount = 0;

        while (roomsCount < 5) {
            roomsCount = RANDOM.nextInt(20);
        }
        return roomsCount;
    }

    /**
     * Generates a list of random XYCoords that will be the starting points for rooms.
     *
     * @return
     */
    public ArrayList<XYCoords> generateRandomCoordinates() {
        ArrayList<XYCoords> coords = new ArrayList<>(roomsCount);

        for (int i = 0; i < roomsCount; i += 1) {
            XYCoords xy = new XYCoords(RANDOM.nextInt(WIDTH - 1), RANDOM.nextInt(HEIGHT - 1));
            if (xy.xPos == 0) {
                xy.xPos += 1;
            }
            if (xy.yPos == 0) {
                xy.yPos += 1;
            }

            coords.add(xy);
        }
        return coords;
    }

    /**
     * Takes a list with room centers and generates rooms of random sizes aorund them.
     *
     * @param coords
     * @return
     */
    public ArrayList<Room> generateRandomRooms(ArrayList<XYCoords> coords) {
        ArrayList<Room> rooms = new ArrayList<>();

        for (int i = 0; i < roomsCount; i += 1) {
            Room newRoom = new Room(coords.get(i));
            newRoom.roomExpandRandomizer();
            rooms.add(newRoom);
        }
        return rooms;
    }

    /**
     * @param rooms
     */
    public void placeGoldenDoor(ArrayList<Room> rooms) {
        int randomRoom = RANDOM.nextInt(roomsCount);
        boolean goodSpot = false;
        XYCoords current = new XYCoords(rooms.get(randomRoom).center.xPos, rooms.get(randomRoom).center.yPos);
        int xIncrement = 0;
        int yIncrement = 0;


        if (randomRoom % 4 == 0) {
            yIncrement = 1;
        } else if (randomRoom % 4 == 1) {
            xIncrement = 1;
        } else if (randomRoom % 4 == 2) {
            yIncrement = -1;
        } else if (randomRoom % 4 == 3) {
            xIncrement = -1;
        } else {
            System.out.println("Error by placeGoldenDoor!");
        }


        while (!goodSpot) {
            if (gameWorld[current.xPos][current.yPos] != Tileset.WALL) {
                current.xPos += xIncrement;
                current.yPos += yIncrement;

            } else if (current.xPos == 0 || current.xPos == WIDTH - 1 || current.yPos == 0 || current.yPos == HEIGHT - 1) {
                goodSpot = true;
                gameWorld[current.xPos][current.yPos] = Tileset.LOCKED_DOOR;
            } else if (gameWorld[current.xPos + 1][current.yPos] == Tileset.NOTHING || gameWorld[current.xPos - 1][current.yPos] == Tileset.NOTHING || gameWorld[current.xPos][current.yPos + 1] == Tileset.NOTHING || gameWorld[current.xPos][current.yPos - 1] == Tileset.NOTHING) {
                goodSpot = true;
                gameWorld[current.xPos][current.yPos] = Tileset.LOCKED_DOOR;
            }
        }
    }

    /**
     * Choses a random room in the world, then choses a random FLOOR location in that room and places player there.
     *
     * @param rooms
     */
    public void placePlayer(ArrayList<Room> rooms) {
        int startingRoomIndex = RANDOM.nextInt(roomsCount);
        Room startingRoom = rooms.get(startingRoomIndex);
        int xBound = startingRoom.topRightCorner.xPos - startingRoom.bottomLeftCorner.xPos + 1;
        int yBound = startingRoom.topRightCorner.yPos - startingRoom.bottomLeftCorner.yPos + 1;

        int playerXPos = RANDOM.nextInt(xBound) + startingRoom.bottomLeftCorner.xPos;
        int playerYPos = RANDOM.nextInt(yBound) + startingRoom.bottomLeftCorner.yPos;

        if (gameWorld[playerXPos][playerYPos].equals(Tileset.FLOOR) || gameWorld[playerXPos][playerYPos].equals(Tileset.FLOWER)) {
            player.position.xPos = playerXPos;
            player.position.yPos = playerYPos;
            player.previousTile = gameWorld[playerXPos][playerYPos];
            gameWorld[playerXPos][playerYPos] = Tileset.PLAYER;
        } else {
            System.out.println("Error by placing player");
        }
    }

    public void displayVictoryMessage() {
        Font font = new Font("Monaco", Font.BOLD, 40);
        StdDraw.setFont(font);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(WIDTH / 2, HEIGHT + 1, "Victory!");
        StdDraw.show();
    }

    public String askForSeed(String seed) {
        StdDraw.clear(Color.BLACK);
        Font font = new Font("Monaco", Font.BOLD, 20);
        StdDraw.setFont(font);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(WIDTH / 2, HEIGHT / 2 + 4, "Type in seed! Press (g) when ready: ");
        StdDraw.text(WIDTH / 2, HEIGHT / 2, seed);
        StdDraw.show();
        return seed;

    }

    /**
     * Fill all tiles in the world with Tileset.NOTHING, to avoid NullPointerExceptions.
     *
     * @return
     */
    public TETile[][] fillWorldWithNothing() {
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                gameWorld[x][y] = Tileset.NOTHING;
            }
        }
        return gameWorld;
    }

    public void menuControls(char c) {
        if (c == 'n') {
            String seed = "";
            while (true) {
                askForSeed(seed);
                if (StdDraw.hasNextKeyTyped()) {
                    char b = StdDraw.nextKeyTyped();
                    if ("0123456789".indexOf(b) != -1) {
                        seed += b;
                    } else if (b == 'g') {
                        SEED = Integer.parseInt(seed);
                        System.out.println(this.SEED);
                        break;//starts the game
                    }
                }
            }

            this.showMousePointerInfo("Monitor");

            //System.out.println(TETile.toString(worldState));
            //this.waitForInput(world);
        } else if (c == 'l') {
            loadGame();
            System.out.println("Save/Load to be implemented");
        } else if (c == 'q') {
            System.exit(0);
        }
    }

    //TODO: rename and remake function so that it's not misleading
    public void setupGame() {
        gameWorld = new TETile[WIDTH][HEIGHT];
        this.ter.initialize(WIDTH, HEIGHT + 3);
        gameWorld = fillWorldWithNothing();

    }

    public void displayStartMenu() {
        StdDraw.clear(Color.BLACK);
        Font font1 = new Font("Monaco", Font.PLAIN, 50);
        Font font2 = new Font("Monaco", Font.PLAIN, 20);
        StdDraw.setFont(font1);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(WIDTH / 2, HEIGHT - 7, "CS61b: The Game");
        StdDraw.setFont(font2);
        StdDraw.text(WIDTH / 2, HEIGHT - 15, "New Game (n)");
        StdDraw.text(WIDTH / 2, HEIGHT - 17, "Load Game (l)");
        StdDraw.text(WIDTH / 2, HEIGHT - 19, "Quit Game (q)");
        StdDraw.show();
    }

    /**
     * Waiting for player input and checking for win conditions.
     */
    public void waitForInput() {
        this.ter.renderFrame(gameWorld);
        boolean change = false;
        int oldX = (int) Math.floor(StdDraw.mouseX());
        int oldY = (int) Math.floor(StdDraw.mouseY());
        String underMouse = "Monitor";

        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                change = true;
                char c = StdDraw.nextKeyTyped();

                if (c == 'e') {
                    saveGame();
                } else {
                    this.player.move(gameWorld, c);
                }
            }

            int newX = (int) Math.floor(StdDraw.mouseX());
            int newY = (int) Math.floor(StdDraw.mouseY());
            if (oldX != newX || oldY != newY) {
                change = true;
                oldX = newX;
                oldY = newY;
                underMouse = this.followMouse(newX, newY);
            }

            if (change) {
                this.ter.renderFrame(gameWorld);
                player.showScore();
                this.showMousePointerInfo(underMouse);
                StdDraw.show();
                change = false;
            }

            if (player.winCondition) {
                displayVictoryMessage();
                StdDraw.show();
                break;
            }

            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void saveGame() {
        File f = new File("./savedGame.ser");
        try {
            if (!f.exists()) {
                f.createNewFile();
            }
            FileOutputStream fs = new FileOutputStream(f);
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(this);
            os.close();
            System.exit(0);
        } catch (FileNotFoundException e) {
            System.out.println("file not found");
            System.exit(0);
        } catch (IOException e) {
            System.out.println(e);
            System.exit(0);
        }
    }

    public static Game loadGame() {
        File f = new File("./savedGame.ser");
        if (f.exists()) {
            System.out.println("Exists");
            try {
                FileInputStream fs = new FileInputStream(f);
                ObjectInputStream os = new ObjectInputStream(fs);
                Game game = (Game) os.readObject();
                os.close();
                return game;
            } catch (FileNotFoundException e) {
                System.out.println("file not found");
                System.exit(0);
            } catch (IOException e) {
                System.out.println(e);
                System.exit(0);
            } catch (ClassNotFoundException e) {
                System.out.println("class not found");
                System.exit(0);
            }
        }
        System.out.println("Loaded Emoty Game. File doesn't exist!");
        return new Game();
    }
}
