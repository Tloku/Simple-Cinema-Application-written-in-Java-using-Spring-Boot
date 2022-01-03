package com.Application.springbootapp.WindowApp;

import com.Application.springbootapp.Services.iBiletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class FixTicketWindow extends JFrame {
    private JPanel panel = new JPanel(new GridBagLayout());
    private JLabel mainLabel = new JLabel("Zmień godzine rozpoczęcia biletu lub usuń bilet");
    private JLabel hourLabel = new JLabel("Godzina: ");
    private JLabel minutesLabel = new JLabel("Minuta: ");
    private JTextField hourTextField = new JTextField( 5);
    private JTextField minutesTextField = new JTextField( 5);

    private JButton changeHourButton = new JButton("Zmień godzinę");
    private JButton deleteTicketButton = new JButton("Usuń bilet");
    private iBiletService biletService;
    private int ticketID;
    private JButton findByEmailButton;

    @Autowired
    public FixTicketWindow(iBiletService biletService, @Value("${property.ticketID:0}") int ticketID,
                           @Value("${property.findByEmailButton:0}") JButton findByEmailButton) {
        super("Fix errors");
        this.biletService = biletService;
        this.ticketID = ticketID;
        this.findByEmailButton = findByEmailButton;
        initComponents();
        initLayout();
        System.out.println(ticketID);
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

        changeHourButton.addActionListener(e -> {
            changeHourActionListener();
        });

        deleteTicketButton.addActionListener(e -> {
            deleteTicketActionListener();
        });
    }


    private void changeHourActionListener()  {
        int hour, minutes;
        try{
            hour = Integer.parseInt(hourTextField.getText());
        } catch(NumberFormatException nfe){
            JOptionPane.showMessageDialog(null, "Podano zły format danych (godziny)");
            return;
        }
        try{
            minutes = Integer.parseInt(minutesTextField.getText());
        } catch(NumberFormatException nfe){
            JOptionPane.showMessageDialog(null, "Podano zły format danych (minuty)");
            return;
        }

        if(checkIfHourIsCorrect(hour) && checkIfMinutesAreCorrect(minutes)){
            Date sdf = new Date();
            try {
                sdf = new SimpleDateFormat("HH:mm:ss")
                        .parse(String.valueOf(hour + 1) +":"+ String.valueOf(minutes)+":00");
                        //hour + 1 because  if I set hour to X then sql sets it to X-1 for some reason
            } catch(ParseException ex) {
                System.out.println(ex.getMessage());
            }
            System.out.println(sdf.getHours() +":" + sdf.getMinutes());
            biletService.changeTicketBeginTimeByTicketID(ticketID, sdf);
        } else {
            JOptionPane.showMessageDialog(null, "Godziny lub minuty nie zawierają się w dopuszczalnych granicach");
        }
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
        biletService.deleteTicketByTicketID(ticketID);
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
        panel.add(hourLabel, constraints);

        constraints.gridx = 1;
        panel.add(hourTextField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(minutesLabel, constraints);

        constraints.gridx = 1;
        panel.add(minutesTextField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        panel.add(changeHourButton, constraints);

        constraints.gridx = 1;
        panel.add(deleteTicketButton, constraints);

        this.add(panel);
    }
}
