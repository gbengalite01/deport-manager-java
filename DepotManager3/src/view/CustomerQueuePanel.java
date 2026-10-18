/*
 * complete every method and constructor marked TODO.
 * Do NOT change the package, the class name, or any method or constructor
 * name, parameter list or return type. You may add private fields and
 * private helper methods.
 */
package view;

import model.Customer;
import model.CustomerQueue;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/** Displays the current queue of customers waiting to be served. */
public class CustomerQueuePanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private final DefaultListModel<String> listModel = new DefaultListModel<>();
    private final JList<String> queueList = new JList<>(listModel);

    /**
     * Creates the panel that lists the customers waiting in the queue.
     */
    public CustomerQueuePanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Customer Queue"));
        
        queueList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(queueList), BorderLayout.CENTER);
    }

    /**
     * Redisplays the queue so that it matches the current contents of the
     * given CustomerQueue, in queue order.
     *
     * @param customerQueue the queue to display
     */
    public void refresh(CustomerQueue customerQueue) {
        listModel.clear();
        if (customerQueue != null) {
            List<Customer> snapshot = customerQueue.getQueueSnapshot();
            for (Customer customer : snapshot) {
                String displayText = String.format("Seq: %d | Name: %s | Parcel: %s",
                        customer.getSequenceNumber(),
                        customer.getName(),
                        customer.getParcelId());
                listModel.addElement(displayText);
            }
        }
    }
}