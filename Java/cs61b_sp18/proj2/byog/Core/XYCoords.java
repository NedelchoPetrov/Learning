package byog.Core;

public class XYCoords {

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
