/*
 * complete every method and constructor marked TODO.
 * Do NOT change the package, the class name, or any method or constructor
 * name, parameter list or return type. You may add private fields and
 * private helper methods.
 */
package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Container class holding all Parcel objects known to the depot, keyed by
 * parcel ID. A LinkedHashMap is used so that iteration order matches
 * insertion order, which keeps GUI displays and reports stable and
 * predictable for the user.
 */
public class ParcelMap {

    private final Map<String, Parcel> parcels = new LinkedHashMap<>();

    /**
     * Adds a parcel, keyed by its ID. A parcel with the same ID as an
     * existing one replaces it.
     *
     * @param parcel the parcel to add
     * @throws IllegalArgumentException if parcel is null
     */
    public void addParcel(Parcel parcel) {
        // TODO: implement this method as described in the Javadoc above.
    	if (parcel == null) {
            throw new IllegalArgumentException("Parcel must not be null.");
        }
        parcels.put(parcel.getId(), parcel);
    }

    /**
     * @param id the parcel ID to look up
     * @return the parcel with that ID, or null if there is none
     */
    public Parcel getParcel(String id) {
        // TODO: implement this method as described in the Javadoc above.
    	return parcels.get(id);
    }

    /**
     * @param id the parcel ID to look up
     * @return true if a parcel with that ID is held
     */
    public boolean containsParcel(String id) {
        // TODO: implement this method as described in the Javadoc above.
    	return parcels.containsKey(id);
    }

    /**
     * Removes the parcel with the given ID.
     *
     * @param id the parcel ID
     * @return the removed parcel, or null if there was none
     */
    public Parcel removeParcel(String id) {
        // TODO: implement this method as described in the Javadoc above.
    	return parcels.remove(id);
    }

    /**
     * @return the number of parcels held
     */
    public int size() {
        // TODO: implement this method as described in the Javadoc above.
    	return parcels.size();
    }

    /**
     * @return an unmodifiable list of every parcel held, in the order the
     *         parcels were added
     */
    public List<Parcel> getAllParcels() {
        // TODO: implement this method as described in the Javadoc above.
    	return Collections.unmodifiableList(new ArrayList<>(parcels.values()));
    }

    /**
     * @return a new list of the parcels that have not been collected, in the
     *         order the parcels were added
     */
    public List<Parcel> getUncollectedParcels() {
        // TODO: implement this method as described in the Javadoc above.
    	List<Parcel> uncollected = new ArrayList<>();
        for (Parcel parcel : parcels.values()) {
            if (!parcel.isCollected()) {
                uncollected.add(parcel);
            }
        }
        return uncollected;
    }

    /**
     * Returns uncollected parcels sorted by days in depot, oldest (most
     * days waiting) first -- a natural collection priority order.
     *
     * A plain insertion sort is used here (rather than List.sort with a
     * lambda/Comparator) so the logic is expressed as ordinary, explicit
     * Java statements.
     */
    public List<Parcel> getUncollectedSortedByDaysInDepot() {
        // TODO: implement this method as described in the Javadoc above.
    	List<Parcel> list = getUncollectedParcels();
        
        // Plain insertion sort
        for (int i = 1; i < list.size(); i++) {
            Parcel key = list.get(i);
            int j = i - 1;
            
            // Sort in descending order of days in depot (oldest/most days first)
            while (j >= 0 && list.get(j).getDaysInDepot() < key.getDaysInDepot()) {
                list.set(j + 1, list.get(j));
                j--;
            }
            list.set(j + 1, key);
        }
        
        return list;
    }
    
}
