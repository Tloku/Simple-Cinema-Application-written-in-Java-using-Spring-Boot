package com.Application.springbootapp.Entities;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "Harmonogram")
@Table(
        name = "Harmonogram",
        uniqueConstraints = {
                @UniqueConstraint(name = "harmonogramID", columnNames = "Harmonogram_ID"),
        }
)
public class Schedule {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    @Column(
            name = "Harmonogram_ID",
            unique = true,
            updatable = false
    )
    private int scheduleID;

    @Column(
            name = "Godzina_startu",
            columnDefinition = "time"
    )
    @Temporal(TemporalType.TIME)
    private Date beginTime;

    @Column(
            name = "Godzina_zakonczenia",
            columnDefinition = "time"
    )
    @Temporal(TemporalType.TIME)
    private Date endTime;

    @ManyToOne
    @JoinColumn(name = "FilmFilm_ID")
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "Repertuar_kinaRepertuar_kina_ID")
    private Repertoire repertoire;

    @ManyToOne
    @JoinColumn(name = "Sala_KinowaSala_ID")
    private CinemaHall cinemaHall;

    public Schedule(Date beginTime, Date endTime,
                    Movie movie, Repertoire repertoire, CinemaHall cinemaHall) {
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.movie = movie;
        this.repertoire = repertoire;
        this.cinemaHall = cinemaHall;
    }

    public Schedule() {
    }

    public int getScheduleID() {
        return scheduleID;
    }

    public void setScheduleID(int scheduleID) {
        this.scheduleID = scheduleID;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Movie getFilm() {
        return movie;
    }

    public void setFilm(Movie movie) {
        this.movie = movie;
    }

    public Repertoire getRepertuarKina() {
        return repertoire;
    }

    public void setRepertuarKina(Repertoire repertoire) {
        this.repertoire = repertoire;
    }

    public CinemaHall getCinemaHall() {
        return cinemaHall;
    }

    public void setCinemaHall(CinemaHall cinemaHall) {
        this.cinemaHall = cinemaHall;
    }
}
