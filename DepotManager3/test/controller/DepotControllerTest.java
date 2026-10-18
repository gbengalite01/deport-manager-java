package controller;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DepotControllerTest {

    private ParcelMap parcelMap;
    private CustomerQueue customerQueue;
    private DepotController controller;

    @BeforeEach
    void setUp() {
        Log.getInstance().clear();
        parcelMap = new ParcelMap();
        customerQueue = new CustomerQueue();
        controller = new DepotController(parcelMap, customerQueue, new Worker("Alice"));
    }

    @Test
    void constructorRejectsNullArguments() {
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                new DepotController(null, customerQueue, new Worker("Alice"));
            }
        });
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                new DepotController(parcelMap, null, new Worker("Alice"));
            }
        });
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                new DepotController(parcelMap, customerQueue, null);
            }
        });
    }

    @Test
    void addParcelAddsToParcelMap() throws InvalidParcelDataException {
        controller.addParcel(new Parcel("C101", 10, 2, 5, 2, 2));
        assertEquals(1, parcelMap.size());
    }

    @Test
    void addCustomerToQueueAddsToQueue() {
        controller.addCustomerToQueue(new Customer(1, "A", "C101"));
        assertEquals(1, customerQueue.size());
    }

    @Test
    void processNextCustomerDelegatesToWorker() throws InvalidParcelDataException {
        controller.addParcel(new Parcel("C101", 10, 2, 5, 2, 2));
        controller.addCustomerToQueue(new Customer(1, "John Brown", "C101"));

        ProcessingResult result = controller.processNextCustomer();

        assertTrue(result.isSuccess());
        assertEquals(7.4, result.getFee(), 0.0001);
    }

    @Test
    void listenersAreNotifiedOnModelChanges() throws InvalidParcelDataException {
        final int[] count = {0};
        controller.addListener(new DepotController.DepotListener() {
            @Override
            public void onDepotUpdated() {
                count[0]++;
            }
        });

        controller.addParcel(new Parcel("C101", 10, 2, 5, 2, 2));
        controller.addCustomerToQueue(new Customer(1, "A", "C101"));
        controller.processNextCustomer();

        assertEquals(3, count[0]);
    }

    @Test
    void removeListenerStopsFurtherNotifications() throws InvalidParcelDataException {
        final int[] count = {0};
        DepotController.DepotListener listener = new DepotController.DepotListener() {
            @Override
            public void onDepotUpdated() {
                count[0]++;
            }
        };
        controller.addListener(listener);

        controller.addParcel(new Parcel("C101", 10, 2, 5, 2, 2));
        controller.removeListener(listener);
        controller.addParcel(new Parcel("C200", 8, 6, 2, 2, 5));

        assertEquals(1, count[0]);
    }

    @Test
    void addListenerIgnoresNull() {
        assertDoesNotThrow(new Executable() {
            @Override
            public void execute() throws Throwable {
                controller.addListener(null);
            }
        });
    }

    @Test
    void getParcelMapAndGetCustomerQueueReturnSameInstances() {
        assertSame(parcelMap, controller.getParcelMap());
        assertSame(customerQueue, controller.getCustomerQueue());
    }

    @Test
    void loadParcelsFromFilePopulatesParcelMap() throws IOException, InvalidParcelDataException {
        Path tmp = Files.createTempFile("parcels", ".txt");
        Files.write(tmp, List.of("C101,10,2,5,2,2", "C200,8,6,2,2,5"));

        controller.loadParcelsFromFile(tmp.toString());
        Files.deleteIfExists(tmp);

        assertEquals(2, parcelMap.size());
    }

    @Test
    void loadCustomersFromFilePopulatesQueue() throws IOException {
        Path tmp = Files.createTempFile("customers", ".txt");
        Files.write(tmp, List.of("1,John Brown,C101", "2,Mary Smith,C200"));

        controller.loadCustomersFromFile(tmp.toString());
        Files.deleteIfExists(tmp);

        assertEquals(2, customerQueue.size());
    }

    @Test
    void endDayWritesReportAndLogFiles() throws IOException, InvalidParcelDataException {
        controller.addParcel(new Parcel("C101", 10, 2, 5, 2, 2));
        String reportPath = "test_controller_report.txt";
        String logPath = "test_controller_log.txt";

        controller.endDay(reportPath, logPath);

        assertTrue(Files.exists(Paths.get(reportPath)));
        assertTrue(Files.exists(Paths.get(logPath)));

        Files.deleteIfExists(Paths.get(reportPath));
        Files.deleteIfExists(Paths.get(logPath));
    }
}
