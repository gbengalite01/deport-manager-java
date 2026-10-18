package util;

import model.InvalidParcelDataException;
import model.Customer;
import model.Parcel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DataLoaderTest {

    @Test
    void loadParcelsParsesValidFile() throws IOException, InvalidParcelDataException {
        Path tmp = Files.createTempFile("parcels", ".txt");
        Files.write(tmp, List.of("# a comment line", "", "C101,10,2,5,2,2", "X59,5,4,1,2,1"));

        List<Parcel> loaded = DataLoader.loadParcels(tmp.toString());
        Files.deleteIfExists(tmp);

        assertEquals(2, loaded.size());
        assertEquals("C101", loaded.get(0).getId());
        assertEquals("X59", loaded.get(1).getId());
    }

    @Test
    void loadParcelsSkipsBlankAndCommentLines() throws IOException, InvalidParcelDataException {
        Path tmp = Files.createTempFile("parcels", ".txt");
        Files.write(tmp, List.of("", "   ", "# comment", "C101,10,2,5,2,2"));

        List<Parcel> loaded = DataLoader.loadParcels(tmp.toString());
        Files.deleteIfExists(tmp);

        assertEquals(1, loaded.size());
    }

    @Test
    void loadParcelsThrowsOnWrongFieldCount() throws IOException {
        Path tmp = Files.createTempFile("parcels", ".txt");
        Files.write(tmp, List.of("C101,10,2"));

        assertThrows(InvalidParcelDataException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                DataLoader.loadParcels(tmp.toString());
            }
        });
        Files.deleteIfExists(tmp);
    }

    @Test
    void loadParcelsThrowsOnInvalidParcelData() throws IOException {
        Path tmp = Files.createTempFile("parcels", ".txt");
        Files.write(tmp, List.of("ZZ1,10,2,5,2,2"));

        assertThrows(InvalidParcelDataException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                DataLoader.loadParcels(tmp.toString());
            }
        });
        Files.deleteIfExists(tmp);
    }

    @Test
    void loadParcelsThrowsIOExceptionForMissingFile() {
        assertThrows(IOException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                DataLoader.loadParcels("does_not_exist_12345.txt");
            }
        });
    }

    @Test
    void loadCustomersParsesValidFile() throws IOException {
        Path tmp = Files.createTempFile("customers", ".txt");
        Files.write(tmp, List.of("1,John Brown,C101", "2,Mary Smith,C200"));

        List<Customer> loaded = DataLoader.loadCustomers(tmp.toString());
        Files.deleteIfExists(tmp);

        assertEquals(2, loaded.size());
        assertEquals("John Brown", loaded.get(0).getName());
        assertEquals("Mary Smith", loaded.get(1).getName());
    }

    @Test
    void loadCustomersSkipsBlankAndCommentLines() throws IOException {
        Path tmp = Files.createTempFile("customers", ".txt");
        Files.write(tmp, List.of("", "# comment", "1,John Brown,C101"));

        List<Customer> loaded = DataLoader.loadCustomers(tmp.toString());
        Files.deleteIfExists(tmp);

        assertEquals(1, loaded.size());
    }

    @Test
    void loadCustomersThrowsOnMalformedLine() throws IOException {
        Path tmp = Files.createTempFile("customers", ".txt");
        Files.write(tmp, List.of("not,enough"));

        assertThrows(IOException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                DataLoader.loadCustomers(tmp.toString());
            }
        });
        Files.deleteIfExists(tmp);
    }

    @Test
    void loadCustomersThrowsIOExceptionForMissingFile() {
        assertThrows(IOException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                DataLoader.loadCustomers("does_not_exist_12345.txt");
            }
        });
    }
}
