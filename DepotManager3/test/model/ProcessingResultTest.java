package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProcessingResultTest {

    @Test
    void successFactoryPopulatesAllFields() throws InvalidParcelDataException {
        Customer c = new Customer(1, "John Brown", "C101");
        Parcel p = new Parcel("C101", 10, 2, 5, 2, 2);
        ProcessingResult result = ProcessingResult.success(c, p, 7.4);

        assertTrue(result.isSuccess());
        assertEquals(c, result.getCustomer());
        assertEquals(p, result.getParcel());
        assertEquals(7.4, result.getFee(), 0.0001);
        assertTrue(result.getMessage().contains("C101"));
        assertTrue(result.getMessage().contains("John Brown"));
    }

    @Test
    void failureFactoryPopulatesMessageOnly() {
        ProcessingResult result = ProcessingResult.failure("No customer in queue to process");

        assertFalse(result.isSuccess());
        assertNull(result.getCustomer());
        assertNull(result.getParcel());
        assertEquals(0.0, result.getFee(), 0.0001);
        assertEquals("No customer in queue to process", result.getMessage());
    }

    @Test
    void toStringReturnsMessage() {
        ProcessingResult result = ProcessingResult.failure("some message");
        assertEquals("some message", result.toString());
    }
}
