package com.Application.springbootapp.WindowApp;

import com.Application.springbootapp.Services.iTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class FixTicketWindow extends JFrame {
    private JPanel panel = new JPanel(new GridBagLayout());
    private JLabel mainLabel = new JLabel("Zmień godzine rozpoczęcia biletu lub usuń bilet");
    private JLabel beginTimeLabel = new JLabel("Godzina rozpoczęcia: ");
    private JTextField beginTimeTextField = new JTextField( "HH:mm:ss",10);

    private JButton changeHourButton = new JButton("Zmień godzinę");
    private JButton deleteTicketButton = new JButton("Usuń bilet");
    private iTicketService ticketService;
    private int ticketID;
    private JButton findByEmailButton;
    private Date beginTime;

    @Autowired
    public FixTicketWindow(iTicketService ticketService, @Value("${property.ticketID:0}") int ticketID,
                           @Value("${property.findByEmailButton:0}") JButton findByEmailButton) {
        super("Popraw błędy");
        this.ticketService = ticketService;
        this.ticketID = ticketID;
        this.findByEmailButton = findByEmailButton;
        initComponents();
        initLayout();
    }

    private void initComponents() {
        int windowSizeWidth = 400;
        int windowsSizeHeight = 300;
        this.setSize(windowSizeWidth, windowsSizeHeight);
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.setLocation((width - windowSizeWidth) / 2, (height - windowsSizeHeight) / 2);
        this.setResizable(false);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(false);
        changeHourButton.addActionListener(e -> {changeHourActionListener();});
        deleteTicketButton.addActionListener(e -> {deleteTicketActionListener();});
    }

    private void changeHourActionListener()  {
        String begin = beginTimeTextField.getText();
        int hour, minutes;
        try {
            hour = Integer.parseInt(begin.substring(0,2));
            minutes = Integer.parseInt(begin.substring(3,5));
        } catch(NumberFormatException nfe) {
            System.out.println(nfe.getMessage());
            return;
        }
        //hour + 1 because  if I set hour to X then sql sets it to X-1 for some reason
        if(checkIfHourIsCorrect(hour+1) && checkIfMinutesAreCorrect(minutes)) {
            try {
                beginTime = new SimpleDateFormat("HH:mm:ss").parse(begin);
            } catch(Exception ex) {
                System.out.println(ex.getMessage());
                return;
            }
            beginTime.setHours(beginTime.getHours() + 1);
            ticketService.changeTicketBeginTimeByTicketID(ticketID, beginTime);
        } else {
            JOptionPane.showMessageDialog(null, "Podano nieistniejącą godzine");
        }
        this.setVisible(false);
        findByEmailButton.doClick();
    }

    private boolean checkIfHourIsCorrect(int hour){
        if(hour > 24 || hour < 0){
            return false;
        }
        return true;
    }

    private boolean checkIfMinutesAreCorrect(int minutes){
        if(minutes > 60 || minutes < 0){
            return false;
        }
        return true;
    }

    private void deleteTicketActionListener() {
        ticketService.deleteTicketByTicketID(ticketID);
        JOptionPane.showMessageDialog(null, "Usunięto bilet o ID: " + ticketID);
        this.setVisible(false);
        findByEmailButton.doClick();
    }

    private void initLayout() {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(mainLabel, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        panel.add(beginTimeLabel, constraints);

        constraints.gridx = 1;
        panel.add(beginTimeTextField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(changeHourButton, constraints);

        constraints.gridx = 1;
        panel.add(deleteTicketButton, constraints);

        this.add(panel);
    }
}
