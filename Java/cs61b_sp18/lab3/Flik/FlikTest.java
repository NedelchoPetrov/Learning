import static org.junit.Assert.*;
import org.junit.Test;

public class FlikTest {

    @Test
    public void testIsSameNumber(){
        int a = 9;
        int b = 9;
        int c = 8;
        int d = -9;

        assertTrue(Flik.isSameNumber(a, b));
        assertFalse(Flik.isSameNumber(a, c));
        assertFalse(Flik.isSameNumber(b, d));
    }
}
