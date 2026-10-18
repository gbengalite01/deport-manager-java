package util;

import model.InvalidParcelDataException;
import model.Parcel;
import model.ParcelMap;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class ReportWriterTest {

    @Test
    void writeReportListsCollectedAndUncollectedParcels() throws IOException, InvalidParcelDataException {
        ParcelMap pm = new ParcelMap();
        Parcel collected = new Parcel("C101", 10, 2, 5, 2, 2);
        collected.setCollected(true);
        pm.addParcel(collected);
        pm.addParcel(new Parcel("C200", 8, 6, 2, 2, 5));

        Path tmp = Files.createTempFile("report", ".txt");
        ReportWriter.writeReport(tmp.toString(), pm);
        String content = Files.readString(tmp);
        Files.deleteIfExists(tmp);

        assertTrue(content.contains("COLLECTED PARCELS"));
        assertTrue(content.contains("C101"));
        assertTrue(content.contains("UNCOLLECTED PARCELS"));
        assertTrue(content.contains("C200"));
    }

    @Test
    void writeReportSummaryCountsAreCorrect() throws IOException, InvalidParcelDataException {
        ParcelMap pm = new ParcelMap();
        Parcel collected = new Parcel("C101", 10, 2, 5, 2, 2);
        collected.setCollected(true);
        pm.addParcel(collected);
        pm.addParcel(new Parcel("C200", 8, 6, 2, 2, 5));
        pm.addParcel(new Parcel("X59", 5, 4, 1, 2, 1));

        Path tmp = Files.createTempFile("report", ".txt");
        ReportWriter.writeReport(tmp.toString(), pm);
        String content = Files.readString(tmp);
        Files.deleteIfExists(tmp);

        assertTrue(content.contains("Total parcels: 3"));
        assertTrue(content.contains("Collected: 1"));
        assertTrue(content.contains("Uncollected: 2"));
    }

    @Test
    void writeReportHandlesNoCollectedParcels() throws IOException, InvalidParcelDataException {
        ParcelMap pm = new ParcelMap();
        pm.addParcel(new Parcel("C200", 8, 6, 2, 2, 5));

        Path tmp = Files.createTempFile("report", ".txt");
        ReportWriter.writeReport(tmp.toString(), pm);
        String content = Files.readString(tmp);
        Files.deleteIfExists(tmp);

        assertTrue(content.contains("Collected: 0"));
    }

    @Test
    void writeReportHandlesNoUncollectedParcels() throws IOException, InvalidParcelDataException {
        ParcelMap pm = new ParcelMap();
        Parcel collected = new Parcel("C101", 10, 2, 5, 2, 2);
        collected.setCollected(true);
        pm.addParcel(collected);

        Path tmp = Files.createTempFile("report", ".txt");
        ReportWriter.writeReport(tmp.toString(), pm);
        String content = Files.readString(tmp);
        Files.deleteIfExists(tmp);

        assertTrue(content.contains("Uncollected: 0"));
    }

    @Test
    void writeReportHandlesEmptyParcelMap() throws IOException {
        ParcelMap pm = new ParcelMap();
        Path tmp = Files.createTempFile("report", ".txt");
        ReportWriter.writeReport(tmp.toString(), pm);
        String content = Files.readString(tmp);
        Files.deleteIfExists(tmp);

        assertTrue(content.contains("Total parcels: 0"));
    }

    @Test
    void writeReportTotalFeesMatchesCalculatedFee() throws IOException, InvalidParcelDataException {
        ParcelMap pm = new ParcelMap();
        Parcel collected = new Parcel("C101", 10, 2, 5, 2, 2); // fee = 7.4
        collected.setCollected(true);
        pm.addParcel(collected);

        Path tmp = Files.createTempFile("report", ".txt");
        ReportWriter.writeReport(tmp.toString(), pm);
        String content = Files.readString(tmp);
        Files.deleteIfExists(tmp);

        assertTrue(content.contains("7.4"));
    }

    @Test
    void writeReportCorrectlyEncodesPoundSignAsUtf8() throws IOException, InvalidParcelDataException {
        // Regression test: writing with the platform default charset
        // instead of explicit UTF-8 silently turns '£' into '?' on some
        // systems. Checking the exact currency symbol, not just the
        // number next to it, is what catches that class of bug.
        ParcelMap pm = new ParcelMap();
        Parcel collected = new Parcel("C101", 10, 2, 5, 2, 2); // fee = 7.4
        collected.setCollected(true);
        pm.addParcel(collected);

        Path tmp = Files.createTempFile("report", ".txt");
        ReportWriter.writeReport(tmp.toString(), pm);
        String content = Files.readString(tmp);
        Files.deleteIfExists(tmp);

        assertTrue(content.contains("£7.4"));
        assertFalse(content.contains("?7.4"));
        assertTrue(content.contains("£") && content.contains("Total fees collected"));
    }
}
