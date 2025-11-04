import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BarTest {

    @Test
    public void testCompareTo_LessThan() {
        Bar bar1 = new Bar("A", 10, "Fruit", "Red");
        Bar bar2 = new Bar("B", 15, "Fruit", "Blue");

        // bar1.value < bar2.value → should return -1
        assertEquals(-1, bar1.compareTo(bar2));
    }

    @Test
    public void testCompareTo_GreaterThan() {
        Bar bar1 = new Bar("A", 20, "Fruit", "Red");
        Bar bar2 = new Bar("B", 10, "Fruit", "Blue");

        // bar1.value > bar2.value → should return 1
        assertEquals(1, bar1.compareTo(bar2));
    }

    @Test
    public void testCompareTo_EqualValues() {
        Bar bar1 = new Bar("A", 10, "Fruit", "Red");
        Bar bar2 = new Bar("B", 10, "Fruit", "Blue");

        // Equal values → should return 0
        assertEquals(0, bar1.compareTo(bar2));
    }

    @Test
    public void testCompareTo_NullOtherBar() {
        Bar bar1 = new Bar("A", 10, "Fruit", "Red");

        // Passing null → should throw NullPointerException
        assertThrows(NullPointerException.class, () -> {
            bar1.compareTo(null);
        });
    }

    @Test
    public void testCompareTo_InvalidOtherBar() {
        Bar bar1 = new Bar("A", 10, "Fruit", "Red");
        Bar invalid = new Bar(null, -5, null, "Label");

        // Invalid fields → should throw IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> {
            bar1.compareTo(invalid);
        });
    }
}
