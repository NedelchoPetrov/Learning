package byog.lab6;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

public class MemoryGame {
    private int width;
    private int height;
    private int round;
    private Random rand;
    private boolean gameOver;
    private boolean playerTurn;
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
                                                   "You got this!", "You're a star!", "Go Bears!",
                                                   "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please enter a seed");
            return;
        }

        int seed = Integer.parseInt(args[0]);
        MemoryGame game = new MemoryGame(40, 40);
        game.rand = new Random(seed);
        game.startGame();
    }

    public MemoryGame(int width, int height) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        //TODO: Initialize random number generator
    }

    public String generateRandomString(int n) {
        //TODO: Generate random string of letters of length n
        String result = "";
        for(int i = 0; i < n; i += 1){
            int pos = rand.nextInt(26);
            result += CHARACTERS[pos];
        }
        return result;
    }

    public void drawFrame(String s, String State) {
        //TODO: Take the string and display it in the center of the screen
        StdDraw.clear(Color.BLACK);
        Font font1 = new Font("Monaco", Font.BOLD, 30);
        Font font2 = new Font("Serif", Font.PLAIN, 30);
        StdDraw.setFont(font1);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(this.width/2, this.height/2, s);
        StdDraw.setFont(font2);
        StdDraw.text(5, this.height - 2, "Round " + round);
        StdDraw.text(this.width - 8, this.height - 2, "You are the best");
        StdDraw.text(this.width/2, this.height - 2, State);

        StdDraw.show();

        //TODO: If game is not over, display relevant game information at the top of the screen
    }

    public void flashSequence(String letters) {
        //TODO: Display each character in letters, making sure to blank the screen between letters
        for(Character c : letters.toCharArray()){
            try{
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.drawFrame(c.toString(), "Watch!");
            try{
                Thread.sleep(1000);
            }catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
        this.drawFrame("", "Type!");
    }

    public String solicitNCharsInput(int n) {
        //TODO: Read n letters of player input
        String result = "";
        for(int i = 0; i < n; i += 1){

            while (!StdDraw.hasNextKeyTyped()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
                result += StdDraw.nextKeyTyped();
                this.drawFrame(result, "Type!");
        }
        return result;
    }

    public void startGame() {
        //TODO: Set any relevant variables before the game starts
        round = 1;
        boolean gameOver = false;
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //TODO: Establish Game loop
        while(!gameOver){
            drawFrame("Round " + round, "Watch!");

            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            String s = generateRandomString(round);
            flashSequence(s);
            String input = solicitNCharsInput(round);

            if (s.equals(input)){
                round += 1;
            }else{
                gameOver = true;
                drawFrame("Game Over! You made it to round " + round, "");
            }
        }

    }

}
