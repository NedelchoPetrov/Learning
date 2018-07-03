import java.net.SocketOption;

public class HorribleSteve {
    public static void main(String [] args) {
        int i = 0;
        for (int j = 0; i < 500; ++i, ++j) {
            System.out.println("i = " + i);
            System.out.println("j = " + j);

            if (!Flik.isSameNumber(i, j)) {
                System.out.println(i + "  " + j);
                break; // break exits the for loop!
            }
        }
        System.out.println("i is " + i);
    }
}
