package com.Application.springbootapp.Services;

import com.Application.springbootapp.Entities.Zamówienie;
import com.Application.springbootapp.Repositories.ZamówienieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ZamówienieService implements  iZamówienieService {
    @Autowired
    private ZamówienieRepository zamowienieRepository;

    public List<Zamówienie> findOrderByEmail(String email){
        System.out.println("Wejszłem do metody find zamowienieByEmail w zamowienie repository");
        List<Zamówienie> zamowienia = zamowienieRepository.findByEmail(email);
        System.out.println("znalazlem i wyjszłem");
        return zamowienia;
    }

    @Override
    public void addOrder(Date data, float orderValue, String paymentMethod, int userID) {
        System.out.println("Wejszłem do metody find addOrder w zamowienie repository");
        zamowienieRepository.addOrder(data, orderValue, paymentMethod, userID);
        System.out.println("znalazlem i wyjszłem");
    }

    @Override
    public void updateOrder(float orderValue, int orderID) {
        System.out.println("Wejszłem do metody find updateOrder w zamowienie repository");
        zamowienieRepository.updateOrderValue(orderValue, orderID);
        System.out.println("znalazlem i wyjszłem");
    }

    @Override
    public int findOrderIDByDateAndUserID(String date, int userID) {
        return zamowienieRepository.findByDateAndUserID(date, userID);
    }
}
