package com.Application.springbootapp.Services;

import com.Application.springbootapp.Entities.Zamówienie;

import java.util.Date;
import java.util.List;

public interface iOrderService {
    public List<Zamówienie> findOrderByEmail(String email);
    public void addOrder(Date data, float orderValue, String paymentMethod, int userID);
    public void updateOrder(float orderValue, int orderID);
    public int findOrderIDByDateAndUserID(String date, int userID);
}

