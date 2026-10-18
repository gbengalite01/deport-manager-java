package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

class WorkerTest {

    @BeforeEach
    void clearLog() {
        Log.getInstance().clear();
    }

    @Test
    void constructorAcceptsValidName() {
        Worker w = new Worker("Alice");
        assertEquals("Alice", w.getName());
    }

    @Test
    void constructorRejectsNullName() {
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                new Worker(null);
            }
        });
    }

    @Test
    void constructorRejectsBlankName() {
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                new Worker("  ");
            }
        });
    }

    @Test
    void calculateFeeMatchesFormulaForNonXParcel() throws InvalidParcelDataException {
        // weight*0.5 + volume*0.1 + daysInDepot*0.2 = 10*0.5 + 20*0.1 + 2*0.2 = 5 + 2 + 0.4 = 7.4
        Parcel p = new Parcel("C101", 10, 2, 5, 2, 2);
        Worker w = new Worker("Alice");
        assertEquals(7.4, w.calculateFee(p), 0.0001);
    }

    @Test
    void calculateFeeAppliesTenPercentDiscountForXParcel() throws InvalidParcelDataException {
        // (5*0.5 + 8*0.1 + 1*0.2) = 2.5 + 0.8 + 0.2 = 3.5, then *0.9 = 3.15
        Parcel p = new Parcel("X59", 5, 4, 1, 2, 1);
        Worker w = new Worker("Alice");
        assertEquals(3.15, w.calculateFee(p), 0.0001);
    }

    @Test
    void calculateFeeRejectsNullParcel() {
        Worker w = new Worker("Alice");
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                w.calculateFee(null);
            }
        });
    }

    @Test
    void calculateFeeRoundsToTwoDecimalPlaces() throws InvalidParcelDataException {
        Parcel p = new Parcel("C101", 1, 1, 1, 1, 0);
        Worker w = new Worker("Alice");
        double fee = w.calculateFee(p);
        double rounded = Math.round(fee * 100.0) / 100.0;
        assertEquals(rounded, fee, 0.0000001);
    }

    @Test
    void processNextCustomerSucceedsAndUpdatesModel() throws InvalidParcelDataException {
        ParcelMap pm = new ParcelMap();
        pm.addParcel(new Parcel("C101", 10, 2, 5, 2, 2));
        CustomerQueue q = new CustomerQueue();
        q.enqueue(new Customer(1, "John Brown", "C101"));
        Worker w = new Worker("Alice");

        ProcessingResult result = w.processNextCustomer(q, pm);

        assertTrue(result.isSuccess());
        assertEquals(7.4, result.getFee(), 0.0001);
        assertEquals("John Brown", result.getCustomer().getName());
        assertEquals("C101", result.getParcel().getId());
        assertTrue(pm.getParcel("C101").isCollected());
        assertTrue(q.isEmpty());
    }

    @Test
    void processNextCustomerOnEmptyQueueReturnsFailure() {
        ParcelMap pm = new ParcelMap();
        CustomerQueue q = new CustomerQueue();
        Worker w = new Worker("Alice");

        ProcessingResult result = w.processNextCustomer(q, pm);

        assertFalse(result.isSuccess());
        assertNull(result.getCustomer());
        assertNull(result.getParcel());
    }

    @Test
    void processNextCustomerWithMissingParcelReturnsFailureAndDequeuesCustomer() {
        ParcelMap pm = new ParcelMap();
        CustomerQueue q = new CustomerQueue();
        q.enqueue(new Customer(1, "A", "C999"));
        Worker w = new Worker("Alice");

        ProcessingResult result = w.processNextCustomer(q, pm);

        assertFalse(result.isSuccess());
        assertTrue(result.getMessage().contains("not found"));
        assertTrue(q.isEmpty());
    }

    @Test
    void processNextCustomerWithAlreadyCollectedParcelReturnsFailure() throws InvalidParcelDataException {
        ParcelMap pm = new ParcelMap();
        Parcel p = new Parcel("C101", 10, 2, 5, 2, 2);
        p.setCollected(true);
        pm.addParcel(p);
        CustomerQueue q = new CustomerQueue();
        q.enqueue(new Customer(1, "A", "C101"));
        Worker w = new Worker("Alice");

        ProcessingResult result = w.processNextCustomer(q, pm);

        assertFalse(result.isSuccess());
        assertTrue(result.getMessage().contains("already collected"));
    }

    @Test
    void processNextCustomerRejectsNullQueue() {
        Worker w = new Worker("Alice");
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                w.processNextCustomer(null, new ParcelMap());
            }
        });
    }

    @Test
    void processNextCustomerRejectsNullParcelMap() {
        Worker w = new Worker("Alice");
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                w.processNextCustomer(new CustomerQueue(), null);
            }
        });
    }
}
