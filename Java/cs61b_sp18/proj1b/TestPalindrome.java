import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testIsPalindrome(){
        String empty = "";
        String input1 = "cat";
        String input2 = "bob";
        String input3 = "a";


        assertTrue(palindrome.isPalindrome(empty));
        assertFalse(palindrome.isPalindrome(input1));
        assertTrue(palindrome.isPalindrome(input2));
        assertTrue(palindrome.isPalindrome(input3));
    }

    @Test
    public void testIsPalindromeCC() {
        String flake = "flake";
        String empty = "";
        String oneChar = "a";
        String flke = "flke";
        String random = "random";

        CharacterComparator comp = new OffByOne();

        assertTrue(palindrome.isPalindrome(flake, comp));
        assertTrue(palindrome.isPalindrome(empty, comp));
        assertTrue(palindrome.isPalindrome(oneChar, comp));
        assertTrue(palindrome.isPalindrome(flke, comp));
        assertFalse(palindrome.isPalindrome(random, comp));

    }
}
