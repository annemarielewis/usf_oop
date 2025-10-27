import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LinkedListTest {

    private LinkedList list;

    @BeforeEach
    public void setUp() {
        list = new LinkedList();
    }


    @Test
    void testSizeOfEmptyList() {

        assertEquals(0,list.size());
    }

    @Test
    void testGetFirstElement() {
      //  LinkedList list = new LinkedList(); --> Don't need this in this unit test because we did BeforeEach above ^
        list.addAtEnd("Hello");
        list.addAtEnd("World");

        assertEquals("Hello", list.get(0), "both should say hello");
    }
    @Test
    void testFailedSize() {
        //LinkedList list = new LinkedList();
        list.addAtEnd("Hello");
        list.addAtEnd("World");
        list.remove("Hello");

        assertEquals(1, list.size(), "should be 1");
    }
    @Test
    public void testFindElement() {
      //  LinkedList list = new LinkedList();

        list.addAtEnd("Hello");
        list.addAtEnd("World");

        //finding an elem should be true
        assertTrue(list.contains("Hello"));
        assertFalse(list.contains("Boba"));
    }

}
