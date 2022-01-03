package com.Application.springbootapp.WindowApp;

import com.Application.springbootapp.Entities.Bilet;
import com.Application.springbootapp.Entities.Film;
import com.Application.springbootapp.Services.iBiletService;
import com.Application.springbootapp.Services.iFilmService;
import com.Application.springbootapp.Services.iHarmonogramService;
import com.Application.springbootapp.Services.iRepertuarKinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class ShowOrderWindow extends JFrame {
    private int winWidth = 800;
    private int winHeight = 200;
    JPanel panel = new JPanel();
    private iBiletService biletService;
    private iFilmService filmService;
    private iRepertuarKinaService repertuarKinaService;
    private int orderID;
    private String[] orderTableColumnNames = {"Bilet_id","Tytuł","Data", "Godzina_rozpoczęcia"};
    private JTable ordersTable;
    private DefaultTableModel ordersTableModel = new DefaultTableModel(null, orderTableColumnNames);

    @Autowired
    public ShowOrderWindow(iBiletService biletService, iFilmService filmService, iRepertuarKinaService repertuarKinaService,
                           @Value("${property.orderID:0}") int orderID) {
            super("Koszyk");
            this.biletService = biletService;
            this.filmService = filmService;
            this.repertuarKinaService = repertuarKinaService;
            this.orderID = orderID;
            initComponents();
            initTableData();
    }

    private void initComponents() {
        this.setLayout(null);
        this.setSize(winWidth,winHeight);
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.setLocation((width - winWidth) / 2, (height - winHeight) / 2);
        this.setResizable(false);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(false);
        panel.setBounds(0, 0, winWidth, winHeight);
        ordersTable = new JTable(ordersTableModel);
        ordersTable.setEnabled(false);
        ordersTable.setPreferredScrollableViewportSize(new Dimension(750, 100));
        panel.add(new JScrollPane(ordersTable));
        this.add(panel);
    }

    private void initTableData() {
        List<Bilet> ticketsList = biletService.findTicketsByOrderID(orderID);
        List<String> moviesList = new ArrayList<>();
        List<Date> datesList = new ArrayList<>();
        for(Bilet ticket : ticketsList){
            moviesList.add(biletService.findMovieTitleByTicketID(ticket.getBiletID()));
        }

        for(int i = 0; i < moviesList.size(); i++) {
            Film film = filmService.findByTitle(moviesList.get(i));
            datesList.add(filmService.findRepertoireDateByMovieIDAndTicketID(film.getFilmID(), ticketsList.get(i).getBiletID()));
        }

        for(int i = 0; i < ticketsList.size(); i++){
            ordersTableModel.addRow(new Object[] {
                    ticketsList.get(i).getBiletID(),
                    moviesList.get(i),
                    datesList.get(i),
                    ticketsList.get(i).getGodzinaRozpoczecia()
            });
        }
        ordersTable.repaint();
    }
}
