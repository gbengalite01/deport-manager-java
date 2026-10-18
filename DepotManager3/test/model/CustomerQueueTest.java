package model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CustomerQueueTest {

    @Test
    void enqueueAndDequeueFollowFifoOrder() {
        CustomerQueue q = new CustomerQueue();
        q.enqueue(new Customer(1, "A", "C101"));
        q.enqueue(new Customer(2, "B", "C200"));
        assertEquals("A", q.dequeue().getName());
        assertEquals("B", q.dequeue().getName());
    }

    @Test
    void dequeueOnEmptyQueueReturnsNull() {
        CustomerQueue q = new CustomerQueue();
        assertNull(q.dequeue());
    }

    @Test
    void peekReturnsFrontWithoutRemoving() {
        CustomerQueue q = new CustomerQueue();
        q.enqueue(new Customer(1, "A", "C101"));
        assertEquals("A", q.peek().getName());
        assertEquals(1, q.size());
    }

    @Test
    void peekOnEmptyQueueReturnsNull() {
        CustomerQueue q = new CustomerQueue();
        assertNull(q.peek());
    }

    @Test
    void isEmptyReflectsQueueState() {
        CustomerQueue q = new CustomerQueue();
        assertTrue(q.isEmpty());
        q.enqueue(new Customer(1, "A", "C101"));
        assertFalse(q.isEmpty());
    }

    @Test
    void sizeReflectsNumberOfCustomers() {
        CustomerQueue q = new CustomerQueue();
        assertEquals(0, q.size());
        q.enqueue(new Customer(1, "A", "C101"));
        q.enqueue(new Customer(2, "B", "C200"));
        assertEquals(2, q.size());
    }

    @Test
    void removeBySequenceNumberRemovesMatchingCustomer() {
        CustomerQueue q = new CustomerQueue();
        q.enqueue(new Customer(1, "A", "C101"));
        q.enqueue(new Customer(2, "B", "C200"));
        assertTrue(q.removeBySequenceNumber(1));
        assertEquals(1, q.size());
        assertEquals("B", q.peek().getName());
    }

    @Test
    void removeBySequenceNumberReturnsFalseWhenNotFound() {
        CustomerQueue q = new CustomerQueue();
        q.enqueue(new Customer(1, "A", "C101"));
        assertFalse(q.removeBySequenceNumber(999));
        assertEquals(1, q.size());
    }

    @Test
    void getQueueSnapshotPreservesOrder() {
        CustomerQueue q = new CustomerQueue();
        q.enqueue(new Customer(1, "A", "C101"));
        q.enqueue(new Customer(2, "B", "C200"));
        List<Customer> snapshot = q.getQueueSnapshot();
        assertEquals(2, snapshot.size());
        assertEquals("A", snapshot.get(0).getName());
        assertEquals("B", snapshot.get(1).getName());
    }

    @Test
    void getQueueSnapshotIsUnmodifiable() {
        CustomerQueue q = new CustomerQueue();
        q.enqueue(new Customer(1, "A", "C101"));
        List<Customer> snapshot = q.getQueueSnapshot();
        assertThrows(UnsupportedOperationException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                snapshot.add(null);
            }
        });
    }

    @Test
    void getQueueSnapshotIsIndependentOfLaterChanges() {
        CustomerQueue q = new CustomerQueue();
        q.enqueue(new Customer(1, "A", "C101"));
        List<Customer> snapshot = q.getQueueSnapshot();
        q.enqueue(new Customer(2, "B", "C200"));
        assertEquals(1, snapshot.size());
    }

    @Test
    void enqueueRejectsNull() {
        CustomerQueue q = new CustomerQueue();
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                q.enqueue(null);
            }
        });
    }
}
