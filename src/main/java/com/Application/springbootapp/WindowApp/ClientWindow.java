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
    private iZamówienieService zamowienieService;
    private iBiletService biletService;
    private iFilmService filmService;
    private iHarmonogramService harmonogramService;
    private iRepertuarKinaService repertuarKinaService;
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
    public ClientWindow(iZamówienieService zamowienieService, iBiletService biletService, iFilmService filmService,
                        iHarmonogramService harmonogramService, iRepertuarKinaService repertuarKinaService,
                        @Value("${property.userID:0}")int userID) {
        super("Okno klienta");
        this.zamowienieService = zamowienieService;
        this.biletService = biletService;
        this.filmService = filmService;
        this.harmonogramService = harmonogramService;
        this.repertuarKinaService = repertuarKinaService;
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
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        initComboBox();
        showOrderButton.setEnabled(false);
        repertoireComboBox.addActionListener(e -> {repertoireComboBoxActionListener();});
        buyTicketButton.addActionListener(e -> {buyTicketButtonActionListener();});
        showOrderButton.addActionListener(e -> {showOrderButtonActionListener();});
    }

    private void showOrderButtonActionListener() {
        ShowOrderWindow showOrderWindow = new ShowOrderWindow(biletService,
                filmService, repertuarKinaService, orderID);
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
                zamowienieService.addOrder(date, 0, "Karta", userID);
                orderID = zamowienieService.findOrderIDByDateAndUserID(sdf.format(date), userID);
            }
            Harmonogram schedule = harmonogramService.findScheduleByID(scheduleID);
            int movieID = filmService.findByScheduleID(scheduleID);
            Date scheduleTime = schedule.getGodzinaStartu();
            orderCost += ORDER_VALUE;
            biletService.addTicket(scheduleTime, rand.nextInt(100)+1, orderID, rand.nextInt(5) + 1, movieID, userID);
            zamowienieService.updateOrder(orderCost, orderID);
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
            moviesList = filmService.findAllByRepertoireID(repertoireID);
        }

        List<Harmonogram> schedulesForOneMovie;
        moviesData.clear();
        moviesTableModel.setRowCount(0);

        for(int i = 0; i < moviesList.size(); i++) {
            schedulesForOneMovie = harmonogramService.findAllByMovieID(moviesList.get(i).getFilmID());
            for(int j = 0; j < schedulesForOneMovie.size(); j++) {
                Film movie = new Film();
                movie.setFilmID(moviesList.get(i).getFilmID());
                movie.setTytul(moviesList.get(i).getTytul());
                schedulesForOneMovie.get(j).setFilm(movie);
                moviesData.add(schedulesForOneMovie.get(j));
            }
        }
        addMoviesToTable();

        if(isNumeric) {
            buyTicketButton.setEnabled(true);
        } else {
            buyTicketButton.setEnabled(false);
        }
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
        cinemaRepertoiresList = repertuarKinaService.findAllRepertoires();
        cinemaRepertoireStrings.add("ID/Data");
        for(int i = 0; i < cinemaRepertoiresList.size(); i++) {
            cinemaRepertoireStrings.add(cinemaRepertoiresList.get(i).getRepertuarKinaID() + ". "
                    + cinemaRepertoiresList.get(i).getData());
        }
        repertoireComboBox = new JComboBox(cinemaRepertoireStrings);
    }

}
