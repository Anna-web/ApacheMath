package gui;

import method.Calculator;
import indicators.ExcelManager;
import excel.Writer;
import org.apache.commons.math3.exception.MathIllegalArgumentException;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class GUI extends JFrame {
    JButton openButton = new JButton("Open file");
    JButton saveButton = new JButton("Save file");
    JButton calculateButton = new JButton("Estimation");
    JButton exitButton = new JButton("Exit");
    JComboBox<String> comboBox = new JComboBox<>();
    Calculator calculator = new Calculator();
    gui.JFileChooser jfilechooser = new gui.JFileChooser();
    String path;
    ArrayList<ArrayList<String>> results = new ArrayList<>();
    GridBagConstraints gbc = new GridBagConstraints();
    private boolean isFileSaved = false;
    private boolean isFileOpened = false;

    public GUI() throws URISyntaxException {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            System.err.println("Failed to customize system appearance");
        }

        setLayout(new GridBagLayout());
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.CENTER;
        setPreferredSize(new Dimension(800, 600));
        addComponent(openButton, 0);
        addComponent(comboBox, 1);
        addComponent(calculateButton, 2);
        addComponent(saveButton, 3);
        addComponent(exitButton, 4);
        Dimension buttonSize = new Dimension(300, 60);
        openButton.setPreferredSize(buttonSize);
        saveButton.setPreferredSize(buttonSize);
        calculateButton.setPreferredSize(buttonSize);
        exitButton.setPreferredSize(buttonSize);
        Dimension comboBoxSize = new Dimension(300, 40);
        comboBox.setPreferredSize(comboBoxSize);
        Color buttonColor = new Color(11, 103, 101);
        openButton.setBackground(buttonColor);
        openButton.setForeground(Color.WHITE);
        saveButton.setBackground(buttonColor);
        saveButton.setForeground(Color.WHITE);
        calculateButton.setBackground(buttonColor);
        calculateButton.setForeground(Color.WHITE);
        exitButton.setBackground(buttonColor);
        exitButton.setForeground(Color.WHITE);

        openButton.addActionListener(e -> {
            try {
                jfilechooser = new JFileChooser();
                path = jfilechooser.openFile();

                if (path == null) {
                    JOptionPane.showMessageDialog(null, "File is not selected", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                isFileOpened = true;
                comboBox.removeAllItems();
                for (String sheet : new gui.Sheets(path).getNames()) {
                    comboBox.addItem(sheet);
                }
            } catch (URISyntaxException ex) {
                throw new RuntimeException(ex);
            }
        });

        calculateButton.addActionListener(e -> {
            try {
                if (!isFileOpened) {
                    JOptionPane.showMessageDialog(null, "File was not opened", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                ExcelManager data = new ExcelManager(path, comboBox.getSelectedIndex());
                ArrayList<ArrayList<Double>> list = data.getDataArray();

                if (list.isEmpty() || list.getFirst().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Data sheets are empty or contain insufficient info for estimation", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                calculator.calculate(list);
                results = calculator.fillResults();
                JOptionPane.showMessageDialog(null, "Estimates were completed");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Unable to read data from the file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (MathIllegalArgumentException ex) {
                JOptionPane.showMessageDialog(null, "Estimation error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Unexpected error occured: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        saveButton.addActionListener(e -> {
            try {
                if (!isFileOpened) {
                    JOptionPane.showMessageDialog(null, "File was not opened", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (results.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No data to save", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                File outputFile = new File(path);
                if (!outputFile.exists() || !outputFile.canWrite()) {
                    throw new IOException("File is not writable");
                }

                Writer writer = new Writer(path, results, calculator);
                writer.writeIntoExcel();

                isFileSaved = true;
                showMessageIfFileSaved();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Error saving file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                isFileSaved = false;
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Unexpected error occured: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                isFileSaved = false;
            }
        });


        exitButton.addActionListener(e -> System.exit(0));
        setTitle("GUI");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);
        pack();
    }

    public static void main(String[] args) throws URISyntaxException {
        new GUI();
    }

    private void addComponent(Component component, int gridy) {
        gbc.gridx = 0;
        gbc.gridy = gridy;
        add(component, gbc);
    }

    public void showMessageIfFileSaved() {
        if (isFileSaved) {
            JOptionPane.showMessageDialog(null, "File was saved");
            isFileSaved = false;
        }
    }
}