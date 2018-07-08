import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {

    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();

    // Your tests go here.

    @Test
    public void testEqualChars(){
        char a = 'a';
        char b = 'b';
        char a2 = 'a';
        char x = '&';
        char y =  '%';
        char c = 'c';

        assertFalse(offByOne.equalChars(a, a2));
        assertTrue(offByOne.equalChars(a, b));
        assertTrue(offByOne.equalChars(x, y));
        assertTrue(offByOne.equalChars(b, c));
        assertFalse(offByOne.equalChars(a, c));

    }

}
