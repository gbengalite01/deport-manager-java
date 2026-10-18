/*
 * complete every method and constructor marked TODO.
 * Do NOT change the package, the class name, or any method or constructor
 * name, parameter list or return type. You may add private fields and
 * private helper methods.
 */
package view;

import model.Parcel;
import model.ParcelMap;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/** Displays the list of parcels still awaiting collection. */
public class ParcelListPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private final DefaultListModel<String> listModel = new DefaultListModel<>();
    private final JList<String> parcelList = new JList<>(listModel);

    /**
     * Creates the panel that lists the parcels awaiting collection.
     */
    public ParcelListPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Uncollected Parcels"));
        
        parcelList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(parcelList), BorderLayout.CENTER);
    }

    /**
     * Redisplays the uncollected parcels in the given ParcelMap, longest
     * waiting first (see ParcelMap.getUncollectedSortedByDaysInDepot).
     *
     * @param parcelMap the parcels to display
     */
    public void refresh(ParcelMap parcelMap) {
        listModel.clear();
        if (parcelMap != null) {
            List<Parcel> uncollectedParcels = parcelMap.getUncollectedSortedByDaysInDepot();
            for (Parcel parcel : uncollectedParcels) {
                String displayText = String.format("ID: %s | Weight: %.1f | Dimensions: %.1fx%.1fx%.1f | Days in Depot: %d",
                        parcel.getId(),
                        parcel.getWeight(),
                        parcel.getLength(),
                        parcel.getWidth(),
                        parcel.getHeight(),
                        parcel.getDaysInDepot());
                listModel.addElement(displayText);
            }
        }
    }
}