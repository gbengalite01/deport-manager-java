/*
 * complete every method and constructor marked TODO.
 * Do NOT change the package, the class name, or any method or constructor
 * name, parameter list or return type. You may add private fields and
 * private helper methods.
 */
package util;

import model.Parcel;
import model.ParcelMap;
import model.Worker;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Writes the end-of-day report: collected parcels (and their fees, if
 * known), uncollected parcels, and summary counts/totals.
 *
 * Since the ParcelMap only tracks whether a parcel is collected (not the
 * fee it was collected for), the report recomputes each collected
 * parcel's fee afresh using {@link Worker#calculateFee}, via a
 * short-lived Worker instance, so the report and the live processing use
 * exactly the same, single formula.
 */
public final class ReportWriter {

    private ReportWriter() {
        // utility class: no instances
    }

    /**
     * Writes the end-of-day report to the given file, using UTF-8 encoding.
     * The report contains, in this order:
     *   - a heading line "=== END OF DAY REPORT ===";
     *   - a "COLLECTED PARCELS:" section listing each collected parcel's ID and
     *     fee (recalculated with Worker.calculateFee), or "(none)";
     *   - an "UNCOLLECTED PARCELS:" section listing each uncollected parcel's
     *     ID, weight, dimensions and days in depot, or "(none)";
     *   - a "SUMMARY:" section with the lines "Total parcels: [n]",
     *     "Collected: [n]", "Uncollected: [n]" and
     *     "Total fees collected: £[total]" (total rounded to 2 decimal places).
     *
     * @param filePath  path of the report file to write
     * @param parcelMap the parcels to report on
     * @throws IOException if the file cannot be written
     */
    public static void writeReport(String filePath, ParcelMap parcelMap) throws IOException {
        if (parcelMap == null) {
            throw new IllegalArgumentException("ParcelMap must not be null.");
        }

        List<Parcel> allParcels = parcelMap.getAllParcels();
        List<Parcel> collectedParcels = new ArrayList<>();
        List<Parcel> uncollectedParcels = new ArrayList<>();

        for (Parcel parcel : allParcels) {
            if (parcel.isCollected()) {
                collectedParcels.add(parcel);
            } else {
                uncollectedParcels.add(parcel);
            }
        }

        Worker worker = new Worker("ReportWorker");
        double totalFees = 0.0;

        try (PrintWriter writer = new PrintWriter(
                new OutputStreamWriter(new FileOutputStream(filePath), StandardCharsets.UTF_8))) {
            
            // Heading
            writer.println("=== END OF DAY REPORT ===");
            
            // Collected Parcels section
            writer.println("COLLECTED PARCELS:");
            if (collectedParcels.isEmpty()) {
                writer.println("(none)");
            } else {
                for (Parcel parcel : collectedParcels) {
                    double fee = worker.calculateFee(parcel);
                    totalFees += fee;
                    writer.println(String.format("%s - £%.2f", parcel.getId(), fee));
                }
            }

            // Uncollected Parcels section
            writer.println("UNCOLLECTED PARCELS:");
            if (uncollectedParcels.isEmpty()) {
                writer.println("(none)");
            } else {
                for (Parcel parcel : uncollectedParcels) {
                    writer.println(String.format("%s, weight: %.2f, dimensions: %.2fx%.2fx%.2f, days in depot: %d",
                            parcel.getId(),
                            parcel.getWeight(),
                            parcel.getLength(),
                            parcel.getWidth(),
                            parcel.getHeight(),
                            parcel.getDaysInDepot()));
                }
            }

            // Summary section
            writer.println("SUMMARY:");
            writer.println("Total parcels: " + allParcels.size());
            writer.println("Collected: " + collectedParcels.size());
            writer.println("Uncollected: " + uncollectedParcels.size());
            writer.println(String.format("Total fees collected: £%.2f", totalFees));
        }
    }
}
