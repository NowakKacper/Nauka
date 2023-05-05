package com.nowakkacper;

import javax.swing.*;
import java.awt.*;

public class AppPanel extends JPanel {

    private JButton calculateButton = new JButton("Calculate");
    private JButton loadSignalButton = new JButton("Load data");
    private JButton showLoadedSignalButton = new JButton("Show loaded data");
    private JButton showSignalCreatedBySolverMethodButton = new JButton("Show created signal");

    private JTextField dataToSolverMethodTextField = new JTextField();
    private JTextField resultTextField = new JTextField();

    String[] methods = {"Choose", "Zero Crossing Method", "Approximation Method"};
    private JComboBox chooseMethodComboBox = new JComboBox(methods);

    JLabel dataToSolverMethodLabel = new JLabel("Choose method first");
    JLabel resultLabel = new JLabel("Frequency:");
    JLabel chooseMethodLabel = new JLabel("Choose method to calculate frequency");

    private String directory;
    private String fileName;

    DataProcessing dataProcessing;

    public AppPanel(AppFrame appFrame) {

        showLoadedSignalButton.setEnabled(false);

        dataProcessing = new DataProcessing();

        this.setBackground(Color.lightGray);
        this.setSize(600, 600);
        this.setLayout(null);//new GridLayout(3, 3));

        createComponents(appFrame);
    }

    private void createComponents(AppFrame appFrame) {

        loadSignalButton.setSize(new Dimension(100, 50));
        loadSignalButton.setLocation(50, 75);
        loadSignalButton.addActionListener((event) -> {
            FileDialog fileDialog =new FileDialog(appFrame,"Load",FileDialog.LOAD);
            fileDialog.setVisible(true);
            directory= fileDialog.getDirectory();
            fileName= fileDialog.getFile();

            dataProcessing.loadData(directory + fileName);
            CreatingChart.createSignalChart(dataProcessing.getSignal(), "Loaded signal");

            showLoadedSignalButton.setEnabled(true);

            if(canIReturnResult()) calculateButton.setEnabled(true);
        });
        add(loadSignalButton);

        chooseMethodLabel.setBounds(200, 30, 400, 100);
        chooseMethodLabel.setVisible(true);
        add(chooseMethodLabel);
        chooseMethodComboBox.setSize(new Dimension(130, 20));
        chooseMethodComboBox.setLocation(250, 95);
        chooseMethodComboBox.addActionListener((event) -> {
            switch (chooseMethodComboBox.getSelectedIndex()){
                case 0:
                    dataToSolverMethodLabel.setText("Choose method first");
                    dataToSolverMethodTextField.setText("");
                    break;
                case 1:
                    dataToSolverMethodLabel.setText("Number of measurement intervals:");
                    dataToSolverMethodTextField.setText("1");
                    break;
                case 2:
                    dataToSolverMethodLabel.setText("Number of intervals to compare:");
                    dataToSolverMethodTextField.setText("200");
                    break;
            }

            if(canIReturnResult()) calculateButton.setEnabled(true);
        });
        add(chooseMethodComboBox);

        resultLabel.setBounds(460, 30, 400, 100);
        resultLabel.setVisible(true);
        add(resultLabel);
        resultTextField.setSize(130, 20);
        resultTextField.setLocation(460, 95);
        add(resultTextField);

        showLoadedSignalButton.setSize(new Dimension(150, 50));
        showLoadedSignalButton.setLocation(25, 175);
        showLoadedSignalButton.addActionListener((event) -> {
            CreatingChart.createSignalChart(dataProcessing.getSignal(), "Loaded signal");
        });
        add(showLoadedSignalButton);

        dataToSolverMethodLabel.setBounds(250, 130, 400, 100);
        dataToSolverMethodLabel.setVisible(true);
        add(dataToSolverMethodLabel);
        dataToSolverMethodTextField.setEnabled(true);
        dataToSolverMethodTextField.setSize(130, 20);
        dataToSolverMethodTextField.setLocation(250, 195);
        add(dataToSolverMethodTextField);

        showSignalCreatedBySolverMethodButton.setSize(new Dimension(150, 50));
        showSignalCreatedBySolverMethodButton.setLocation(450, 175);
        showSignalCreatedBySolverMethodButton.setEnabled(false);
        showSignalCreatedBySolverMethodButton.addActionListener((event) -> {
            if(chooseMethodComboBox.getSelectedIndex() == 1)
                CreatingChart.createSignalChart(dataProcessing.getWhereSignalCrossZero());
            if(chooseMethodComboBox.getSelectedIndex() == 2)
                CreatingChart.createSignalChart(dataProcessing.getTestSignalThatBestReflectsTheOriginal(), "Test Signal That Best Reflects The Original");
        });
        add(showSignalCreatedBySolverMethodButton);

        calculateButton.setSize(new Dimension(150, 50));
        calculateButton.setLocation(240, 270);
        calculateButton.setEnabled(false);
        calculateButton.addActionListener((event) -> {
            double data=0;
            if(chooseMethodComboBox.getSelectedIndex() == 1)
                data = dataProcessing.zeroCrossingMethod(dataToSolverMethodTextField.getText());
            if(chooseMethodComboBox.getSelectedIndex() == 2)
                data = dataProcessing.approximationMethod(dataToSolverMethodTextField.getText());
            resultTextField.setText("" + data);

            if(chooseMethodComboBox.getSelectedIndex() == 1)
                CreatingChart.createSignalChart(dataProcessing.getWhereSignalCrossZero());
            if(chooseMethodComboBox.getSelectedIndex() == 2)
                CreatingChart.createSignalChart(dataProcessing.getTestSignalThatBestReflectsTheOriginal(), "Test Signal That Best Reflects The Original");

            showSignalCreatedBySolverMethodButton.setEnabled(true);
        });
        add(calculateButton);

    }

    private boolean canIReturnResult() {
        return showLoadedSignalButton.isEnabled() && chooseMethodComboBox.getSelectedIndex() != 0;
    }
}
