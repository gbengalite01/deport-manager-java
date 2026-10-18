package model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

class ParcelTest {

    @Test
    void constructorAcceptsValidData() throws InvalidParcelDataException {
        Parcel p = new Parcel("C101", 10, 2, 5, 2, 2);
        assertEquals("C101", p.getId());
        assertEquals(10, p.getWeight());
        assertEquals(2, p.getLength());
        assertEquals(5, p.getWidth());
        assertEquals(2, p.getHeight());
        assertEquals(2, p.getDaysInDepot());
        assertFalse(p.isCollected());
    }

    @Test
    void constructorAcceptsThreeDigitXId() throws InvalidParcelDataException {
        Parcel p = new Parcel("X999", 1, 1, 1, 1, 0);
        assertEquals("X999", p.getId());
    }

    @Test
    void constructorRejectsNullId() {
        assertThrows(InvalidParcelDataException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                new Parcel(null, 1, 1, 1, 1, 1);
            }
        });
    }

    @Test
    void constructorRejectsWrongLetterPrefix() {
        assertThrows(InvalidParcelDataException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                new Parcel("Z101", 1, 1, 1, 1, 1);
            }
        });
    }

    @Test
    void constructorRejectsTooFewDigits() {
        assertThrows(InvalidParcelDataException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                new Parcel("C1", 1, 1, 1, 1, 1);
            }
        });
    }

    @Test
    void constructorRejectsTooManyDigits() {
        assertThrows(InvalidParcelDataException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                new Parcel("C1234", 1, 1, 1, 1, 1);
            }
        });
    }

    @Test
    void constructorRejectsZeroWeight() {
        assertThrows(InvalidParcelDataException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                new Parcel("C101", 0, 1, 1, 1, 1);
            }
        });
    }

    @Test
    void constructorRejectsNegativeWeight() {
        assertThrows(InvalidParcelDataException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                new Parcel("C101", -5, 1, 1, 1, 1);
            }
        });
    }

    @Test
    void constructorRejectsZeroLength() {
        assertThrows(InvalidParcelDataException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                new Parcel("C101", 1, 0, 1, 1, 1);
            }
        });
    }

    @Test
    void constructorRejectsZeroWidth() {
        assertThrows(InvalidParcelDataException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                new Parcel("C101", 1, 1, 0, 1, 1);
            }
        });
    }

    @Test
    void constructorRejectsZeroHeight() {
        assertThrows(InvalidParcelDataException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                new Parcel("C101", 1, 1, 1, 0, 1);
            }
        });
    }

    @Test
    void constructorRejectsNegativeDaysInDepot() {
        assertThrows(InvalidParcelDataException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                new Parcel("C101", 1, 1, 1, 1, -1);
            }
        });
    }

    @Test
    void constructorAcceptsZeroDaysInDepot() throws InvalidParcelDataException {
        Parcel p = new Parcel("C101", 1, 1, 1, 1, 0);
        assertEquals(0, p.getDaysInDepot());
    }

    @Test
    void setCollectedChangesState() throws InvalidParcelDataException {
        Parcel p = new Parcel("C101", 10, 2, 5, 2, 2);
        p.setCollected(true);
        assertTrue(p.isCollected());
        p.setCollected(false);
        assertFalse(p.isCollected());
    }

    @Test
    void getVolumeMultipliesDimensions() throws InvalidParcelDataException {
        Parcel p = new Parcel("C101", 10, 2, 5, 2, 2);
        assertEquals(20.0, p.getVolume(), 0.0001);
    }

    @Test
    void equalsIsTrueForSameId() throws InvalidParcelDataException {
        Parcel a = new Parcel("C101", 10, 2, 5, 2, 2);
        Parcel b = new Parcel("C101", 99, 1, 1, 1, 9);
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void equalsIsFalseForDifferentId() throws InvalidParcelDataException {
        Parcel a = new Parcel("C101", 10, 2, 5, 2, 2);
        Parcel b = new Parcel("C200", 10, 2, 5, 2, 2);
        assertNotEquals(a, b);
    }

    @Test
    void equalsIsTrueForSameReference() throws InvalidParcelDataException {
        Parcel a = new Parcel("C101", 10, 2, 5, 2, 2);
        assertEquals(a, a);
    }

    @Test
    void equalsIsFalseForNullAndDifferentType() throws InvalidParcelDataException {
        Parcel a = new Parcel("C101", 10, 2, 5, 2, 2);
        assertNotEquals(a, null);
        assertNotEquals(a, "C101");
    }

    @Test
    void toStringContainsKeyFields() throws InvalidParcelDataException {
        Parcel p = new Parcel("C101", 10, 2, 5, 2, 2);
        String s = p.toString();
        assertTrue(s.contains("C101"));
        assertTrue(s.contains("10.0"));
    }
}
