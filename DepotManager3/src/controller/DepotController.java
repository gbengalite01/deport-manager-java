/*
 * complete every method and constructor marked TODO.
 * Do NOT change the package, the class name, or any method or constructor
 * name, parameter list or return type. You may add private fields and
 * private helper methods.
 */
package controller;

import model.InvalidParcelDataException;
import model.Customer;
import model.CustomerQueue;
import model.Log;
import model.Parcel;
import model.ParcelMap;
import model.ProcessingResult;
import model.Worker;
import util.DataLoader;
import util.ReportWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The Controller in this application's MVC structure. Views register
 * themselves as {@link DepotListener}s and are notified whenever the
 * model changes, so they know to refresh their display; the Controller
 * never references any View class directly, keeping the dependency
 * direction correct for MVC.
 */
public class DepotController {

    /** Observer-pattern callback interface implemented by View classes. */
    public interface DepotListener {
        void onDepotUpdated();
    }

    private ParcelMap parcelMap;
    private CustomerQueue customerQueue;
    private Worker worker;
    private final List<DepotListener> listeners = new ArrayList<>();

    /**
     * Creates a controller for the given model objects.
     *
     * @param parcelMap     the parcels held at the depot
     * @param customerQueue the queue of customers waiting to be served
     * @param worker        the worker who processes customers
     * @throws IllegalArgumentException if any argument is null
     */
    public DepotController(ParcelMap parcelMap, CustomerQueue customerQueue, Worker worker) {
        
    }

    /**
     * Registers a View to be notified whenever the model changes. A null
     * listener is ignored.
     *
     * @param listener the listener to register
     */
    public void addListener(DepotListener listener) {
        
    }

    /**
     * Stops notifying the given listener of model changes. Has no effect if
     * the listener was not registered.
     *
     * @param listener the listener to remove
     */
    public void removeListener(DepotListener listener) {
        
    }

    /**
     * Calls onDepotUpdated() on every registered listener, in the order in
     * which they were registered. Called at the end of every method that changes
     * the model.
     */
    private void notifyListeners() {
        
    }

    /**
     * @return the ParcelMap managed by this controller
     */
    public ParcelMap getParcelMap() {
        
    }

    /**
     * @return the CustomerQueue managed by this controller
     */
    public CustomerQueue getCustomerQueue() {
        
    }

    /**
     * Adds a parcel to the depot, records the event "Parcel [id] added to
     * depot" in the Log, and notifies all listeners.
     *
     * @param parcel the parcel to add
     */
    public void addParcel(Parcel parcel) {
        
    }

    /**
     * Adds a customer to the back of the queue, records the event
     * "Customer [name] joined the queue" in the Log, and notifies all
     * listeners.
     *
     * @param customer the customer joining the queue
     */
    public void addCustomerToQueue(Customer customer) {
        
    }

    /**
     * Asks the worker to process the customer at the front of the queue,
     * notifies all listeners, and returns the worker's result.
     *
     * @return the ProcessingResult produced by the worker
     */
    public ProcessingResult processNextCustomer() {
        
    }

    /**
     * Loads parcels from a file using DataLoader.loadParcels, adds each one
     * to the ParcelMap, records the event "[n] parcels loaded from [filePath]"
     * in the Log, and notifies all listeners.
     *
     * @param filePath path of the parcel data file
     * @throws IOException                if the file cannot be read
     * @throws InvalidParcelDataException if any line holds invalid parcel data
     */
    public void loadParcelsFromFile(String filePath) throws IOException, InvalidParcelDataException {
        
    }

    /**
     * Loads customers from a file using DataLoader.loadCustomers, adds each
     * one to the back of the queue in file order, records the event
     * "[n] customers loaded from [filePath]" in the Log, and notifies all
     * listeners.
     *
     * @param filePath path of the customer data file
     * @throws IOException if the file cannot be read or a line is malformed
     */
    public void loadCustomersFromFile(String filePath) throws IOException {
        
    }

    /**
     * Ends the working day: writes the final report (collected parcels and
     * fees, uncollected parcels, counts and totals) and the log file to
     * the given paths.
     */
    public void endDay(String reportFilePath, String logFilePath) throws IOException {
        
    }
}