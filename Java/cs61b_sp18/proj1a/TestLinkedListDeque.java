import static org.junit.Assert.*;
import org.junit.Test;





/***
 * JUnit testing class for LinkedListDeque.java
 * This is not included in proj1a tasks but I am doing it for myself.
 */

public class TestLinkedListDeque {


    @Test
    public void testAddFirst(){
        LinkedListDeque list = new LinkedListDeque();
        list.addFirst(10);
        list.addFirst(11);

        assertEquals(10, list.sentinel.next.next.item);
        assertEquals(11, list.sentinel.next.item);
        assertEquals(10, list.sentinel.previous.item);
    }


    @Test
    public void testAddLast(){
        LinkedListDeque list = new LinkedListDeque();
        list.addLast(10);
        list.addLast(11);

        assertEquals(10, list.sentinel.previous.previous.item);
        assertEquals(11, list.sentinel.previous.item);
        assertEquals(10, list.sentinel.next.item);
        assertEquals(2, list.size);
    }


    @Test
    public void testIsEmpty(){
        LinkedListDeque list = new LinkedListDeque();
        LinkedListDeque list2 = new LinkedListDeque();
        list.addFirst(10);

        assertTrue(list2.isEmpty());
        assertFalse(list.isEmpty());


    }


    @Test
    public void testSize(){
        LinkedListDeque list = new LinkedListDeque();
        LinkedListDeque emptyList = new LinkedListDeque();
        list.addLast(10);
        list.addLast(11);

        assertEquals(2, list.size());
        assertEquals(0, emptyList.size());
    }


    @Test
    public void testPrintDequeHelper(){
        LinkedListDeque list = new LinkedListDeque();
        LinkedListDeque emptyList = new LinkedListDeque();
        list.addLast(10);
        list.addLast(11);
        list.addLast(12);
        list.addLast(13);

        String expected1 = "";
        String expected2 = "10 11 12 13";

        assertEquals(expected1, emptyList.printDequeHelper());
        assertEquals(expected2, list.printDequeHelper());
    }

    @Test
    public void testRemoveFirst(){
        LinkedListDeque<Integer> list = new LinkedListDeque<Integer>();
        LinkedListDeque<Integer> emptyList = new LinkedListDeque();
        LinkedListDeque<String> list2 = new LinkedListDeque<String>();

        list.addLast(10);
        list.addLast(11);
        list.addLast(12);
        list.addLast(13);

        list2.addFirst("Meow");
        list2.addFirst("Meow");


        int i = list.removeFirst();
        list2.removeFirst();



        assertEquals(10, i);
        assertEquals(3, list.size());
        assertEquals(11, list.sentinel.next.item);
        assertEquals(null, emptyList.removeFirst());
        assertEquals(list2.sentinel.next, list2.sentinel.previous);
    }

    @Test
    public void testRemoveLast(){
        LinkedListDeque<Integer> list = new LinkedListDeque<Integer>();
        LinkedListDeque emptyList = new LinkedListDeque();
        LinkedListDeque<String> list2 = new LinkedListDeque<String>();

        list.addLast(10);
        list.addLast(11);
        list.addLast(12);
        list.addLast(13);

        int i = list.removeLast();

        list2.addFirst("Miu");
        list2.addFirst("Meow");
        list2.removeLast();

        assertEquals(13, i);
        assertEquals(3, list.size());
        assertEquals(12, list.sentinel.previous.item);
        assertEquals(null, emptyList.removeFirst());
        assertEquals(list2.sentinel.next, list2.sentinel.previous);
    }


    @Test
    public void testGet(){
        LinkedListDeque<Integer> list = new LinkedListDeque<Integer>();
        LinkedListDeque emptyList = new LinkedListDeque();


        list.addLast(10);
        list.addLast(11);
        list.addLast(12);
        list.addLast(13);

        int i = list.get(0);

        assertEquals(10, i);
        assertNull(list.get(10));
        assertNull(emptyList.get(2));

    }

    @Test
    public void testGetRecursive(){
        LinkedListDeque<Integer> list = new LinkedListDeque<Integer>();
        LinkedListDeque emptyList = new LinkedListDeque();

        list.addLast(10);
        list.addLast(11);
        list.addLast(12);
        list.addLast(13);

        int i = list.getRecursive(0);

        assertEquals(10, i);
        assertNull(list.getRecursive(10));
        assertNull(emptyList.getRecursive(2));
    }

    //Test constructor of LinkedListDeque
    @Test
    public void TestLinkedListDequeConstructor(){
        LinkedListDeque list = new LinkedListDeque();

        assertEquals(0, list.size);
    }

    //Test constructor of a single Node
    @Test
    public void testDLNode(){
        LinkedListDeque.DLNode nodeInteger = new LinkedListDeque.DLNode(null, null, 228);
        LinkedListDeque.DLNode nodeString = new LinkedListDeque.DLNode(null, null, "Shido");

        nodeInteger.next = nodeString;
        nodeString.previous = nodeInteger;

        assertEquals(228, nodeInteger.item);
        assertEquals("Shido", nodeString.item);
        assertEquals(228, nodeString.previous.item);
    }


}
