/*
 *  complete every method and constructor marked TODO.
 * Do NOT change the package, the class name, or any method or constructor
 * name, parameter list or return type. You may add private fields and
 * private helper methods.
 */
package view;

import controller.DepotController;
import model.ProcessingResult;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/** Holds the buttons the user interacts with to drive the simulation. */
public class ControlPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private final JButton processNextButton = new JButton("Process Next Customer");
    private final JButton endDayButton = new JButton("End Day");

    /**
     * Creates the panel of buttons that drive the simulation. It must
     * contain at least a "Process Next Customer" button, which asks the
     * controller to process the next customer and shows the result in the
     * processing panel, and an "End Day" button, which asks the controller to
     * write the end-of-day report and log file and tells the user (with a
     * dialog) whether this succeeded. Use anonymous ActionListener classes,
     * not lambdas.
     *
     * @param controller      the controller that performs the actions
     * @param processingPanel the panel in which to show each processing result
     */
    public ControlPanel(DepotController controller, ProcessingPanel processingPanel) {
        if (controller == null) {
            throw new IllegalArgumentException("Controller must not be null.");
        }
        if (processingPanel == null) {
            throw new IllegalArgumentException("ProcessingPanel must not be null.");
        }

        setLayout(new FlowLayout(FlowLayout.LEFT));

        // Add action listener to process next customer button using an anonymous class
        processNextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProcessingResult result = controller.processNextCustomer();
                processingPanel.showResult(result);
                
            }
        });

        // Add action listener to end day button using an anonymous class
        endDayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String reportPath = "end_of_day_report.txt";
                String logPath = "depot_log.txt";
                try {
                    controller.endDay(reportPath, logPath);
                    JOptionPane.showMessageDialog(
                            ControlPanel.this,
                            "Day ended successfully!\nReport saved to: " + reportPath + "\nLog saved to: " + logPath,
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(
                            ControlPanel.this,
                            "Failed to end day: " + ex.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });

        add(processNextButton);
        add(endDayButton);
    }
}