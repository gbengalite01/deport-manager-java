/*
 * complete every method and constructor marked TODO.
 * Do NOT change the package, the class name, or any method or constructor
 * name, parameter list or return type. You may add private fields and
 * private helper methods.
 */
package util;

import model.InvalidParcelDataException;
import model.Customer;
import model.Parcel;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Reads Parcel and Customer data from plain-text, comma-separated files.
 *
 * Expected parcel file format, one parcel per line:
 *   id,weight,length,width,height,daysInDepot
 *   e.g. C101,10,2,5,2,2
 *
 * Expected customer file format, one customer per line:
 *   sequenceNumber,name,parcelId
 *   e.g. 1,John Brown,C101
 *
 * Blank lines and lines starting with '#' are ignored in both files.
 */
public final class DataLoader {

    private DataLoader() {
        // utility class: no instances
    }

    /**
     * Reads parcels from a text file in the format described above. Leading
     * and trailing spaces around each line and each field are ignored.
     *
     * @param filePath path of the parcel data file
     * @return the parcels, in file order
     * @throws IOException                if the file cannot be read
     * @throws InvalidParcelDataException if a line does not have exactly six
     *                                    fields, or holds invalid parcel data
     */
    public static List<Parcel> loadParcels(String filePath) throws IOException, InvalidParcelDataException {
        List<Parcel> parcels = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                // Ignore blank lines and comment lines starting with '#'
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }

                String[] parts = line.split(",");
                if (parts.length != 6) {
                    throw new InvalidParcelDataException("Invalid parcel line format (expected 6 fields): " + line);
                }

                try {
                    String id = parts[0].trim();
                    double weight = Double.parseDouble(parts[1].trim());
                    double length = Double.parseDouble(parts[2].trim());
                    double width = Double.parseDouble(parts[3].trim());
                    double height = Double.parseDouble(parts[4].trim());
                    int daysInDepot = Integer.parseInt(parts[5].trim());

                    Parcel parcel = new Parcel(id, weight, length, width, height, daysInDepot);
                    parcels.add(parcel);
                } catch (NumberFormatException e) {
                    throw new InvalidParcelDataException("Invalid number format in parcel line: " + line);
                }
            }
        }

        return parcels;
    }

    /**
     * Reads customers from a text file in the format described above. Leading
     * and trailing spaces around each line and each field are ignored.
     *
     * @param filePath path of the customer data file
     * @return the customers, in file order
     * @throws IOException if the file cannot be read or a line does not have
     *                     three fields
     */
    public static List<Customer> loadCustomers(String filePath) throws IOException, NumberFormatException {
        List<Customer> customers = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                // Ignore blank lines and comment lines starting with '#'
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }

                String[] parts = line.split(",");
                if (parts.length != 3) {
                    throw new IOException("Invalid customer line format (expected 3 fields): " + line);
                }

                try {
                    int sequenceNumber = Integer.parseInt(parts[0].trim());
                    String name = parts[1].trim();
                    String parcelId = parts[2].trim();

                    Customer customer = new Customer(sequenceNumber, name, parcelId);
                    customers.add(customer);
                } catch (IllegalArgumentException e) {
                    throw new IOException("Invalid data in customer line: " + line, e);
                }
            }
        }

        return customers;
    }
}
