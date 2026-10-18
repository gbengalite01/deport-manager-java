/*
 * complete every method and constructor marked TODO.
 * Do NOT change the package, the class name, or any method or constructor
 * name, parameter list or return type. You may add private fields and
 * private helper methods.
 */
package view;

import model.ProcessingResult;

import javax.swing.*;
import java.awt.*;

/** Displays details of the parcel most recently processed by the worker. */
public class ProcessingPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private final JTextArea detailsArea = new JTextArea(6, 24);

    /**
     * Creates the panel that shows the outcome of the most recent
     * processing of a customer.
     */
    public ProcessingPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Latest Processing Result"));
        detailsArea.setEditable(false);
        detailsArea.setLineWrap(true);
        detailsArea.setWrapStyleWord(true);
        add(new JScrollPane(detailsArea), BorderLayout.CENTER);
    }

    /**
     * Shows the given result: for a successful result, the customer's name,
     * the parcel ID and the fee; for a failed result, the failure message.
     * A null result clears the display.
     *
     * @param result the result to show (may be null)
     */
    public void showResult(ProcessingResult result) {
        if (result == null) {
            detailsArea.setText("");
        } else {
            detailsArea.setText(result.getMessage());
        }
    }
}