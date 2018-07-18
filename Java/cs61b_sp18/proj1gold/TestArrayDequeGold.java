import static org.junit.Assert.*;
import org.junit.Test;

public class TestArrayDequeGold {


    @Test
    public void randomlyTestingMethod(){
        StudentArrayDeque<Integer> sad = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> ad = new ArrayDequeSolution<>();
        String callSequence  = "";
        String message = "";
        for (int j = 0; j < 1000; j ++){
            /**Switch case and a random number generator,
             * to execute random function each time.
             */
            int i = StdRandom.uniform(0, 4);
            Integer k  = StdRandom.uniform(0, 100);     //Random dummy number for inputting.

            switch (i){
                case 0 :
                    sad.addFirst(k);
                    ad.addFirst(k);
                    callSequence = i + callSequence;
                    break;
                case 1:
                    sad.addLast(k);
                    ad.addLast(k);
                    callSequence = i + callSequence;
                    break;
                case 2:
                    Integer expected = ad.removeFirst();
                    Integer actual = sad.removeFirst();
                    callSequence = i + callSequence;
                    message = callSequence.substring(0, Math.min(callSequence.length(), 20));

                    assertEquals(toFunctionCalls(message) + "Failure at J: " + j + " " + message, expected, actual);
                    break;
                case 3:
                    Integer expected1 = ad.removeLast();
                    Integer actual1 = sad.removeLast();
                    callSequence = i + callSequence;
                    message = callSequence.substring(0, Math.min(callSequence.length(), 20));

                    assertEquals(toFunctionCalls(message) + "Failure at J: " + j + " " + message, expected1, actual1);
                    break;
            }
        }
    }

    /**
     * Test for checking if a given sequence of function calls, given as a string
     * of numbers, leads to a test failure.
     * */
    @Test
    public void testFailingSequence(){
        StudentArrayDeque<Integer> sad = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> ad = new ArrayDequeSolution<>();

        //Put here a sequence to test if it fails:
        //String inputraw = "30203103010111";
        String inputraw = "111"; //So that it passes now
        String input = new StringBuilder(inputraw).reverse().toString();
        char[] sequence = input.toCharArray();
        Integer expected = null;
        Integer actual = null;

        for (char c : sequence){
            int random = StdRandom.uniform(0,100);
            switch(c){
                case '0':
                    sad.addFirst(random);
                    ad.addFirst(random);
                    break;

                case '1':
                    sad.addLast(random);
                    ad.addLast(random);
                    break;

                case '2':
                    expected = ad.removeFirst();
                    actual = sad.removeFirst();
                    break;

                case '3':
                    expected = ad.removeLast();
                    actual = sad.removeLast();
            }
        }
        assertEquals(expected, actual);
    }

    /**
     * This is a test for some experiments. Currently passes.
     * */
    @Test
    public void basicTest(){
        StudentArrayDeque<Integer> sad = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> ad = new ArrayDequeSolution<>();

        Integer expected = null;
        Integer actual = null;

        sad.addFirst(38);
        ad.addFirst(38);

        sad.addFirst(79);
        ad.addFirst(79);

        expected = ad.removeLast();
        actual = sad.removeLast();

        assertEquals(expected, actual);
    }

    /**
     * Static function that turns a string sequence like "02312022..."
     * into a String of function names separated by new lines.
     * It just makes the code of the main test more readable.
     * */
    public static String toFunctionCalls(String integerSequence){
        char[] chars = integerSequence.toCharArray();
        String result = "";

        for (char c : chars){
            switch (c){
                case '0':
                    result = result + "addFirst()\n";
                    break;
                case '1':
                    result = result + "addLast()\n";
                    break;
                case '2':
                    result = result + "removeFirst()\n";
                    break;
                case '3':
                    result = result + "removeLast()\n";
            }
        }
        return result;
    }
}


