/*
 * complete every method and constructor marked TODO.
 * Do NOT change the package, the class name, or any method or constructor
 * name, parameter list or return type. You may add private fields and
 * private helper methods.
 */
package view;

import controller.DepotController;

import javax.swing.*;
import java.awt.*;

/**
 * The main application window. Implements DepotController.DepotListener
 * (the Observer role) so that it is notified, and refreshes its child
 * panels, every time the model changes.
 */
public class DepotManagerFrame extends JFrame implements DepotController.DepotListener {

    private static final long serialVersionUID = 1L;

    private DepotController controller;
    private final ParcelListPanel parcelListPanel = new ParcelListPanel();
    private final CustomerQueuePanel customerQueuePanel = new CustomerQueuePanel();
    private final ProcessingPanel processingPanel = new ProcessingPanel();

    /**
     * Creates the main window: registers itself with the controller as a
     * DepotListener, lays out the parcel list, customer queue, processing and
     * control panels, and displays the initial state of the model.
     *
     * @param controller the application's controller
     */
    public DepotManagerFrame(DepotController controller) {
        if (controller == null) {
            throw new IllegalArgumentException("Controller must not be null.");
        }
        this.controller = controller;

        // Register as a listener
        this.controller.addListener(this);

        // Basic frame setup
        setTitle("Depot Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Create control panel passing controller and processing panel
        ControlPanel controlPanel = new ControlPanel(controller, processingPanel);

        // Central area split between Parcel List and Customer Queue
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        centerPanel.add(parcelListPanel);
        centerPanel.add(customerQueuePanel);

        // South area holding processing panel and control panel
        JPanel southPanel = new JPanel(new BorderLayout(5, 5));
        southPanel.add(processingPanel, BorderLayout.CENTER);
        southPanel.add(controlPanel, BorderLayout.SOUTH);

        // Add main sections to frame
        add(centerPanel, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);

        // Display initial state of the model
        onDepotUpdated();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Called by the controller whenever the model changes: refreshes the
     * parcel list and customer queue panels from the controller's model.
     */
    @Override
    public void onDepotUpdated() {
        if (controller != null) {
            parcelListPanel.refresh(controller.getParcelMap());
            customerQueuePanel.refresh(controller.getCustomerQueue());
        }
    }
}