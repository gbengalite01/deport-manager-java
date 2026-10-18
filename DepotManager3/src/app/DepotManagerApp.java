/*
 * complete every method and constructor marked TODO.
 * Do NOT change the package, the class name, or any method or constructor
 * name, parameter list or return type. You may add private fields and
 * private helper methods.
 */
package app;

import controller.DepotController;
import model.CustomerQueue;
import model.ParcelMap;
import model.Worker;
import view.DepotManagerFrame;

import javax.swing.*;

/** Application entry point: wires up the model, controller and view. */
public class DepotManagerApp {

    /**
     * Application entry point. Creates the model objects (a ParcelMap, a
     * CustomerQueue and a Worker), creates a DepotController around them, loads
     * the initial data from data/parcels.txt and data/customers.txt (printing a
     * warning to System.err if either file cannot be loaded), and then creates
     * and shows a DepotManagerFrame.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                
            }
        });
    }
}
