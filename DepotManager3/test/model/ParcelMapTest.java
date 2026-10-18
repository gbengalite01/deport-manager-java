package model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ParcelMapTest {

    @Test
    void addAndGetParcel() throws InvalidParcelDataException {
        ParcelMap pm = new ParcelMap();
        pm.addParcel(new Parcel("C101", 10, 2, 5, 2, 2));
        assertEquals("C101", pm.getParcel("C101").getId());
    }

    @Test
    void getParcelReturnsNullWhenAbsent() {
        ParcelMap pm = new ParcelMap();
        assertNull(pm.getParcel("C999"));
    }

    @Test
    void containsParcelTrueAndFalse() throws InvalidParcelDataException {
        ParcelMap pm = new ParcelMap();
        pm.addParcel(new Parcel("C101", 10, 2, 5, 2, 2));
        assertTrue(pm.containsParcel("C101"));
        assertFalse(pm.containsParcel("C999"));
    }

    @Test
    void removeParcelReturnsRemovedAndUpdatesSize() throws InvalidParcelDataException {
        ParcelMap pm = new ParcelMap();
        pm.addParcel(new Parcel("C101", 10, 2, 5, 2, 2));
        Parcel removed = pm.removeParcel("C101");
        assertNotNull(removed);
        assertEquals(0, pm.size());
        assertNull(pm.getParcel("C101"));
    }

    @Test
    void removeParcelReturnsNullWhenAbsent() {
        ParcelMap pm = new ParcelMap();
        assertNull(pm.removeParcel("C999"));
    }

    @Test
    void sizeReflectsNumberOfParcels() throws InvalidParcelDataException {
        ParcelMap pm = new ParcelMap();
        assertEquals(0, pm.size());
        pm.addParcel(new Parcel("C101", 10, 2, 5, 2, 2));
        pm.addParcel(new Parcel("C200", 8, 6, 2, 2, 5));
        assertEquals(2, pm.size());
    }

    @Test
    void getAllParcelsReturnsEveryParcel() throws InvalidParcelDataException {
        ParcelMap pm = new ParcelMap();
        pm.addParcel(new Parcel("C101", 10, 2, 5, 2, 2));
        pm.addParcel(new Parcel("C200", 8, 6, 2, 2, 5));
        assertEquals(2, pm.getAllParcels().size());
    }

    @Test
    void getAllParcelsIsUnmodifiable() throws InvalidParcelDataException {
        ParcelMap pm = new ParcelMap();
        pm.addParcel(new Parcel("C101", 10, 2, 5, 2, 2));
        List<Parcel> all = pm.getAllParcels();
        assertThrows(UnsupportedOperationException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                all.add(null);
            }
        });
    }

    @Test
    void getUncollectedParcelsExcludesCollected() throws InvalidParcelDataException {
        ParcelMap pm = new ParcelMap();
        Parcel a = new Parcel("C101", 10, 2, 5, 2, 2);
        Parcel b = new Parcel("C200", 8, 6, 2, 2, 5);
        b.setCollected(true);
        pm.addParcel(a);
        pm.addParcel(b);
        List<Parcel> uncollected = pm.getUncollectedParcels();
        assertEquals(1, uncollected.size());
        assertEquals("C101", uncollected.get(0).getId());
    }

    @Test
    void getUncollectedSortedByDaysInDepotOrdersDescending() throws InvalidParcelDataException {
        ParcelMap pm = new ParcelMap();
        pm.addParcel(new Parcel("C101", 10, 2, 5, 2, 2));
        pm.addParcel(new Parcel("C105", 32, 3, 3, 3, 10));
        pm.addParcel(new Parcel("X59", 5, 4, 1, 2, 1));
        List<Parcel> sorted = pm.getUncollectedSortedByDaysInDepot();
        assertEquals("C105", sorted.get(0).getId());
        assertEquals("C101", sorted.get(1).getId());
        assertEquals("X59", sorted.get(2).getId());
    }

    @Test
    void getUncollectedSortedByDaysInDepotHandlesEmptyList() {
        ParcelMap pm = new ParcelMap();
        List<Parcel> sorted = pm.getUncollectedSortedByDaysInDepot();
        assertTrue(sorted.isEmpty());
    }

    @Test
    void getUncollectedSortedByDaysInDepotHandlesSingleElement() throws InvalidParcelDataException {
        ParcelMap pm = new ParcelMap();
        pm.addParcel(new Parcel("C101", 10, 2, 5, 2, 2));
        List<Parcel> sorted = pm.getUncollectedSortedByDaysInDepot();
        assertEquals(1, sorted.size());
        assertEquals("C101", sorted.get(0).getId());
    }

    @Test
    void getUncollectedSortedByDaysInDepotHandlesAlreadyOrderedPair() throws InvalidParcelDataException {
        // Exercises the case where the while-loop condition is false on
        // its very first check (the incoming element already belongs
        // where it is), not just the case where a shift happens.
        ParcelMap pm = new ParcelMap();
        pm.addParcel(new Parcel("C105", 32, 3, 3, 3, 10));
        pm.addParcel(new Parcel("X59", 5, 4, 1, 2, 1));
        List<Parcel> sorted = pm.getUncollectedSortedByDaysInDepot();
        assertEquals("C105", sorted.get(0).getId());
        assertEquals("X59", sorted.get(1).getId());
    }

    @Test
    void addParcelRejectsNull() {
        ParcelMap pm = new ParcelMap();
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                pm.addParcel(null);
            }
        });
    }

    @Test
    void addParcelWithSameIdOverwritesPrevious() throws InvalidParcelDataException {
        ParcelMap pm = new ParcelMap();
        pm.addParcel(new Parcel("C101", 10, 2, 5, 2, 2));
        pm.addParcel(new Parcel("C101", 99, 1, 1, 1, 1));
        assertEquals(1, pm.size());
        assertEquals(99, pm.getParcel("C101").getWeight());
    }
}
