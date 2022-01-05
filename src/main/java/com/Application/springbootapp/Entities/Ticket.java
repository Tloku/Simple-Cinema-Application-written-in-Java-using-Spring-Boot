package com.Application.springbootapp.Entities;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;

@Entity(name = "Bilet")
@Table(
        name = "Bilet",
        uniqueConstraints = {
                @UniqueConstraint(name = "BiletID", columnNames = "Bilet_ID"),
        }
)
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(
            name = "Bilet_ID",
            updatable = false,
            unique = true
    )
    private int ticketID;

    @Column(
            name = "Godzina_rozpoczęcia",
            columnDefinition = "time"
    )
    @Temporal(TemporalType.TIME)
    private Date beginTime;


    @Column(
            name = "Numer_miejsca"
    )
    private int seatNumber;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ZamówienieZamowienie_ID")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "FilmFilm_ID")
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "Sala_KinowaSala_ID")
    private CinemaHall cinemaHall;

    @ManyToOne
    @JoinColumn(name = "UżytkownikUżytkownik_ID")
    private User user;

    public Ticket(Time beginTime, int seatNumber, Order order, Movie movie,
                  CinemaHall cinemaHall, User user) {
        this.beginTime = beginTime;
        this.seatNumber = seatNumber;
        this.order = order;
        this.movie = movie;
        this.cinemaHall = cinemaHall;
        this.user = user;
    }

    public Ticket() {
    }

    public int getTicketID() {
        return ticketID;
    }

    public void setTicketID(int ticketID) {
        this.ticketID = ticketID;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public CinemaHall getCinemaHall() {
        return cinemaHall;
    }

    public void setCinemaHall(CinemaHall cinemaHall) {
        this.cinemaHall = cinemaHall;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Bilet{" +
                "bilet_ID=" + ticketID +
                ", godzinaRozpoczecia=" + beginTime +
                ", numerMiejsca=" + seatNumber +
                ", zamowienie=" + order +
                ", film=" + movie +
                ", salaKinowa=" + cinemaHall +
                ", uzytkownik=" + user +
                '}';
    }
}
