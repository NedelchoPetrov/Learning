import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

public class IntListTest {

    /** Example test that verifies correctness of the IntList.list static
     *  method. The main point of this is to convince you that
     *  assertEquals knows how to handle IntLists just fine.
     */

    @Test
    public void testList() {
        IntList one = new IntList(1, null);
        IntList twoOne = new IntList(2, one);
        IntList threeTwoOne = new IntList(3, twoOne);

        IntList x = IntList.list(3, 2, 1);
        assertEquals(threeTwoOne, x);
    }

    /** Do not use the new keyword in your tests. You can create
     *  lists using the handy IntList.list method.
     *
     *  Make sure to include test cases involving lists of various sizes
     *  on both sides of the operation. That includes the empty list, which
     *  can be instantiated, for example, with
     *  IntList empty = IntList.list().
     *
     *  Keep in mind that dcatenate(A, B) is NOT required to leave A untouched.
     *  Anything can happen to A.
     */

    @Test
    public void testDcatenate() {
        IntList a = IntList.list(2, 4, 6);
        IntList a2 = IntList.list(2, 4, 6);
        IntList a3 = IntList.list(2, 4, 6);
        IntList b = IntList.list(1, 3, 5);
        IntList empty1 = IntList.list();
        IntList empty2 = IntList.list();

        IntList ab = IntList.list(2,4,6,1,3,5);
        IntList ba = IntList.list(1,3,5,2,4,6);
        IntList empty1empty2 = IntList.list();
        IntList empty1a = IntList.list(2,4,6);

        assertEquals(ab, IntList.dcatenate(a, b));
        assertEquals(ba, IntList.dcatenate(b, a2));
        assertEquals(empty1empty2, IntList.dcatenate(empty1, empty2));
        assertEquals(empty1a, IntList.dcatenate(empty1, a3));

    }

    /** Tests that subtail works properly. Again, don't use new.
     *
     *  Make sure to test that subtail does not modify the list.
     */

    @Test
    public void testSubtail() {

        IntList list = IntList.list(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);


        IntList tail0 = IntList.list(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        IntList tail4 = IntList.list(4, 5, 6, 7, 8, 9, 10);
        IntList tail10 = IntList.list(10);
        IntList tail20 = IntList.list();
        IntList tail7 = IntList.list(7, 8, 9, 10);

        assertEquals(tail0, IntList.subTail(list, 0));
        assertEquals(tail4, IntList.subTail(list, 4));
        assertEquals(tail7, IntList.subTail(list, 7));
        assertEquals(tail10, IntList.subTail(list, 10));
        assertEquals(tail20, IntList.subTail(list, 20));

    }

    /** Tests that sublist works properly. Again, don't use new.
     *
     *  Make sure to test that sublist does not modify the list.
     */

    @Test
    public void testSublist() {

        IntList list = IntList.list(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);


        IntList tail0_11 = IntList.list(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        IntList tail0_6 = IntList.list(0, 1, 2, 3, 4, 5);
        IntList tail5_6 = IntList.list(5, 6, 7, 8, 9, 10);
        IntList tail5_7 = IntList.list(5, 6, 7, 8, 9, 10);
        IntList tail5_1 = IntList.list(5);
        IntList tail10_5 = IntList.list(10);
        IntList tail12_4 = IntList.list();
        IntList tail3_0 = IntList.list();


        assertEquals(tail0_11, IntList.sublist(list, 0, 11));
        assertEquals(tail0_6, IntList.sublist(list, 0, 6));
        assertEquals(tail5_6, IntList.sublist(list, 5, 6));
        assertEquals(tail5_7, IntList.sublist(list, 5, 7));
        assertEquals(tail5_1, IntList.sublist(list, 5, 1));
        assertEquals(tail10_5, IntList.sublist(list, 10, 5));
        assertEquals(tail12_4, IntList.sublist(list, 12, 4));
        assertEquals(tail3_0, IntList.sublist(list, 3, 0));

    }

    /** Tests that dSublist works properly. Again, don't use new.
     *
     *  As with testDcatenate, it is not safe to assume that list passed
     *  to dSublist is the same after any call to dSublist
     */

    @Test
    public void testDsublist() {


    }


    /* Run the unit tests in this file. */
    /*
    public static void main(String... args) {
        System.exit(ucb.junit.textui.runClasses(IntListTest.class));
    }
    */
}
