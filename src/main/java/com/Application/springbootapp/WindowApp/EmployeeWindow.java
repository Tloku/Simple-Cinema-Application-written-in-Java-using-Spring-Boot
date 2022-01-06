package com.Application.springbootapp.WindowApp;

import com.Application.springbootapp.Entities.*;
import com.Application.springbootapp.Services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


@Controller
public class EmployeeWindow extends JFrame {
    private JTabbedPane tabbedPane = new JTabbedPane();
    private JPanel fixPanel = new JPanel();
    private JPanel modifyRepertoirePanel = new JPanel();
    private int winWidth = 800;
    private int winHeight = 300;
    private iOrderService orderService;
    private iTicketService ticketService;
    private iMovieService movieService;
    private iScheduleService scheduleService;
    private iRepertoireService repertoireService;
    private iCategoryService categoryService;

    private JTable ordersTable;
    private JLabel userEmailLabel = new JLabel("Email użytkownika: ");
    private JTextField userEmailTextField = new JTextField(16);
    private JButton fixTicketButton = new JButton("Zmień bilet");
    public JButton findByEmailButton = new JButton("Wyświetl zamówienia");
    private JLabel ticketIDLabel = new JLabel("Podaj ID biletu do zmiany: ");
    private JTextField ticketIDTextField = new JTextField(5);
    private String[] ordersTableColumnNames = {"Zamówienie_ID", "Data zamówienia", "Bilet_ID", "Godzina_rozpoczęcia"};
    private DefaultTableModel ordersTableModel;
    List<Order> ordersList;
    List<Ticket> ticketsList = new ArrayList<>();

    private List<Repertoire> cinemaRepertoiresList;
    private Vector<String> cinemaRepertoireStrings = new Vector<>();
    private JTable moviesTable;
    private JComboBox repertoireComboBox = new JComboBox();
    private JButton addMovieButton = new JButton("Dodaj film");
    private JButton addScheduleButton = new JButton("Dodaj harmonogram dla filmu");
    private JButton deleteMovieButton = new JButton("Usuń film");
    private JButton modifyTimeButton = new JButton("Zmień godzine filmu w harmonogramie");
    private String[] moviesTableColumnNames = {"Film_id","Tytuł","Harmonogram_ID","Godzina_rozpoczecia","Godzina_zakończenia"};
    private JTextField movieIDTextField = new JTextField("film id",5);
    private DefaultTableModel moviesTableModel;
    public int ticketID;
    private int repertoireID;
    List<Movie> moviesList;
    List<Schedule> moviesData = new ArrayList<>();

    @Autowired
    EmployeeWindow(iOrderService orderService, iTicketService ticketService, iMovieService movieService,
                   iScheduleService scheduleService, iRepertoireService repertoireService,
                   iCategoryService categoryService) {
        super("Employee Window");

        this.orderService = orderService;
        this.ticketService = ticketService;
        this.movieService = movieService;
        this.scheduleService = scheduleService;
        this.repertoireService = repertoireService;
        this.categoryService = categoryService;

        initComponents();
        initFixLayout();
        initModifyLayout();
    }

    private void initComponents() {
        this.setLayout(null);
        this.setSize(winWidth,winHeight);
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.setLocation((width - winWidth) / 2, (height - winHeight) / 2);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        tabbedPane.setBounds(0, 0, winWidth ,winHeight);
        tabbedPane.add("Fix Errors", fixPanel);
        tabbedPane.add("Modify", modifyRepertoirePanel);
        initComboBox();
        this.add(tabbedPane);
    }

    private void initComboBox() {
        cinemaRepertoiresList = repertoireService.findAllRepertoires();
        cinemaRepertoireStrings.add("ID/Data");
        for(Repertoire cinemaRepertoire : cinemaRepertoiresList) {
            cinemaRepertoireStrings.add(cinemaRepertoire.getRepertoireID() + ". " + cinemaRepertoire.getDate());
        }
        repertoireComboBox = new JComboBox(cinemaRepertoireStrings);
    }

    private void initFixLayout() {
        fixPanel.setBounds(0, 0, winWidth, winHeight);
        fixTicketButton.setEnabled(false);
        ordersTableModel = new DefaultTableModel(null, ordersTableColumnNames);
        ordersTable = new JTable(ordersTableModel);
        ordersTable.setPreferredScrollableViewportSize(new Dimension(700, 100));
        ordersTable.setEnabled(false);
        fixPanel.add(new JScrollPane(ordersTable));
        fixPanel.add(userEmailLabel);
        fixPanel.add(userEmailTextField);
        fixPanel.add(findByEmailButton);
        fixPanel.add(ticketIDLabel);
        fixPanel.add(ticketIDTextField);
        fixPanel.add(fixTicketButton);

        findByEmailButton.addActionListener(e -> {findByEmailButtonActionListener();});
        fixTicketButton.addActionListener(e -> {fixBiletButtonActionListener();});
    }

    private void findByEmailButtonActionListener() {
        String email = userEmailTextField.getText();

        if(!email.isBlank() || !email.isEmpty()) {
            ordersList = orderService.findOrderByEmail(email);
            List<Ticket> ticketsFromOneOrder;
            ticketsList.clear();
            ordersTableModel.setRowCount(0);

            for(Order order : ordersList) {
                ticketsFromOneOrder = ticketService.findTicketsByOrderID(order.getOrderID());
                for(Ticket ticket : ticketsFromOneOrder) {
                    Order tmpOrder = new Order();
                    tmpOrder.setOrderID(order.getOrderID());
                    tmpOrder.setOrderDate(order.getOrderDate());
                    ticket.setOrder(tmpOrder);
                    ticketsList.add(ticket);
                }
            }
            addTicketDataToTable();
            fixTicketButton.setEnabled(true);
        } else {
            JOptionPane.showMessageDialog(null, "Nie podano mail'a");
        }
    }

    private void addTicketDataToTable() {
        for(Ticket ticket : ticketsList){
            ordersTableModel.addRow(new Object[] {
                    ticket.getOrder().getOrderID(),
                    ticket.getOrder().getOrderDate(),
                    ticket.getTicketID(),
                    ticket.getBeginTime() });
        }
        ordersTable.repaint();
    }

    private void addMoviesToTable() {
        for(Schedule movie : moviesData){
            moviesTableModel.addRow(new Object[] {
                    movie.getFilm().getMovieID(),
                    movie.getFilm().getTitle(),
                    movie.getScheduleID(),
                    movie.getBeginTime(),
                    movie.getEndTime() });
        }
        moviesTable.repaint();
    }

    private void fixBiletButtonActionListener() {
        try{
            ticketID = Integer.parseInt(ticketIDTextField.getText());
        } catch(NumberFormatException nfe){
            JOptionPane.showMessageDialog(null, "Podano zły format danych");
            return;
        }

        if(checkIfNumberIsSomeTicketID()){
            FixTicketWindow fixBiletWindow = new FixTicketWindow(ticketService, ticketID, findByEmailButton);
            fixBiletWindow.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Użytkownik nie ma biletu o takim ID");
            return;
        }
    }

    private boolean checkIfNumberIsSomeTicketID() {
        List<Integer> ticketIDs = new ArrayList<>();
        for(Ticket ticket : ticketsList){
            ticketIDs.add(ticket.getTicketID());
        }
        return ticketIDs.contains(ticketID);
    }

    private void initModifyLayout() {
        modifyRepertoirePanel.setBounds(0, 0, winWidth, winHeight);;
        moviesTableModel = new DefaultTableModel(null, moviesTableColumnNames);
        moviesTable = new JTable(moviesTableModel);

        moviesTable.setPreferredScrollableViewportSize(new Dimension(750, 100));
        moviesTable.setEnabled(false);
        addScheduleButton.setEnabled(false);
        addMovieButton.setEnabled(false);
        deleteMovieButton.setEnabled(false);
        modifyTimeButton.setEnabled(false);

        modifyRepertoirePanel.add(new JScrollPane(moviesTable));
        modifyRepertoirePanel.add(repertoireComboBox);
        modifyRepertoirePanel.add(addMovieButton);
        modifyRepertoirePanel.add(addScheduleButton);
        modifyRepertoirePanel.add(movieIDTextField);
        modifyRepertoirePanel.add(deleteMovieButton);
        modifyRepertoirePanel.add(modifyTimeButton);

        repertoireComboBox.addActionListener(e -> {repertoireComboBoxActionListener();});
        addMovieButton.addActionListener(e -> {addMovieButtonActionListener();});
        addScheduleButton.addActionListener(e -> {addScheduleButtonActionListener();});
        deleteMovieButton.addActionListener(e -> {deleteMovieButtonActionListener();});
        modifyTimeButton.addActionListener(e -> {modifyTimeButtonActionListener();});
    }

    private void modifyTimeButtonActionListener() {
        ChangeScheduleTimeWindow changeScheduleTimeWindow = new ChangeScheduleTimeWindow(scheduleService);
        changeScheduleTimeWindow.setVisible(true);
    }

    private void deleteMovieButtonActionListener() {
        int movieID;
        try {
            movieID = Integer.parseInt(movieIDTextField.getText());
        } catch(NumberFormatException nfe) {
            JOptionPane.showMessageDialog(null, "Zły format filmID");
            return;
        }
        int delete = JOptionPane.showConfirmDialog(null, "Czy na pewno chcesz usunąć ten film",
                "Usuwanie",
                JOptionPane.YES_NO_OPTION);
        if(delete == JOptionPane.YES_OPTION) {
            scheduleService.deleteScheduleByMovieID(movieID);
            movieService.deleteByID(movieID);
        }
    }

    private void addScheduleButtonActionListener() {
        AddScheduleWindow addScheduleWindow = new AddScheduleWindow(scheduleService, repertoireID);
        addScheduleWindow.setVisible(true);
    }

    private void addMovieButtonActionListener() {
        AddMovieWindow addMovieWindow = new AddMovieWindow(movieService, categoryService,repertoireID);
        addMovieWindow.setVisible(true);
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
        addMovieButton.setEnabled(true & isNumeric);
        addScheduleButton.setEnabled(true & isNumeric);
        deleteMovieButton.setEnabled(true & isNumeric);
        modifyTimeButton.setEnabled(true & isNumeric);
    }
}
