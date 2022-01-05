package com.Application.springbootapp.Entities;

import javax.persistence.*;
import java.util.*;

@Entity(name = "Zamówienie")
@Table(
        name = "Zamówienie",
        uniqueConstraints = {
                @UniqueConstraint(name = "zamowienieID", columnNames = "Zamowienie_ID"),
        }
)
public class Order {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    @Column(
            name = "Zamowienie_ID",
            unique = true,
            updatable = false
    )
    private int orderID;

    @Column(
            name = "Data_zamowienia",
            columnDefinition = "date"
    )
    @Temporal(TemporalType.DATE)
    private Date orderDate;

    @Column(
            name = "Kwota_zamowienia"
    )
    private double orderValue;

    @Column(
           name = "Sposob_platnosci"
    )
    private String paymentMethod;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Ticket ticket;

    @ManyToOne
    @JoinColumn(name = "UżytkownikUżytkownik_ID")
    private User user;

    public Order(Date orderDate, double orderValue, String paymentMethod, User user) {
        this.orderDate = orderDate;
        this.orderValue = orderValue;
        this.paymentMethod = paymentMethod;
        this.user = user;
    }

    public Order() {}

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public double getOrderValue() {
        return orderValue;
    }

    public void setOrderValue(double orderValue) {
        this.orderValue = orderValue;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Ticket getBilet() {
        return ticket;
    }

    public void setBilet(Ticket ticket) {
        this.ticket = ticket;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Zamówienie{" +
                "zamowienieID=" + orderID +
                ", dataZamowienia=" + orderDate +
                ", kwotaZamowienia=" + orderValue +
                ", sposobPlatnosci='" + paymentMethod + '\'' +
                ", bilet=" + ticket +
                ", uzytkownik=" + user +
                '}';
    }
}
