package com.Application.springbootapp.WindowApp;

import com.Application.springbootapp.Entities.Film;
import com.Application.springbootapp.Entities.Harmonogram;
import com.Application.springbootapp.Entities.RepertuarKina;
import com.Application.springbootapp.Services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
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
    private int userID;
    private int winWidth = 800;
    private int winHeight = 300;
    private final float ORDER_VALUE = 20;

    private List<RepertuarKina> cinemaRepertoiresList;
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
    private int repertoireID;
    private int orderID = 0;
    private int scheduleID;
    int orderCost = 0;
    List<Film> moviesList;
    List<Harmonogram> moviesData = new ArrayList<>();
    List<Integer> schedulesIDList = new ArrayList<>();
    Date date = new Date();

    @Autowired
    public ClientWindow(iOrderService orderService, iTicketService ticketService, iMovieService movieService,
                        iScheduleService scheduleService, iRepertoireService repertoireService,
                        @Value("${property.userID:0}")int userID) {
        super("Okno klienta");
        this.orderService = orderService;
        this.ticketService = ticketService;
        this.movieService = movieService;
        this.scheduleService = scheduleService;
        this.repertoireService = repertoireService;
        this.userID = userID;
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
            Harmonogram schedule = scheduleService.findScheduleByID(scheduleID);
            int movieID = movieService.findByScheduleID(scheduleID);
            Date scheduleTime = schedule.getGodzinaStartu();
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

        List<Harmonogram> schedulesForOneMovie;
        moviesData.clear();
        moviesTableModel.setRowCount(0);

        for(Film movie : moviesList) {
            schedulesForOneMovie = scheduleService.findAllByMovieID(movie.getFilmID());
            for(Harmonogram schedule : schedulesForOneMovie) {
                Film tmpMovie = new Film();
                tmpMovie.setFilmID(movie.getFilmID());
                tmpMovie.setTytul(movie.getTytul());
                schedule.setFilm(tmpMovie);
                moviesData.add(schedule);
            }
        }

        addMoviesToTable();
        buyTicketButton.setEnabled(true & isNumeric);
    }

    private void addMoviesToTable() {
        schedulesIDList.clear();
        for(Harmonogram movie : moviesData){
            moviesTableModel.addRow(new Object[] {
                    movie.getFilm().getFilmID(),
                    movie.getFilm().getTytul(),
                    movie.getHarmonogramID(),
                    movie.getGodzinaStartu(),
                    movie.getGodzinaZakonczenia() });
            schedulesIDList.add(movie.getHarmonogramID());
        }
        moviesTable.repaint();
    }

    private void initComboBox() {
        cinemaRepertoiresList = repertoireService.findAllRepertoires();
        cinemaRepertoireStrings.add("ID/Data");
        for(RepertuarKina repertoire : cinemaRepertoiresList) {
            cinemaRepertoireStrings.add(repertoire.getRepertuarKinaID() + ". " + repertoire.getData());
        }
        repertoireComboBox = new JComboBox(cinemaRepertoireStrings);
    }

}
