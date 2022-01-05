package com.Application.springbootapp.WindowApp;

import com.Application.springbootapp.Entities.Ticket;
import com.Application.springbootapp.Entities.Movie;
import com.Application.springbootapp.Services.iTicketService;
import com.Application.springbootapp.Services.iMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class ShowOrderWindow extends JFrame {
    private int winWidth = 800;
    private int winHeight = 200;
    JPanel panel = new JPanel();
    private iTicketService ticketService;
    private iMovieService movieService;
    private int orderID;
    private String[] orderTableColumnNames = {"Bilet_id","Tytuł","Data", "Godzina_rozpoczęcia"};
    private JTable ordersTable;
    private DefaultTableModel ordersTableModel = new DefaultTableModel(null, orderTableColumnNames);

    @Autowired
    public ShowOrderWindow(iTicketService ticketService, iMovieService movieService,
                           @Value("${property.orderID:0}") int orderID) {
            super("Koszyk");
            this.ticketService = ticketService;
            this.movieService = movieService;
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
        List<Ticket> ticketsList = ticketService.findTicketsByOrderID(orderID);
        List<String> moviesList = new ArrayList<>();
        List<Date> datesList = new ArrayList<>();
        for(Ticket ticket : ticketsList){
            moviesList.add(ticketService.findMovieTitleByTicketID(ticket.getTicketID()));
        }

        for(int i = 0; i < moviesList.size(); i++) {
            Movie movie = movieService.findByTitle(moviesList.get(i));
            datesList.add(movieService.findRepertoireDateByMovieIDAndTicketID(movie.getMovieID(), ticketsList.get(i).getTicketID()));
        }

        for(int i = 0; i < ticketsList.size(); i++){
            ordersTableModel.addRow(new Object[] {
                    ticketsList.get(i).getTicketID(),
                    moviesList.get(i),
                    datesList.get(i),
                    ticketsList.get(i).getBeginTime()
            });
        }
        ordersTable.repaint();
    }
}
