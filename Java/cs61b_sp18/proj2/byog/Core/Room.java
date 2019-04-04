package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import java.util.Comparator;

public class Room implements Comparable<Room> {

    public XYCoords center = null;
    public XYCoords bottomLeftCorner = null;
    public XYCoords topRightCorner = null;
    public static final Comparator<Room> xPosComparator = new XPosComparator();


    public Room(XYCoords center){
        this.center = center;
        this.bottomLeftCorner = new XYCoords(center.xPos, center.yPos);
        this.topRightCorner = new XYCoords(center.xPos, center.yPos);
    }

    @Override
    public int compareTo(Room o) {
        if(this.center.xPos + this.center.yPos > o.center.xPos + o.center.yPos){
            return 1;
        }else {
            return -1;
        }
    }

    /**
     * Randomly decides whether the room should further expand with 2:1 yes to no probability.
     * @return
     */
    private static boolean repeatExpansionRandomizer(){
        int repeat = Game.RANDOM.nextInt(3);

        if(repeat == 0){
            return false;
        }else{
            return true;
        }
    }

    /**
     * Draws the floor of the room including the edges.
     * @param world
     */
    public void drawRoomFloor(TETile[][] world) {
        int roomWidth = this.topRightCorner.xPos - this.bottomLeftCorner.xPos;
        int roomHeight = this.topRightCorner.yPos - this.bottomLeftCorner.yPos;

        for (int i = 0; i < roomWidth + 1; i += 1){
            for (int j = 0; j < roomHeight + 1; j += 1){
                world[this.bottomLeftCorner.xPos + i][this.bottomLeftCorner.yPos + j] = Tileset.FLOOR;
            }
        }
    }

    /**
     * Draws walls on the rows outside of the edges. Skips if there are floor tiles, so that rooms can merge.
     * @param world
     */
    //TODO: Refactor with drawSingleWall() function for readability.
    public void drawRoomWalls(TETile[][] world){
        int roomWidth = this.topRightCorner.xPos - this.bottomLeftCorner.xPos;
        int roomHeight = this.topRightCorner.yPos - this.bottomLeftCorner.yPos;

        //Draw left and right horizontal walls.
        for(int i = 0; i < roomWidth + 3; i += 1){
            if(world[this.bottomLeftCorner.xPos - 1 + i][this.bottomLeftCorner.yPos - 1] == Tileset.NOTHING) {
                world[this.bottomLeftCorner.xPos - 1 + i][this.bottomLeftCorner.yPos - 1] = Tileset.WALL;
            }
            if(world[this.bottomLeftCorner.xPos - 1 + i][this.topRightCorner.yPos + 1] == Tileset.NOTHING) {
                world[this.bottomLeftCorner.xPos - 1 + i][this.topRightCorner.yPos + 1] = Tileset.WALL;
            }
        }

        //Draw upper and lower vertical walls.
        for(int i = 0; i < roomHeight + 3; i += 1){
            if(world[this.bottomLeftCorner.xPos - 1][this.bottomLeftCorner.yPos - 1 + i] == Tileset.NOTHING) {
                world[this.bottomLeftCorner.xPos - 1][this.bottomLeftCorner.yPos - 1 + i] = Tileset.WALL;
            }
            if(world[this.topRightCorner.xPos + 1][this.bottomLeftCorner.yPos - 1 + i] == Tileset.NOTHING){
                world[this.topRightCorner.xPos + 1][this.bottomLeftCorner.yPos - 1 + i] = Tileset.WALL;
            }
        }
    }

    /**
     * Draws a single Wall on given tile that is not FLOOR.
     * @param world
     * @param xPos
     * @param yPos
     */
    public static void putSingleWall(TETile[][] world, int xPos, int yPos){
        if(world[xPos][yPos] == Tileset.FLOOR){
            return;
        }else{
            world[xPos][yPos] = Tileset.WALL;
        }
    }

    /**
     *   #   #   : Paths with turns are drawn with two for loops, each drawing one straight line and the walls on
     *     C     : it's sides. This function is called between the two loops, on the spot where the two lines merge,
     *   #   #   : to draw WALLs on the diagonals, so that the edge doesn't look like...
     *   --------
     *   ###.#   : ...with missing WALL on the place of 0.
     *   ....#   :
     *   ####0   :
     *
     * @param world
     * @param xPos
     * @param yPos
     */
    public static void drawPathCorner(TETile[][] world, int xPos, int yPos){
        putSingleWall(world, xPos + 1, yPos + 1);
        putSingleWall(world, xPos + 1, yPos - 1);
        putSingleWall(world, xPos - 1, yPos + 1);
        putSingleWall(world, xPos - 1, yPos - 1);
    }

    /**
     * Draws a path and the walls on it's sides between two Rooms using for loops.
     * Currently, without randomness, prioritizes the horizontal direction.
     * @param world
     * @param targetRoom
     */
    //TODO: Add random choice between horizontal and vertical direction.
    public void drawPath(TETile[][] world, Room targetRoom) {
        int xDifference = targetRoom.center.xPos - this.center.xPos;
        int yDifference = targetRoom.center.yPos - this.center.yPos;

        if (xDifference >= 0) {
            for (int i = 1; i < xDifference + 1; i += 1) {
                world[this.center.xPos + i][this.center.yPos] = Tileset.FLOOR;
                putSingleWall(world, this.center.xPos + i, this.center.yPos + 1);
                putSingleWall(world, this.center.xPos + i, this.center.yPos - 1);
            }
            drawPathCorner(world, this.center.xPos + xDifference, this.center.yPos);
        } else {
            for (int i = xDifference; i < 1; i += 1) {
                world[this.center.xPos + i][this.center.yPos] = Tileset.FLOOR;
                putSingleWall(world, this.center.xPos + i, this.center.yPos + 1);
                putSingleWall(world, this.center.xPos + i, this.center.yPos - 1);
            }
            drawPathCorner(world, this.center.xPos + xDifference, this.center.yPos);
        }

        if (yDifference >= 0) {
            for (int j = 0; j < yDifference + 1; j++) {
                world[this.center.xPos + xDifference][this.center.yPos + j] = Tileset.FLOOR;
                putSingleWall(world, this.center.xPos + xDifference + 1, this.center.yPos + j);
                putSingleWall(world, this.center.xPos + xDifference - 1, this.center.yPos + j);
            }
        } else {
            for (int j = yDifference; j < 1; j++) {
                world[this.center.xPos + xDifference][this.center.yPos + j] = Tileset.FLOOR;
                putSingleWall(world, this.center.xPos + xDifference + 1, this.center.yPos + j);
                putSingleWall(world, this.center.xPos + xDifference - 1, this.center.yPos + j);
            }
        }
    }

    /**
     * Given a room checks if it's corners are between 1 and HEIGHT/WIDTH -2 of the map. If not puts them back in.
     * I can sense that it can hide some buggy situations, though I have not encountered them yet on a seed.
     * Problems might arise if Room centers are generated next to the corner of the map (on 0 and HEIGHT/WIDTH-1), as
     * then the edges will be set outside of the room center. This is currently not allowed.
     */
    private void putRoomBackInMap() {
        if(this.topRightCorner.xPos > Game.WIDTH-2) {
            this.topRightCorner.xPos = Game.WIDTH -2;
        }
        if(this.topRightCorner.yPos > Game.HEIGHT-2) {
            this.topRightCorner.yPos = Game.HEIGHT -2;
        }
        if(this.bottomLeftCorner.xPos < 1) {
            this.bottomLeftCorner.xPos = 1;
        }
        if(this.bottomLeftCorner.yPos < 1) {
            this.bottomLeftCorner.yPos = 1;
        }
    }

    /**
     * Called to each room expands its corners randomly until 'random' is false. On each iteration swaps between
     * 'bottomLeftCorner' and 'topRightCorner' and expands them on one or on both axises with 1 tile.
     */
    public void roomExpandRandomizer(){
        boolean repeat = repeatExpansionRandomizer();
        boolean currentEdgeSwapper = true;

        while(repeat){
            //Expanding decisions
            int expandDirection = Game.RANDOM.nextInt(3);

            if(currentEdgeSwapper){
                currentEdgeSwapper = false;
                switch(expandDirection){
                    case 0: this.bottomLeftCorner.xPos -= 1;
                    case 1: this.bottomLeftCorner.yPos -= 1;
                    case 2:
                        this.bottomLeftCorner.xPos -= 1;
                        this.bottomLeftCorner.yPos -= 1;
                }
            }else{
                currentEdgeSwapper = true;
                switch(expandDirection){
                    case 0: this.topRightCorner.xPos += 1;
                    case 1: this.topRightCorner.yPos += 1;
                    case 2:
                        this.topRightCorner.xPos += 1;
                        this.topRightCorner.yPos += 1;
                    }
                }
                repeat = repeatExpansionRandomizer();
            }
        this.putRoomBackInMap();
        }

        private static class XPosComparator implements Comparator<Room>{
            @Override
            public int compare(Room o1, Room o2) {
                return o1.compareTo(o2);
            }
        }
}


