package com.Application.springbootapp.Services;

import com.Application.springbootapp.Entities.Ticket;
import com.Application.springbootapp.Repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TicketService implements iTicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Override
    public List<Ticket> findTicketsByOrderID(int order_ID) {
        return ticketRepository.findByOrderID(order_ID);
    }

    @Override
    public void deleteTicketByTicketID(int ticketID) {
        ticketRepository.deleteTicketByID(ticketID);
    }

    @Override
    public void changeTicketBeginTimeByTicketID(int ticketID, Date sdf) {
        ticketRepository.changeTicketBeginTimeByID(ticketID, sdf);
    }

    @Override
    public void addTicket(Date beginTime, int sitNumber, int orderID, int cinemaHallID, int movieID, int userID) {
        ticketRepository.addTicket(beginTime, sitNumber, orderID, cinemaHallID, movieID, userID);
    }

    @Override
    public String findMovieTitleByTicketID(int ticketID) {
        return ticketRepository.findMovieTitleByTicketID(ticketID);
    }
}
