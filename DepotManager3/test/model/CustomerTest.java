package model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {

    @Test
    void constructorAcceptsValidData() {
        Customer c = new Customer(1, "John Brown", "C101");
        assertEquals(1, c.getSequenceNumber());
        assertEquals("John Brown", c.getName());
        assertEquals("C101", c.getParcelId());
    }

    @Test
    void constructorAcceptsZeroSequenceNumber() {
        Customer c = new Customer(0, "A", "C101");
        assertEquals(0, c.getSequenceNumber());
    }

    @Test
    void constructorRejectsNegativeSequenceNumber() {
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                new Customer(-1, "A", "C101");
            }
        });
    }

    @Test
    void constructorRejectsNullName() {
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                new Customer(1, null, "C101");
            }
        });
    }

    @Test
    void constructorRejectsBlankName() {
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                new Customer(1, "   ", "C101");
            }
        });
    }

    @Test
    void constructorRejectsNullParcelId() {
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                new Customer(1, "A", null);
            }
        });
    }

    @Test
    void constructorRejectsBlankParcelId() {
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                new Customer(1, "A", " ");
            }
        });
    }

    @Test
    void equalsIsTrueForSameValues() {
        Customer a = new Customer(1, "A", "C101");
        Customer b = new Customer(1, "A", "C101");
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void equalsIsFalseForDifferentValues() {
        Customer a = new Customer(1, "A", "C101");
        Customer b = new Customer(2, "A", "C101");
        assertNotEquals(a, b);
    }

    @Test
    void equalsIsFalseWhenOnlyNameDiffers() {
        Customer a = new Customer(1, "A", "C101");
        Customer b = new Customer(1, "B", "C101");
        assertNotEquals(a, b);
    }

    @Test
    void equalsIsFalseWhenOnlyParcelIdDiffers() {
        Customer a = new Customer(1, "A", "C101");
        Customer b = new Customer(1, "A", "C200");
        assertNotEquals(a, b);
    }

    @Test
    void equalsIsTrueForSameReference() {
        Customer a = new Customer(1, "A", "C101");
        assertEquals(a, a);
    }

    @Test
    void equalsIsFalseForNullAndDifferentType() {
        Customer a = new Customer(1, "A", "C101");
        assertNotEquals(a, null);
        assertNotEquals(a, "A");
    }

    @Test
    void toStringContainsKeyFields() {
        Customer c = new Customer(1, "John Brown", "C101");
        String s = c.toString();
        assertTrue(s.contains("John Brown"));
        assertTrue(s.contains("C101"));
    }
}
