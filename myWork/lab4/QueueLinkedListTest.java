// Testing QueueLinkedList Implementation
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class QueueLinkedListTest {

    private QueueLinkedList queue;

    @BeforeEach
    public void setUp() {
        queue = new QueueLinkedList();
    }

    // Task 5 testing for Queue Implementation 

    @Test
    public void testEnqueueSingleElement() {
        queue.enqueue("Item");

        assertEquals(1, queue.size());
        assertFalse(queue.isEmpty());
    }

    @Test
    public void testEnqueueMultipleElements() {
        queue.enqueue("First");
        queue.enqueue("Second");
        queue.enqueue("Third");

        assertEquals(3, queue.size());
        assertFalse(queue.isEmpty());
    }

    @Test
    public void testEnqueueWithIntegers() {
        queue.enqueue(Integer.valueOf(10));
        queue.enqueue(Integer.valueOf(20));
        queue.enqueue(Integer.valueOf(30));

        assertEquals(3, queue.size());
    }

    @Test
    public void testDequeueSingleElement() {
        queue.enqueue("Only");

        Object dequeued = queue.dequeue();

        assertEquals("Only", dequeued);
        assertEquals(0, queue.size());
        assertTrue(queue.isEmpty());
    }

    @Test
    public void testDequeueFIFOOrder() {
        queue.enqueue(Integer.valueOf(1));
        queue.enqueue(Integer.valueOf(2));
        queue.enqueue(Integer.valueOf(3));

        // First In, First Out - should dequeue in same order
        assertEquals(Integer.valueOf(1), queue.dequeue());
        assertEquals(Integer.valueOf(2), queue.dequeue());
        assertEquals(Integer.valueOf(3), queue.dequeue());
    }

    @Test
    public void testDequeueFIFOOrderWithStrings() {
        queue.enqueue("First");
        queue.enqueue("Second");
        queue.enqueue("Third");

        assertEquals("First", queue.dequeue());
        assertEquals("Second", queue.dequeue());
        assertEquals("Third", queue.dequeue());
    }

    @Test
    public void testDequeueReducesSize() {
        queue.enqueue("A");
        queue.enqueue("B");
        queue.enqueue("C");

        assertEquals(3, queue.size());
        queue.dequeue();
        assertEquals(2, queue.size());
        queue.dequeue();
        assertEquals(1, queue.size());
        queue.dequeue();
        assertEquals(0, queue.size());
    }

    @Test
    public void testDequeueFromEmptyQueueThrowsException() {
        assertThrows(RuntimeException.class,() -> queue.dequeue(),
                "Expected front() to throw, but it didn't"
        );

    }

    @Test
    public void testFrontWithoutRemoving() {
        queue.enqueue("Item");

        Object front = queue.front();

        assertEquals("Item", front);
        assertEquals(1, queue.size());  // Size should not change
    }

    @Test
    public void testFrontMultipleTimes() {
        queue.enqueue("Item");

        assertEquals("Item", queue.front());
        assertEquals("Item", queue.front());
        assertEquals("Item", queue.front());

        assertEquals(1, queue.size());  // Size still 1
    }

    @Test
    public void testFrontAfterMultipleEnqueues() {
        queue.enqueue("First");
        queue.enqueue("Second");
        queue.enqueue("Third");

        // Front should return the front element (First)
        assertEquals("First", queue.front());
        assertEquals(3, queue.size());
    }

    @Test
    public void testFrontFromEmptyQueueThrowsException() {
        assertThrows(RuntimeException.class,() -> queue.front(),
                "Expected front() to throw, but it didn't"
        );

    }

    @Test
    public void testEnqueueDequeueSequence() {
        queue.enqueue("A");
        queue.enqueue("B");
        assertEquals("A", queue.dequeue());

        queue.enqueue("C");
        queue.enqueue("D");

        assertEquals("B", queue.dequeue());
        assertEquals("C", queue.dequeue());
        assertEquals("D", queue.dequeue());
    }

    @Test
    public void testEnqueueFrontDequeueCombination() {
        queue.enqueue("A");
        assertEquals("A", queue.front());

        queue.enqueue("B");
        assertEquals("A", queue.front());

        assertEquals("A", queue.dequeue());
        assertEquals("B", queue.front());

        queue.enqueue("C");
        assertEquals("B", queue.front());

        assertEquals("B", queue.dequeue());
        assertEquals("C", queue.dequeue());

        assertTrue(queue.isEmpty());
    }

    @Test
    public void testQueueWithMixedTypes() {
        queue.enqueue("String");
        queue.enqueue(Integer.valueOf(42));
        queue.enqueue(3.14);

        assertEquals(3, queue.size());

        queue.dequeue();  // "String"
        queue.dequeue();  // 42
        queue.dequeue();  // 3.14

        assertTrue(queue.isEmpty());
    }

    @Test
    public void testQueueAfterClear() {
        queue.enqueue("A");
        queue.enqueue("B");
        queue.enqueue("C");

        queue.clear();

        assertTrue(queue.isEmpty());

        queue.enqueue("New");
        assertEquals("New", queue.front());
    }

    @Test
    public void testQueueJobProcessingSimulation() {
        // Simulate job processing (first job in is first to be processed)
        queue.enqueue("Job1");
        queue.enqueue("Job2");
        queue.enqueue("Job3");

        assertEquals("Job1", queue.dequeue());  // Process first job
        assertEquals("Job2", queue.dequeue());  // Process second job
        assertEquals("Job3", queue.dequeue());  // Process third job
    }

    @Test
    public void testQueueVsStackBehavior() {
        // This test demonstrates the difference between Queue (FIFO) and Stack (LIFO)

        // With Queue: First in, first out
        QueueLinkedList q = new QueueLinkedList();
        q.enqueue(1);
        q.enqueue(2);
        q.enqueue(3);

        assertEquals(1, q.dequeue());  // First item added, first removed
        assertEquals(2, q.dequeue());
        assertEquals(3, q.dequeue());
    }
}