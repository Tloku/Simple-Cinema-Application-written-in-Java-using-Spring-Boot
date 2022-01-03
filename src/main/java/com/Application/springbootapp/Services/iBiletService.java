package com.Application.springbootapp.Services;

import com.Application.springbootapp.Entities.Bilet;
import java.util.Date;
import java.util.List;

public interface iBiletService {

    List<Bilet> findTicketsByOrderID(int order_ID);
    void deleteTicketByTicketID(int ticketID);
    void changeTicketBeginTimeByTicketID(int ticketID,  Date sdf);
    void addTicket(Date beginTime, int sitNumber,int orderID, int cinemaHallID,  int movieID, int userID);
    String findMovieTitleByTicketID(int ticketID);
}
