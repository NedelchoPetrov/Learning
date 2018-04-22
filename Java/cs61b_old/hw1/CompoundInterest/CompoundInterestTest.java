import static org.junit.Assert.*;
import org.junit.Test;

public class CompoundInterestTest {

    @Test
    public void testNumYears() {
        /** Sample assert statement for comparing integers.

         assertEquals(0, 0); */
        assertEquals(0, CompoundInterest.numYears(2018));
        assertEquals(2, CompoundInterest.numYears(2020));
    }

    @Test
    public void testFutureValue() {
        double tolerance = 0.01;
        double mynum = CompoundInterest.futureValue(10, 12, 2020);
        assertTrue(12.544 - tolerance <= mynum && mynum <= 12.544 + tolerance);
    }

    @Test
    public void testFutureValueReal() {
        double tolerance = 0.01;
        double mynum = CompoundInterest.futureValueReal(1000000, 0, 2058, 3);
        assertTrue(295712.28 - tolerance <= mynum && mynum <= 295712.28 + tolerance);
    }


    @Test
    public void testTotalSavings() {
        double tolerance = 0.01;
    }

    @Test
    public void testTotalSavingsReal() {
        double tolerance = 0.01;
    }


    /* Run the unit tests in this file. */
    /*
    public static void main(String... args) {
        System.exit(ucb.junit.textui.runClasses(CompoundInterestTest.class));
    }
}
*/
}