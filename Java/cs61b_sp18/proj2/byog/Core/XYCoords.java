package byog.Core;

import java.io.Serializable;

public class XYCoords implements Serializable {

    public int xPos;
    public int yPos;

    public XYCoords(int xPos, int yPos){
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public String printC (){
        return "(" + this.xPos + ", " + this.yPos + ")";
    }
}
