//Testing full implementation of LinkedList Test
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class FullLinkedListTest {

    private LinkedList list;

    @BeforeEach
    public void setUp() {
        list = new LinkedList();
    }

    // ========== TASK 1 TESTS: Basic LinkedList Methods ==========

    @Test
    void name() {
    }

    @Test
    public void testConstructor() {
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
    }

    @Test
    public void testAddInFrontSingleElement() {
        list.addInFront("Hello");

        assertEquals(1, list.size());
        assertFalse(list.isEmpty());
        assertTrue(list.contains("Hello"));
    }

    @Test
    public void testAddInFrontMultipleElements() {
        list.addInFront("Third");
        list.addInFront("Second");
        list.addInFront("First");

        assertEquals(3, list.size());
        assertEquals("First", list.get(0));
        assertEquals("Second", list.get(1));
        assertEquals("Third", list.get(2));
    }

    @Test
    public void testAddInFrontWithIntegers() {
        list.addInFront(Integer.valueOf(30));
        list.addInFront(Integer.valueOf(20));
        list.addInFront(Integer.valueOf(10));

        assertEquals(3, list.size());
        assertEquals(Integer.valueOf(10), list.get(0));
        assertEquals(Integer.valueOf(20), list.get(1));
        assertEquals(Integer.valueOf(30), list.get(2));
    }

    @Test
    public void testAddInFrontOrderingWithStrings() {
        list.addInFront("C");
        list.addInFront("B");
        list.addInFront("A");

        // First element should be "A" (last one added to front)
        assertEquals("A", list.get(0));
        assertEquals("B", list.get(1));
        assertEquals("C", list.get(2));
    }

    @Test
    public void testSizeIncrementsCorrectly() {
        assertEquals(0, list.size());

        list.addInFront("First");
        assertEquals(1, list.size());

        list.addInFront("Second");
        assertEquals(2, list.size());

        list.addInFront("Third");
        assertEquals(3, list.size());
    }

    @Test
    public void testIsEmptyAfterAddingElements() {
        assertTrue(list.isEmpty());

        list.addInFront("Item");
        assertFalse(list.isEmpty());
    }

    // ========== TASK 2 TESTS: Search and Remove ==========

    @Test
    public void testContainsWithExistingElement() {
        list.addInFront("Apple");
        list.addInFront("Banana");
        list.addInFront("Cherry");

        assertTrue(list.contains("Apple"));
        assertTrue(list.contains("Banana"));
        assertTrue(list.contains("Cherry"));
    }

    @Test
    public void testContainsWithNonExistingElement() {
        list.addInFront("Apple");
        list.addInFront("Banana");

        assertFalse(list.contains("Orange"));
        assertFalse(list.contains("Grape"));
    }

    @Test
    public void testGetIndexTooLarge() {
        list.addAtEnd("Only");
        assertThrows(IndexOutOfBoundsException.class,() -> list.get(999),
                "Expected get to throw, but it didn't"
        );


    }

    @Test
    public void testGetNegativeIndex() {
        list.addAtEnd("Only");
        assertThrows(IndexOutOfBoundsException.class,() -> list.get(-1),
                "Expected get to throw, but it didn't"
        );
    }

    @Test()
    public void testGetFromEmptyList() {
        assertThrows(IndexOutOfBoundsException.class,() -> list.get(0),
                "Expected get to throw, but it didn't"
        );
    }

    @Test
    public void testContainsInEmptyList() {
        assertFalse(list.contains("Anything"));
        assertFalse(list.contains(Integer.valueOf(5)));
    }

    @Test
    public void testContainsWithIntegers() {
        list.addInFront(Integer.valueOf(100));
        list.addInFront(Integer.valueOf(200));
        list.addInFront(Integer.valueOf(300));

        assertTrue(list.contains(Integer.valueOf(100)));
        assertTrue(list.contains(Integer.valueOf(200)));
        assertTrue(list.contains(Integer.valueOf(300)));
        assertFalse(list.contains(Integer.valueOf(400)));
    }

    @Test
    public void testRemoveFromHead() {
        list.addInFront("C");
        list.addInFront("B");
        list.addInFront("A");

        assertTrue(list.remove("A"));

        assertEquals(2, list.size());
        assertFalse(list.contains("A"));
        assertTrue(list.contains("B"));
        assertTrue(list.contains("C"));
        assertEquals("B", list.get(0));
    }

    @Test
    public void testRemoveFromMiddle() {
        list.addInFront("C");
        list.addInFront("B");
        list.addInFront("A");

        assertTrue(list.remove("B"));

        assertEquals(2, list.size());
        assertTrue(list.contains("A"));
        assertFalse(list.contains("B"));
        assertTrue(list.contains("C"));
        assertEquals("A", list.get(0));
        assertEquals("C", list.get(1));
    }

    @Test
    public void testRemoveFromEnd() {
        list.addInFront("C");
        list.addInFront("B");
        list.addInFront("A");

        assertTrue(list.remove("C"));

        assertEquals(2, list.size());
        assertTrue(list.contains("A"));
        assertTrue(list.contains("B"));
        assertFalse(list.contains("C"));
    }

    @Test
    public void testRemoveNonExistentElement() {
        list.addInFront("A");
        list.addInFront("B");

        assertFalse(list.remove("Z"));
        assertEquals(2, list.size());
    }

    @Test
    public void testRemoveFromEmptyList() {
        assertFalse(list.remove("Anything"));
        assertEquals(0, list.size());
    }

    @Test
    public void testRemoveSingleElement() {
        list.addInFront("Only");

        assertTrue(list.remove("Only"));

        assertEquals(0, list.size());
        assertTrue(list.isEmpty());
    }

    @Test
    public void testRemoveReducesSize() {
        list.addInFront("C");
        list.addInFront("B");
        list.addInFront("A");

        assertEquals(3, list.size());
        list.remove("A");
        assertEquals(2, list.size());
        list.remove("B");
        assertEquals(1, list.size());
        list.remove("C");
        assertEquals(0, list.size());
    }

    @Test
    public void testRemoveWithIntegers() {
        list.addInFront(Integer.valueOf(100));
        list.addInFront(Integer.valueOf(200));
        list.addInFront(Integer.valueOf(300));

        assertTrue(list.remove(Integer.valueOf(200)));
        assertEquals(2, list.size());
        assertFalse(list.contains(Integer.valueOf(200)));
    }

    // ========== TASK 3 TESTS: Additional Functionality ==========

    @Test
    public void testAddAtEndToEmptyList() {
        list.addAtEnd("First");

        assertEquals(1, list.size());
        assertTrue(list.contains("First"));
        assertEquals("First", list.get(0));
    }

    @Test
    public void testAddAtEndMultipleElements() {
        list.addAtEnd("First");
        list.addAtEnd("Second");
        list.addAtEnd("Third");

        assertEquals(3, list.size());
        assertEquals("First", list.get(0));
        assertEquals("Second", list.get(1));
        assertEquals("Third", list.get(2));
    }

    @Test
    public void testAddAtEndWithIntegers() {
        list.addAtEnd(Integer.valueOf(10));
        list.addAtEnd(Integer.valueOf(20));
        list.addAtEnd(Integer.valueOf(30));

        assertEquals(3, list.size());
        assertEquals(Integer.valueOf(10), list.get(0));
        assertEquals(Integer.valueOf(20), list.get(1));
        assertEquals(Integer.valueOf(30), list.get(2));
    }

    @Test
    public void testAddAtEndMaintainsOrder() {
        list.addAtEnd("A");
        list.addAtEnd("B");
        list.addAtEnd("C");
        list.addAtEnd("D");

        for (int i = 0; i < list.size(); i++) {
            assertEquals(String.valueOf((char)('A' + i)), list.get(i));
        }
    }

    @Test
    public void testGetValidIndices() {
        list.addAtEnd("Zero");
        list.addAtEnd("One");
        list.addAtEnd("Two");
        list.addAtEnd("Three");

        assertEquals("Zero", list.get(0));
        assertEquals("One", list.get(1));
        assertEquals("Two", list.get(2));
        assertEquals("Three", list.get(3));
    }


    @Test
    public void testClearEmptyList() {
        list.clear();

        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
    }

    @Test
    public void testClearNonEmptyList() {
        list.addInFront("A");
        list.addInFront("B");
        list.addInFront("C");

        assertEquals(3, list.size());

        list.clear();

        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
    }

    @Test
    public void testClearRemovesAllReferences() {
        list.addAtEnd("A");
        list.addAtEnd("B");
        list.addAtEnd("C");

        list.clear();

        assertFalse(list.contains("A"));
        assertFalse(list.contains("B"));
        assertFalse(list.contains("C"));
    }

    @Test
    public void testClearAndReAdd() {
        list.addInFront("Old");
        list.clear();

        list.addInFront("New");

        assertEquals(1, list.size());
        assertTrue(list.contains("New"));
        assertFalse(list.contains("Old"));
    }

    @Test
    public void testMixedAddInFrontAndAddAtEnd() {
        list.addInFront("B");
        list.addAtEnd("C");
        list.addInFront("A");
        list.addAtEnd("D");

        assertEquals(4, list.size());
        assertEquals("A", list.get(0));
        assertEquals("B", list.get(1));
        assertEquals("C", list.get(2));
        assertEquals("D", list.get(3));
    }

    @Test
    public void testComplexSequenceOfOperations() {
        list.addInFront("B");
        list.addAtEnd("C");
        list.addInFront("A");

        assertTrue(list.contains("A"));
        assertTrue(list.contains("B"));
        assertTrue(list.contains("C"));

        list.remove("B");

        assertEquals(2, list.size());
        assertFalse(list.contains("B"));

        list.clear();

        assertTrue(list.isEmpty());
    }
}