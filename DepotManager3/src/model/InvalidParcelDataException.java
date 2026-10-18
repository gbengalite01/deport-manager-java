package model;

/**
 * Thrown when a Parcel is constructed with invalid data (bad ID format,
 * non-positive dimensions/weight, or a negative number of days in depot).
 */
public class InvalidParcelDataException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Creates the exception with a description of the invalid data.
     *
     * @param message a description of what was invalid
     */
    public InvalidParcelDataException(String message) {
        super(message);
    }
}
