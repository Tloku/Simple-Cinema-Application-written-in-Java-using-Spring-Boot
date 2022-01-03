package com.Application.springbootapp.Services;

import com.Application.springbootapp.Entities.Bilet;
import com.Application.springbootapp.Repositories.BiletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BiletService implements iBiletService {

    @Autowired
    private BiletRepository biletRepository;

    @Override
    public List<Bilet> findTicketsByOrderID(int order_ID) {
        System.out.println("Wejszłem do metody findTicketsByorderID");
        List<Bilet> tickets = biletRepository.findByOrderID(order_ID);
        System.out.println("Znalazlem i wyjszlem");
        return tickets;
    }

    @Override
    public void deleteTicketByTicketID(int ticketID) {
        System.out.println("Wejszłem do metody findTicketsByorderID");
        biletRepository.deleteTicketByID(ticketID);
        System.out.println("Znalazlem i wyjszlem");
    }

    @Override
    public void changeTicketBeginTimeByTicketID(int ticketID, Date sdf) {
        System.out.println("Wejszłem do metody changeTicketBeginTimeByTicketID");
        biletRepository.changeTicketBeginTimeByID(ticketID, sdf);
        System.out.println("Znalazlem i wyjszlem");
    }

    @Override
    public void addTicket(Date beginTime, int sitNumber, int orderID, int cinemaHallID, int movieID, int userID) {
        System.out.println("Wejszłem do metody addTicket");
        biletRepository.addTicket(beginTime, sitNumber, orderID, cinemaHallID, movieID, userID);
        System.out.println("Znalazlem i wyjszlem");
    }

    @Override
    public String findMovieTitleByTicketID(int ticketID) {
        return biletRepository.findMovieTitleByTicketID(ticketID);
    }
}
