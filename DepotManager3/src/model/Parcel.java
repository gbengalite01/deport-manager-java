/*
 * complete every method and constructor marked TODO.
 * Do NOT change the package, the class name, or any method or constructor
 * name, parameter list or return type. You may add private fields and
 * private helper methods.
 */
package model;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Represents a single parcel held at the depot.
 *
 * A valid parcel ID is a single letter, 'C' or 'X', followed by two or three
 * digits (e.g. "C101", "X59"). Weight and every dimension must be strictly
 * positive. Days in depot must not be negative.
 */
public class Parcel {

    private static final Pattern ID_PATTERN = Pattern.compile("^[CX][0-9]{2,3}$");

    private String id;
    private double weight;
    private double length;
    private double width;
    private double height;
    private int daysInDepot;
    private boolean collected;

    /**
     * Creates a parcel, validating every argument. A newly created parcel is
     * not collected.
     *
     * @param id          the parcel ID: 'C' or 'X' followed by 2-3 digits
     * @param weight      the weight (must be greater than zero)
     * @param length      the length (must be greater than zero)
     * @param width       the width (must be greater than zero)
     * @param height      the height (must be greater than zero)
     * @param daysInDepot the number of days in the depot (must not be negative)
     * @throws InvalidParcelDataException if any argument is invalid
     */
    public Parcel(String id, double weight, double length, double width,
                  double height, int daysInDepot) throws InvalidParcelDataException {
        // TODO: implement this constructor as described in the Javadoc above.
    	if (id == null || !ID_PATTERN.matcher(id).matches()) {
            throw new InvalidParcelDataException("Invalid parcel ID format: " + id);
        }
        if (weight <= 0) {
            throw new InvalidParcelDataException("Weight must be strictly positive.");
        }
        if (length <= 0) {
            throw new InvalidParcelDataException("Length must be strictly positive.");
        }
        if (width <= 0) {
            throw new InvalidParcelDataException("Width must be strictly positive.");
        }
        if (height <= 0) {
            throw new InvalidParcelDataException("Height must be strictly positive.");
        }
        if (daysInDepot < 0) {
            throw new InvalidParcelDataException("Days in depot must not be negative.");
        }

        this.id = id;
        this.weight = weight;
        this.length = length;
        this.width = width;
        this.height = height;
        this.daysInDepot = daysInDepot;
        this.collected = false;
    }

    /**
     * @return the parcel ID
     */
    public String getId() {
        // TODO: implement this method as described in the Javadoc above.
    	return id;
    }

    /**
     * @return the parcel weight
     */
    public double getWeight() {
        // TODO: implement this method as described in the Javadoc above.
    	return weight;
    }

    /**
     * @return the parcel length
     */
    public double getLength() {
        // TODO: implement this method as described in the Javadoc above.
    	return length;
    }

    /**
     * @return the parcel width
     */
    public double getWidth() {
        // TODO: implement this method as described in the Javadoc above.
    	return width;
    }

    /**
     * @return the parcel height
     */
    public double getHeight() {
        // TODO: implement this method as described in the Javadoc above.
    	return height;
    }

    /**
     * @return the number of days the parcel has been in the depot
     */
    public int getDaysInDepot() {
        // TODO: implement this method as described in the Javadoc above.
    	return daysInDepot;
    }

    /**
     * @return true if the parcel has been collected
     */
    public boolean isCollected() {
        // TODO: implement this method as described in the Javadoc above.
    	return collected;
    }

    /**
     * @param collected the new collected status of the parcel
     */
    public void setCollected(boolean collected) {
        // TODO: implement this method as described in the Javadoc above.
    	this.collected = collected;
    }

    /**
     * @return the volume of the parcel (length x width x height)
     */
    public double getVolume() {
        // TODO: implement this method as described in the Javadoc above.
    	return length * width * height;
    }

    /**
     * Two parcels are equal if they have the same ID.
     *
     * @param o the object to compare with
     * @return true if o is a Parcel with the same ID
     */
    @Override
    public boolean equals(Object o) {
        // TODO: implement this method as described in the Javadoc above.
    	if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Parcel parcel = (Parcel) o;
        return Objects.equals(id, parcel.id);
    }

    /**
     * @return a hash code consistent with equals (based on the ID)
     */
    @Override
    public int hashCode() {
        // TODO: implement this method as described in the Javadoc above.
    	return Objects.hash(id);
    }

    /**
     * @return a readable description that includes the ID, weight,
     *         dimensions, days in depot and collected status
     */
    @Override
    public String toString() {
        // TODO: implement this method as described in the Javadoc above.
    	return "Parcel{" +
        "id='" + id + '\'' +
        ", weight=" + weight +
        ", length=" + length +
        ", width=" + width +
        ", height=" + height +
        ", daysInDepot=" + daysInDepot +
        ", collected=" + collected +
        '}';
    }
}
