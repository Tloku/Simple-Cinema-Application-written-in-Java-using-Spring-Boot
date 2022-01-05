package com.Application.springbootapp.WindowApp;

import com.Application.springbootapp.Services.iScheduleService;
import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChangeScheduleTimeWindow extends JFrame {
    private JPanel panel = new JPanel(new GridBagLayout());
    private JLabel beginLabel = new JLabel("Godzina rozpoczęcia: ");
    private JTextField beginTextField = new JTextField("HH:MM:SS", 10);
    private JLabel endLabel = new JLabel("Godzina zakończenia: ");
    private JTextField endTextField = new JTextField("HH:MM:SS", 10);
    private JLabel scheduleIDLabel = new JLabel("ID Harmonogramu");
    private JTextField scheduleIDTextField = new JTextField(5);
    private iScheduleService scheduleService;
    private JButton changeButton = new JButton("Zmień");
    private Date beginTime, endTime;

    public ChangeScheduleTimeWindow(iScheduleService scheduleService) {
        super("Zmiana godzin grania filmu");
        this.scheduleService = scheduleService;
        initComponents();
        initLayout();
    }

    private void initComponents() {
        int windowSizeWidth = 300;
        int windowsSizeHeight = 300;
        this.setSize(windowSizeWidth, windowsSizeHeight);
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.setLocation((width - windowSizeWidth) / 2, (height - windowsSizeHeight) / 2);
        this.setResizable(false);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(false);
        changeButton.addActionListener(e -> {changeButtonActionListener();});
    }

    private void changeButtonActionListener() {
        int scheduleID;
        String begin = beginTextField.getText();
        String end = endTextField.getText();
        try {
            scheduleID = Integer.parseInt(scheduleIDTextField.getText());
        } catch(NumberFormatException nfe) {
            JOptionPane.showMessageDialog(null, "Zły format ID harmonogramu");
            return;
        }
        try {
            beginTime = new SimpleDateFormat("HH:mm:ss").parse(begin);
            endTime = new SimpleDateFormat("HH:mm:ss").parse(end);
        } catch(Exception ex) {
            System.out.println(ex.getMessage());
            return;
        }
        beginTime.setHours(beginTime.getHours() + 1);
        endTime.setHours(endTime.getHours() + 1);

        scheduleService.updateScheduleByID(beginTime, endTime, scheduleID);
        this.setVisible(false);
    }

    private void initLayout() {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        panel.add(beginLabel, constraints);

        constraints.gridx = 1;
        panel.add(beginTextField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(endLabel, constraints);

        constraints.gridx = 1;
        panel.add(endTextField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        panel.add(scheduleIDLabel, constraints);

        constraints.gridx = 1;
        panel.add(scheduleIDTextField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(changeButton, constraints);

        this.add(panel);
    }
}
