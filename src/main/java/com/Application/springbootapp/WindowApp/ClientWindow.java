package com.Application.springbootapp.WindowApp;

import com.Application.springbootapp.Entities.Movie;
import com.Application.springbootapp.Entities.Schedule;
import com.Application.springbootapp.Entities.Repertoire;
import com.Application.springbootapp.Entities.User;
import com.Application.springbootapp.Services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

@Controller
public class ClientWindow extends JFrame {
    private iOrderService orderService;
    private iTicketService ticketService;
    private iMovieService movieService;
    private iScheduleService scheduleService;
    private iRepertoireService repertoireService;
    private iUserService userService;
    private int userID;
    private int winWidth = 800;
    private int winHeight = 300;
    private final float ORDER_VALUE = 20;

    private List<Repertoire> cinemaRepertoiresList;
    private Vector<String> cinemaRepertoireStrings = new Vector<>();
    JPanel panel = new JPanel();
    private String[] moviesTableColumnNames = {"Film_id","Tytuł","Harmonogram_ID","Godzina_rozpoczecia","Godzina_zakończenia"};
    private JTable moviesTable;
    private DefaultTableModel moviesTableModel;
    private JComboBox repertoireComboBox = new JComboBox();;
    private JLabel buyTicketLabel = new JLabel("Kup Bilet:");
    private JTextField buyTicketTextField = new JTextField("harmonogram_ID", 7);
    private JButton buyTicketButton = new JButton("Kup!");
    private JButton showOrderButton = new JButton("Pokaż zamówienie");
    private JButton changePasswordButton = new JButton("Zmień hasło");
    private int repertoireID;
    private int orderID = 0;
    private int scheduleID;
    int orderCost = 0;
    List<Movie> moviesList;
    List<Schedule> moviesData = new ArrayList<>();
    List<Integer> schedulesIDList = new ArrayList<>();
    Date date = new Date();
    private EmailSenderService emailSenderService;

    @Autowired
    public ClientWindow(iOrderService orderService, iTicketService ticketService, iMovieService movieService,
                        iScheduleService scheduleService, iRepertoireService repertoireService, iUserService userService,
                        @Value("${property.userID:0}")int userID, EmailSenderService emailSenderService) {
        super("Okno klienta");
        this.orderService = orderService;
        this.ticketService = ticketService;
        this.movieService = movieService;
        this.scheduleService = scheduleService;
        this.userService = userService;
        this.repertoireService = repertoireService;
        this.userID = userID;
        this.emailSenderService = emailSenderService;
        initComponents();
        initLayout();
    }

    private void initComponents() {
        this.setLayout(null);
        this.setSize(winWidth,winHeight);
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.setLocation((width - winWidth) / 2, (height - winHeight) / 2);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        initComboBox();
        showOrderButton.setEnabled(false);
        repertoireComboBox.addActionListener(e -> {repertoireComboBoxActionListener();});
        buyTicketButton.addActionListener(e -> {buyTicketButtonActionListener();});
        showOrderButton.addActionListener(e -> {showOrderButtonActionListener();});
        changePasswordButton.addActionListener(e -> {changePasswordButtonActionListener();});
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                User user = userService.findUserByID(userID);
                if(orderID != 0) {
                    JOptionPane.showMessageDialog(null, "Zamykanie potrwa trochę dłużej," +
                            " kompletujemy twoje zamówienie oraz wysyłamy maila.");
                    emailSenderService.sendEmail(user.getEmail(), "Twoje zamówienie o nr " +orderID,
                            "Dziękujęmy za zakup biletów w naszym kinie.\n" +
                                    "Życzymy miłego seansu! :)\n" +
                                    "Ponizej znadjduje się plik pdf z twoimi biletami\n" +
                                    "*plik*");
                }

            }
        });
    }

    private void showOrderButtonActionListener() {
        ShowOrderWindow showOrderWindow = new ShowOrderWindow(ticketService,
                movieService, orderID);
        showOrderWindow.setVisible(true);
    }

    private void initLayout() {
        moviesTableModel = new DefaultTableModel(null, moviesTableColumnNames);
        moviesTable = new JTable(moviesTableModel);
        moviesTable.setPreferredScrollableViewportSize(new Dimension(750, 100));
        moviesTable.setEnabled(false);
        buyTicketButton.setEnabled(false);
        panel.setBounds(0, 0, winWidth, winHeight);
        panel.add(new JScrollPane(moviesTable));
        panel.add(repertoireComboBox);
        panel.add(buyTicketLabel);
        panel.add(buyTicketTextField);
        panel.add(buyTicketButton);
        panel.add(showOrderButton);
        panel.add(changePasswordButton);
        this.add(panel);
    }

    private void buyTicketButtonActionListener() {
        Random rand = new Random();
        try{
            scheduleID = Integer.parseInt(buyTicketTextField.getText());
        } catch(NumberFormatException nfe) {
            JOptionPane.showMessageDialog(null, "Nie podano prawidłowej wartości w harmonogramID");
            return;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD");

        if(schedulesIDList.contains(scheduleID)) {
            if(orderID == 0){
                orderService.addOrder(date, 0, "Karta", userID);
                orderID = orderService.findOrderIDByDateAndUserID(sdf.format(date), userID);
            }
            Schedule schedule = scheduleService.findScheduleByID(scheduleID);
            int movieID = movieService.findByScheduleID(scheduleID);
            Date scheduleTime = schedule.getBeginTime();
            orderCost += ORDER_VALUE;
            ticketService.addTicket(scheduleTime, rand.nextInt(100)+1, orderID,
                    rand.nextInt(5) + 1, movieID, userID);
            orderService.updateOrder(orderCost, orderID);
            showOrderButton.setEnabled(true);
        } else {
            JOptionPane.showMessageDialog(null,"Podanego ID nie ma w harmonogramie dla tego repertuaru");
        }
    }

    private void repertoireComboBoxActionListener() {
        String selectedItem = (String) repertoireComboBox.getSelectedItem();
        boolean isNumeric = false;
        try {
            repertoireID = Integer.parseInt(String.valueOf(selectedItem.charAt(0)));
            isNumeric = true;
        } catch(NumberFormatException ex) {
            System.out.println(ex.getMessage());
            isNumeric = false;
        }
        if(isNumeric) {
            moviesList = movieService.findAllByRepertoireID(repertoireID);
        }

        List<Schedule> schedulesForOneMovie;
        moviesData.clear();
        moviesTableModel.setRowCount(0);

        for(Movie movie : moviesList) {
            schedulesForOneMovie = scheduleService.findAllByMovieID(movie.getMovieID());
            for(Schedule schedule : schedulesForOneMovie) {
                Movie tmpMovie = new Movie();
                tmpMovie.setMovieID(movie.getMovieID());
                tmpMovie.setTitle(movie.getTitle());
                schedule.setFilm(tmpMovie);
                moviesData.add(schedule);
            }
        }

        addMoviesToTable();
        buyTicketButton.setEnabled(true & isNumeric);
    }

    private void addMoviesToTable() {
        schedulesIDList.clear();
        for(Schedule movie : moviesData){
            moviesTableModel.addRow(new Object[] {
                    movie.getFilm().getMovieID(),
                    movie.getFilm().getTitle(),
                    movie.getScheduleID(),
                    movie.getBeginTime(),
                    movie.getEndTime() });
            schedulesIDList.add(movie.getScheduleID());
        }
        moviesTable.repaint();
    }

    private void initComboBox() {
        cinemaRepertoiresList = repertoireService.findAllRepertoires();
        cinemaRepertoireStrings.add("ID/Data");
        for(Repertoire repertoire : cinemaRepertoiresList) {
            cinemaRepertoireStrings.add(repertoire.getRepertoireID() + ". " + repertoire.getDate());
        }
        repertoireComboBox = new JComboBox(cinemaRepertoireStrings);
    }

    private void changePasswordButtonActionListener() {
        ChangePasswordWindow changePasswordWindow = new ChangePasswordWindow(userService, userID);
        changePasswordWindow.setVisible(true);
    }
}
