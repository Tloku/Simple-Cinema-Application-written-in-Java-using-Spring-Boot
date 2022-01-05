package com.Application.springbootapp.Services;

import com.Application.springbootapp.Entities.Order;
import com.Application.springbootapp.Repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderService implements iOrderService {
    @Autowired
    private OrderRepository orderRepository;

    public List<Order> findOrderByEmail(String email){
        return orderRepository.findByEmail(email);
    }

    @Override
    public void addOrder(Date data, float orderValue, String paymentMethod, int userID) {
        orderRepository.addOrder(data, orderValue, paymentMethod, userID);
    }

    @Override
    public void updateOrder(float orderValue, int orderID) {
        orderRepository.updateOrderValue(orderValue, orderID);
    }

    @Override
    public int findOrderIDByDateAndUserID(String date, int userID) {
        return orderRepository.findByDateAndUserID(date, userID);
    }
}
