package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.io.Serializable;

public class Player implements Serializable {
    public TETile previousTile = Tileset.FLOOR;
    XYCoords position = new XYCoords(0,0);
    int score = 0;
    boolean winCondition = false;

    /**
     * Call to check wether the movement in that direction is allowed or blocked by wall, etc.
     * @param xPos
     * @param yPos
     * @param world
     * @return
     */
    public boolean checkMove(int xPos, int yPos, TETile[][] world){
        boolean moveOk = false;
        TETile target;
        if(xPos < Game.WIDTH && xPos > 0 && yPos < Game.HEIGHT && yPos > 0) {
            target = world[xPos][yPos];
        }else{
            return moveOk;
        }
        if(target.equals(Tileset.FLOOR)){
            moveOk = true;
        }else if(target.equals(Tileset.FLOWER)){
            moveOk = true;
            score += 1;
        }
        else if(target.equals(Tileset.LOCKED_DOOR)){
            moveOk = true;
            winCondition = true;
        }
        return moveOk;
    }

    public void move(TETile[][] world, char direction){
        XYCoords newPos = new XYCoords(this.position.xPos, this.position.yPos);
        if(direction == 'w'){
            newPos.yPos += 1;
        }else if(direction == 's'){
            newPos.yPos -= 1;
        }else if(direction == 'a'){
            newPos.xPos -= 1;
        }else if(direction == 'd'){
            newPos.xPos += 1;
        }else if(direction == 'q'){
            System.exit(0);
        }

        if(checkMove(newPos.xPos, newPos.yPos, world)){
            if(previousTile.equals(Tileset.FLOWER)){
                previousTile = Tileset.FLOOR;
            }
            world[this.position.xPos][this.position.yPos] = previousTile;
            this.position = newPos;
            previousTile = world[this.position.xPos][this.position.yPos];
            world[this.position.xPos][this.position.yPos] = Tileset.PLAYER;
        }
    }

    /**
     * Shows the current score.
     */
    public void showScore(){
        Font font = new Font("Monaco", Font.BOLD, 14);
        StdDraw.setFont(font);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.textRight(Game.WIDTH - 4, Game.HEIGHT + 2, "Score: " + score);
        StdDraw.show();
    }
}

