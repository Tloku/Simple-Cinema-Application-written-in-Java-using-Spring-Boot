package com.Application.springbootapp.Services;

import com.Application.springbootapp.Entities.Ticket;
import java.util.Date;
import java.util.List;

public interface iTicketService {

    List<Ticket> findTicketsByOrderID(int order_ID);
    void deleteTicketByTicketID(int ticketID);
    void changeTicketBeginTimeByTicketID(int ticketID,  Date sdf);
    void addTicket(Date beginTime, int sitNumber,int orderID, int cinemaHallID,  int movieID, int userID);
    String findMovieTitleByTicketID(int ticketID);
}
