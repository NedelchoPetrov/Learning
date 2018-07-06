import static org.junit.Assert.*;

import com.sun.jdi.connect.Connector.IntegerArgument;
import org.junit.Test;

public class TestArrayDeque {


    @Test
    public void testSize(){
        ArrayDeque deque = new ArrayDeque();

        for(int i = 0; i < 10; i++){
            deque.addFirst(0);
            deque.addLast(1);
        }

        assertEquals(20, deque.size());
    }


    @Test
    public void testIsEmpty(){
        ArrayDeque deque = new ArrayDeque();
        ArrayDeque emptyDeque = new ArrayDeque();

        deque.addFirst(0);

        assertTrue(emptyDeque.isEmpty());
        assertFalse(deque.isEmpty());
    }

    @Test
    public void testPrintDequeHelper(){
        ArrayDeque deque = new ArrayDeque();
        ArrayDeque emptyDeque = new ArrayDeque();
        deque.addLast(10);
        deque.addLast(11);
        deque.addLast(12);
        deque.addFirst(13);

        String expected1 = "";
        String expected2 = "13 10 11 12";

        assertEquals(expected1, emptyDeque.printDequeHelper());
        assertEquals(expected2, deque.printDequeHelper());
    }


    @Test
    public void testAddLast(){
        ArrayDeque deque = new ArrayDeque();

        deque.addLast(1);
        deque.addLast(2);
        deque.addLast(3);

        assertEquals(3, deque.size);
        assertEquals(4, deque.last);
        assertEquals(0, deque.first);
        assertEquals(2, deque.items[2]);
    }


    @Test
    public void testResize(){
        ArrayDeque deque = new ArrayDeque();
        deque.resize();

        assertEquals(16,deque.items.length);
    }


    @Test
    public void testAddFirst(){
        ArrayDeque deque = new ArrayDeque();

        deque.addFirst(0);

        assertEquals(0, deque.items[0]);
        assertEquals(7, deque.first);
        assertEquals(1, deque.size);
    }

    @Test
    public void testRemoveFirst(){
        ArrayDeque deque = new ArrayDeque();
        ArrayDeque emptyDeque = new ArrayDeque();

        deque.addFirst(0);
        deque.addLast(1);
        deque.addLast(2);
        deque.addLast(3);
        deque.addLast(4);

        int i = (int) deque.removeFirst();

        assertEquals(4, deque.size());
        assertEquals(0, i);
        assertEquals(0, deque.first); //position of pointer, not element
        assertNull(emptyDeque.removeFirst());
    }


    @Test
    public void testRemoveLast(){
        ArrayDeque deque = new ArrayDeque();
        ArrayDeque emptyDeque = new ArrayDeque();

        deque.addFirst(0);
        deque.addLast(1);
        deque.addLast(2);
        deque.addLast(3);
        deque.addLast(4);

        int i = (int) deque.removeLast();

        assertEquals(4, deque.size());
        assertEquals(4, i);
        assertEquals(4, deque.last);
        assertNull(emptyDeque.removeLast());
    }


    @Test
    public void testGet(){
        ArrayDeque deque = new ArrayDeque();

        deque.addFirst(0);
        deque.addLast(1);
        deque.addLast(2);
        deque.addLast(3);
        deque.addLast(4);

        assertNull(deque.get(7));
        assertEquals(3, deque.get(3));
    }


    @Test
    public void testArrayDequeConstructor(){
        ArrayDeque<Integer> deque = new ArrayDeque<Integer>();

        assertEquals(0, deque.size);
    }
}
