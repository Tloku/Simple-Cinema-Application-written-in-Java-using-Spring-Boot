package com.Application.springbootapp.WindowApp;

import com.Application.springbootapp.Services.iScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class AddScheduleWindow extends JFrame {
    JPanel panel = new JPanel(new GridBagLayout());
    private iScheduleService scheduleService;
    private int repertoireID;
    private JLabel beginningTimeLabel = new JLabel("Czas rozpoczęcia");
    private JTextField beginningTimeTextField = new JTextField("HH:MM:SS",16);
    private JLabel endingTimeLabel = new JLabel("Czas zakończenia");
    private JTextField endingTimeTextField = new JTextField("HH:MM:SS", 16);
    private JLabel movieIdLabel = new JLabel("ID filmu");
    private JTextField movieIdTextField = new JTextField(5);
    private JLabel cinemaHallIDLabel = new JLabel("Sala ");
    private JTextField cinemaHallIDTextField = new JTextField(5);
    private JButton addScheduleButton = new JButton("Dodaj harmonogram");
    private Date beginTime, endTime;

    @Autowired
    public AddScheduleWindow(iScheduleService scheduleService, @Value("${property.repertoireID:0}") int repertoireID){
        super("Dodawanie harmonogramu dla filmu");
        this.scheduleService = scheduleService;
        this.repertoireID = repertoireID;
        initLayout();
        initComponents();
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
        addScheduleButton.addActionListener(e -> {addScheduleActionListener();});
    }

    private void addScheduleActionListener() {
        String begin = beginningTimeTextField.getText();
        String end = endingTimeTextField.getText();
        int movieID;
        int cinemaHallID;
        try {
            movieID = Integer.parseInt(movieIdTextField.getText());
            cinemaHallID = Integer.parseInt(cinemaHallIDTextField.getText());
        } catch(NumberFormatException nfe) {
            JOptionPane.showMessageDialog(null, "Zły format film/sala ID");
            return;
        }
        try{
            beginTime = new SimpleDateFormat("HH:mm:ss").parse(begin);
            endTime = new SimpleDateFormat("HH:mm:ss").parse(end);
        } catch(Exception ex) {
            System.out.println(ex.getMessage());
            return;
        }
        beginTime.setHours(beginTime.getHours() + 1);
        endTime.setHours(endTime.getHours() + 1);
        //same explanation as in FixTicketWindow changeHourActionListener function
        scheduleService.addScheduleForMovie(beginTime, endTime, movieID, repertoireID, cinemaHallID);
        this.setVisible(false);
    }

    private void initLayout() {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.WEST;
        panel.add(beginningTimeLabel, constraints);

        constraints.gridx = 1;
        constraints.gridwidth = 1;
        panel.add(beginningTimeTextField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(endingTimeLabel, constraints);

        constraints.gridx = 1;
        panel.add(endingTimeTextField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(movieIdLabel, constraints);

        constraints.gridx = 1;
        panel.add(movieIdTextField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        panel.add(cinemaHallIDLabel, constraints);

        constraints.gridx = 1;
        panel.add(cinemaHallIDTextField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(addScheduleButton, constraints);

        this.add(panel);
    }

}
