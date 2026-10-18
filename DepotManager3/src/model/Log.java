/*
 * complete every method and constructor marked TODO.
 * Do NOT change the package, the class name, or any method or constructor
 * name, parameter list or return type. You may add private fields and
 * private helper methods.
 */
package model;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Singleton class recording every significant event in the depot (a
 * customer joining the queue, a customer being processed, a parcel being
 * added, and so on). There is exactly one Log instance for the whole
 * application, obtained via {@link #getInstance()}.
 */
public final class Log {

    private static Log instance;

    private static final DateTimeFormatter TIMESTAMP_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final StringBuffer buffer = new StringBuffer();

    private Log() {
        // private constructor enforces the Singleton pattern
    }

    /**
     * Returns the single Log instance for the whole application, creating it
     * the first time this method is called (lazy initialisation). The method
     * is synchronized so that only one instance can ever be created.
     *
     * @return the one and only Log instance
     */
    public static synchronized Log getInstance() {
        // TODO: implement this method as described in the Javadoc above.
    	
    }

    /**
     * Appends one line to the log in the form
     * "[yyyy-MM-dd HH:mm:ss] message", followed by a line separator, using the
     * current date and time.
     *
     * @param message the event to record
     */
    public void logEvent(String message) {
        // TODO: implement this method as described in the Javadoc above.
    	
    }

    /**
     * @return everything recorded in the log so far, as a single String
     */
    public String getLogContents() {
        // TODO: implement this method as described in the Javadoc above.
    	return buffer.toString();
    }

    /**
     * Writes the full contents of the log to the given file, replacing any
     * existing content. The file must be written using UTF-8 encoding so that
     * characters such as the pound sign are stored correctly.
     *
     * @param filePath path of the file to write
     * @throws IOException if the file cannot be written
     */
    public void writeToFile(String filePath) throws IOException {
        // TODO: implement this method as described in the Javadoc above.
    	
    }

    /**
     * Clears all recorded log entries. Intended for use between test cases
     * so that each test starts from a known, empty log state, since the
     * Singleton instance is otherwise shared across the whole JVM.
     */
    public void clear() {
        // TODO: implement this method as described in the Javadoc above.
    	buffer.setLength(0);
    }
}
