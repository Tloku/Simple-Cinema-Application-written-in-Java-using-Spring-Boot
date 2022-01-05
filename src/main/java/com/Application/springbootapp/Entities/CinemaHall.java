package com.Application.springbootapp.Entities;

import javax.persistence.*;
import java.util.List;


@Entity(name = "Sala_kinowa")
@Table(
        name = "Sala_kinowa",
        uniqueConstraints = {
                @UniqueConstraint(name = "salaID", columnNames = "Sala_ID"),
                @UniqueConstraint(name = "numerSali", columnNames = "Numer_sali"),
        }
)
public class CinemaHall {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(
            name = "Sala_ID",
            updatable = false,
            unique = true
    )
    private int cinemaHallID;
    @Column(
            name = "Numer_sali",
            unique = true
    )
    private int cinemaHallNumber;

    @Column(
            name = "Ilosc_miejsc"
    )
    private int seatsNumber;
    @Column(
            name = "Pietro"
    )
    private int floor;

    @OneToMany(mappedBy = "cinemaHall")
    private List<Ticket> tickets;

    @OneToMany(mappedBy = "cinemaHall")
    private List<Schedule> schedules;

    public CinemaHall(int cinemaHallNumber, int seatsNumber, int floor, List<Ticket> tickets, List<Schedule> schedules) {
        this.cinemaHallNumber = cinemaHallNumber;
        this.seatsNumber = seatsNumber;
        this.floor = floor;
        this.tickets = tickets;
        this.schedules = schedules;
    }

    public CinemaHall() {}

    public int getCinemaHallID() {
        return cinemaHallID;
    }

    public void setCinemaHallID(int cinemaHallID) {
        this.cinemaHallID = cinemaHallID;
    }

    public int getCinemaHallNumber() {
        return cinemaHallNumber;
    }

    public void setCinemaHallNumber(int cinemaHallNumber) {
        this.cinemaHallNumber = cinemaHallNumber;
    }

    public int getSeatsNumber() {
        return seatsNumber;
    }

    public void setSeatsNumber(int seatsNumber) {
        this.seatsNumber = seatsNumber;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public List<Ticket> getBilets() {
        return tickets;
    }

    public void setBilets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public List<Schedule> getHarmonograms() {
        return schedules;
    }

    public void setHarmonograms(List<Schedule> schedules) {
        this.schedules = schedules;
    }
}
