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
public class EmployeeWindow  extends JFrame {
    private JTabbedPane tabbedPane = new JTabbedPane();
    private JPanel fixPanel = new JPanel();
    private JPanel modifyRepertoirePanel = new JPanel();
    private int winWidth = 800;
    private int winHeight = 300;
    private iZamówienieService zamowienieService;
    private iBiletService biletService;
    private iFilmService filmService;
    private iHarmonogramService harmonogramService;
    private iUżytkownikService uzytkownikService;
    private iRepertuarKinaService repertuarKinaService;
    private iGatunekService gatunekService;

    private JTable ordersTable;
    private JLabel userEmailLabel = new JLabel("Email użytkownika: ");
    private JTextField userEmailTextField = new JTextField(16);
    private JButton fixTicketButton = new JButton("Zmień bilet");
    public JButton findByEmailButton = new JButton("Wyświetl zamówienia");
    private JLabel ticketIDLabel = new JLabel("Podaj ID biletu do zmiany: ");
    private JTextField ticketIDTextField = new JTextField(5);
    private String[] ordersTableColumnNames = {"Zamówienie_ID", "Data zamówienia", "Bilet_ID", "Godzina_rozpoczęcia"};
    private DefaultTableModel ordersTableModel;
    List<Zamówienie> ordersList;
    List<Bilet> ticketsList = new ArrayList<>();

    private List<RepertuarKina> cinemaRepertoiresList;
    private Vector<String> cinemaRepertoireStrings = new Vector<>();
    private JTable moviesTable;
    private JComboBox repertoireComboBox = new JComboBox();
    private JButton addFilmButton = new JButton("Dodaj film");
    private JButton addScheduleButton = new JButton("Dodaj harmonogram dla filmu");
    private JButton deleteMovieButton = new JButton("Usuń film");
    private JButton modifyTimeButton = new JButton("Zmień godzine filmu w harmonogramie");
    private String[] moviesTableColumnNames = {"Film_id","Tytuł","Harmonogram_ID","Godzina_rozpoczecia","Godzina_zakończenia"};
    private JTextField movieIDTextField = new JTextField("film id",5);
    private DefaultTableModel moviesTableModel;
    public int ticketID;
    private int repertoireID;
    List<Film> moviesList;
    List<Harmonogram> moviesData = new ArrayList<>();

    @Autowired
    EmployeeWindow(iZamówienieService zamowienieService, iBiletService biletService, iFilmService filmService,
                   iHarmonogramService harmonogramService, iUżytkownikService uzytkownikService,
                   iRepertuarKinaService repertuarKinaService, iGatunekService gatunekService) {
        super("Employee Window");

        this.zamowienieService = zamowienieService;
        this.biletService = biletService;
        this.filmService = filmService;
        this.harmonogramService = harmonogramService;
        this.uzytkownikService = uzytkownikService;
        this.repertuarKinaService = repertuarKinaService;
        this.gatunekService = gatunekService;

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
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        tabbedPane.setBounds(0, 0, winWidth ,winHeight);
        tabbedPane.add("Fix Errors", fixPanel);
        tabbedPane.add("Modify", modifyRepertoirePanel);
        initComboBox();
        this.add(tabbedPane);
    }

    private void initComboBox() {
        cinemaRepertoiresList = repertuarKinaService.findAllRepertoires();
        cinemaRepertoireStrings.add("ID/Data");
        for(int i = 0; i < cinemaRepertoiresList.size(); i++) {
            cinemaRepertoireStrings.add(cinemaRepertoiresList.get(i).getRepertuarKinaID() + ". "+ cinemaRepertoiresList.get(i).getData());
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

        findByEmailButton.addActionListener(e -> {
            findByEmailButtonActionListener();
        });

        fixTicketButton.addActionListener(e -> {
            fixBiletButtonActionListener();
        });
    }

    private void findByEmailButtonActionListener() {
        String email = userEmailTextField.getText();

        if(!email.isBlank() || !email.isEmpty()) {
            ordersList = zamowienieService.findOrderByEmail(email);
            List<Bilet> ticketsFromOneOrder;
            ticketsList.clear();
            ordersTableModel.setRowCount(0);

            for(int i = 0; i < ordersList.size(); i++) {
                ticketsFromOneOrder = biletService.findTicketsByOrderID(ordersList.get(i).getZamowienieID());
                for(int j = 0; j < ticketsFromOneOrder.size(); j++) {
                    Zamówienie zamowienie = new Zamówienie();
                    zamowienie.setZamowienieID(ordersList.get(i).getZamowienieID());
                    zamowienie.setDataZamowienia(ordersList.get(i).getDataZamowienia());
                    ticketsFromOneOrder.get(j).setZamowienie(zamowienie);
                    ticketsList.add(ticketsFromOneOrder.get(j));
                }
            }
            addTicketDataToTable();
            fixTicketButton.setEnabled(true);
        } else {
            JOptionPane.showMessageDialog(null, "Nie podano mail'a");
        }
    }

    private void fixBiletButtonActionListener() {
        try{
            ticketID = Integer.parseInt(ticketIDTextField.getText());
        } catch(NumberFormatException nfe){
            JOptionPane.showMessageDialog(null, "Podano zły format danych");
            return;
        }

        if(checkIfNumberIsSomeTicketID()){
            FixTicketWindow fixBiletWindow = new FixTicketWindow(biletService, ticketID, findByEmailButton);
            fixBiletWindow.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Użytkownik nie ma biletu o takim ID");
            return;
        }
    }

    private boolean checkIfNumberIsSomeTicketID() {
        List<Integer> ticketIDs = new ArrayList<>();
        for(Bilet ticket : ticketsList){
            ticketIDs.add(ticket.getBiletID());
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
        addFilmButton.setEnabled(false);
        deleteMovieButton.setEnabled(false);
        modifyTimeButton.setEnabled(false);

        modifyRepertoirePanel.add(new JScrollPane(moviesTable));
        modifyRepertoirePanel.add(repertoireComboBox);
        modifyRepertoirePanel.add(addFilmButton);
        modifyRepertoirePanel.add(addScheduleButton);
        modifyRepertoirePanel.add(movieIDTextField);
        modifyRepertoirePanel.add(deleteMovieButton);
        modifyRepertoirePanel.add(modifyTimeButton);

        repertoireComboBox.addActionListener(e -> {repertoireComboBoxActionListener();});
        addFilmButton.addActionListener(e -> {addMovieButtonActionListener();});
        addScheduleButton.addActionListener(e -> {addScheduleButtonActionListener();});
        deleteMovieButton.addActionListener(e -> {deleteMovieButtonActionListener();});
        modifyTimeButton.addActionListener(e -> {modifyTimeButtonActionListener();});
    }

    private void modifyTimeButtonActionListener() {
        ChangeScheduleTimeWindow changeScheduleTimeWindow = new ChangeScheduleTimeWindow(harmonogramService);
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
        harmonogramService.deleteScheduleByMovieID(movieID);
        filmService.deleteByID(movieID);
    }

    private void addScheduleButtonActionListener() {
        AddScheduleWindow addScheduleWindow = new AddScheduleWindow(harmonogramService, repertoireID);
        addScheduleWindow.setVisible(true);
    }

    private void addMovieButtonActionListener() {
        AddMovieWindow addMovieWindow = new AddMovieWindow(filmService, gatunekService,repertoireID);
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
            addFilmButton.setEnabled(true);
            addScheduleButton.setEnabled(true);
            deleteMovieButton.setEnabled(true);
            modifyTimeButton.setEnabled(true);
        } else {
            addFilmButton.setEnabled(false);
            addScheduleButton.setEnabled(false);
            deleteMovieButton.setEnabled(false);
            modifyTimeButton.setEnabled(false);
        }
    }

    private void addTicketDataToTable() {
        for(Bilet ticket : ticketsList){
            ordersTableModel.addRow(new Object[] {
                    ticket.getZamowienie().getZamowienieID(),
                    ticket.getZamowienie().getDataZamowienia(),
                    ticket.getBiletID(),
                    ticket.getGodzinaRozpoczecia() });
        }
        ordersTable.repaint();
    }

    private void addMoviesToTable() {
        for(Harmonogram movie : moviesData){
            moviesTableModel.addRow(new Object[] {
                    movie.getFilm().getFilmID(),
                    movie.getFilm().getTytul(),
                    movie.getHarmonogramID(),
                    movie.getGodzinaStartu(),
                    movie.getGodzinaZakonczenia() });
        }
        moviesTable.repaint();
    }
}
