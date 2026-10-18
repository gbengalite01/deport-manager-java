package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class LogTest {

    @BeforeEach
    void clearLogBeforeEachTest() {
        // The Log is a Singleton, so its state persists across tests unless
        // explicitly cleared here first, to keep each test independent.
        Log.getInstance().clear();
    }

    @Test
    void getInstanceAlwaysReturnsSameObject() {
        Log first = Log.getInstance();
        Log second = Log.getInstance();
        assertSame(first, second);
    }

    @Test
    void logEventAppendsToContents() {
        Log.getInstance().logEvent("customer joined queue");
        assertTrue(Log.getInstance().getLogContents().contains("customer joined queue"));
    }

    @Test
    void multipleLogEventsAreBothPresent() {
        Log.getInstance().logEvent("event one");
        Log.getInstance().logEvent("event two");
        String contents = Log.getInstance().getLogContents();
        assertTrue(contents.contains("event one"));
        assertTrue(contents.contains("event two"));
    }

    @Test
    void clearEmptiesTheLog() {
        Log.getInstance().logEvent("something");
        Log.getInstance().clear();
        assertEquals("", Log.getInstance().getLogContents());
    }

    @Test
    void writeToFileWritesLoggedContents() throws IOException {
        Log.getInstance().logEvent("written to file");
        Path tmp = Files.createTempFile("log", ".txt");
        Log.getInstance().writeToFile(tmp.toString());
        String contents = Files.readString(tmp);
        Files.deleteIfExists(tmp);
        assertTrue(contents.contains("written to file"));
    }

    @Test
    void writeToFileCorrectlyEncodesPoundSignAsUtf8() throws IOException {
        // Regression test: writing with the platform default charset
        // instead of explicit UTF-8 silently turns '£' into '?' on some
        // systems. Reading the file back with Files.readString (which
        // defaults to UTF-8) and checking the exact character, rather
        // than just checking nearby text, is what catches that class of
        // bug -- checking only the numbers or labels around it would not.
        Log.getInstance().logEvent("fee £7.4");
        Path tmp = Files.createTempFile("log", ".txt");
        Log.getInstance().writeToFile(tmp.toString());
        String contents = Files.readString(tmp);
        Files.deleteIfExists(tmp);
        assertTrue(contents.contains("£7.4"));
        assertFalse(contents.contains("?7.4"));
    }

    @Test
    void logContentsIncludesTimestampBrackets() {
        Log.getInstance().logEvent("timestamped event");
        String contents = Log.getInstance().getLogContents();
        assertTrue(contents.startsWith("["));
    }
}
