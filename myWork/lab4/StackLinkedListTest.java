// Testing StackLinkedList Implementation
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class StackLinkedListTest {

    private StackLinkedList stack;

    @BeforeEach
    public void setUp() {
        stack = new StackLinkedList();
    }

    // ========== TASK 4 TESTS: Stack Implementation ==========

    @Test
    public void testPushSingleElement() {
        stack.push("Item");

        assertEquals(1, stack.size());
        assertFalse(stack.isEmpty());
    }

    @Test
    public void testPushMultipleElements() {
        stack.push("First");
        stack.push("Second");
        stack.push("Third");

        assertEquals(3, stack.size());
        assertFalse(stack.isEmpty());
    }

    @Test
    public void testPushWithIntegers() {
        stack.push(Integer.valueOf(10));
        stack.push(Integer.valueOf(20));
        stack.push(Integer.valueOf(30));

        assertEquals(3, stack.size());
    }

    @Test
    public void testPopSingleElement() {
        stack.push("Only");

        Object popped = stack.pop();

        assertEquals("Only", popped);
        assertEquals(0, stack.size());
        assertTrue(stack.isEmpty());
    }

    @Test
    public void testPopLIFOOrder() {
        stack.push(Integer.valueOf(1));
        stack.push(Integer.valueOf(2));
        stack.push(Integer.valueOf(3));

        // Last In, First Out - should pop in reverse order
        assertEquals(Integer.valueOf(3), stack.pop());
        assertEquals(Integer.valueOf(2), stack.pop());
        assertEquals(Integer.valueOf(1), stack.pop());
    }

    @Test
    public void testPopLIFOOrderWithStrings() {
        stack.push("First");
        stack.push("Second");
        stack.push("Third");

        assertEquals("Third", stack.pop());
        assertEquals("Second", stack.pop());
        assertEquals("First", stack.pop());
    }

    @Test
    public void testPopReducesSize() {
        stack.push("A");
        stack.push("B");
        stack.push("C");

        assertEquals(3, stack.size());
        stack.pop();
        assertEquals(2, stack.size());
        stack.pop();
        assertEquals(1, stack.size());
        stack.pop();
        assertEquals(0, stack.size());
    }

    @Test
    public void testPopFromEmptyStackThrowsException() {
            assertThrows(RuntimeException.class,() -> stack.pop(),
                    "Expected pop() to throw, but it didn't"
            );

    }

    @Test
    public void testPeekWithoutRemoving() {
        stack.push("Item");

        Object peeked = stack.peek();

        assertEquals("Item", peeked);
        assertEquals(1, stack.size());  // Size should not change
    }

    @Test
    public void testPeekMultipleTimes() {
        stack.push("Item");

        assertEquals("Item", stack.peek());
        assertEquals("Item", stack.peek());
        assertEquals("Item", stack.peek());

        assertEquals(1, stack.size());  // Size still 1
    }

    @Test
    public void testPeekAfterMultiplePushes() {
        stack.push("First");
        stack.push("Second");
        stack.push("Third");

        // Peek should return the top element (Third)
        assertEquals("Third", stack.peek());
        assertEquals(3, stack.size());
    }

    @Test//(expected = RuntimeException.class)
    public void testPeekFromEmptyStackThrowsException() {
         assertThrows(RuntimeException.class,() -> stack.peek(),
                 "Expected peek() to throw, but it didn't"
         );
    }

    @Test
    public void testPushPopSequence() {
        stack.push("A");
        stack.push("B");
        assertEquals("B", stack.pop());

        stack.push("C");
        stack.push("D");

        assertEquals("D", stack.pop());
        assertEquals("C", stack.pop());
        assertEquals("A", stack.pop());
    }

    @Test
    public void testPushPeekPopCombination() {
        stack.push("A");
        assertEquals("A", stack.peek());

        stack.push("B");
        assertEquals("B", stack.peek());

        assertEquals("B", stack.pop());
        assertEquals("A", stack.peek());

        stack.push("C");
        assertEquals("C", stack.peek());

        assertEquals("C", stack.pop());
        assertEquals("A", stack.pop());

        assertTrue(stack.isEmpty());
    }

    @Test
    public void testStackWithMixedTypes() {
        stack.push("String");
        stack.push(Integer.valueOf(42));
        stack.push(3.14);

        assertEquals(3, stack.size());

        stack.pop();  // 3.14
        stack.pop();  // 42
        stack.pop();  // "String"

        assertTrue(stack.isEmpty());
    }

    @Test
    public void testStackAfterClear() {
        stack.push("A");
        stack.push("B");
        stack.push("C");

        stack.clear();

        assertTrue(stack.isEmpty());

        stack.push("New");
        assertEquals("New", stack.peek());
    }
}
