package com.Application.springbootapp.Entities;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Entity(name = "Repertuar_kina")
@Table(
        name = "Repertuar_kina",
        uniqueConstraints = {
                @UniqueConstraint(name = "repertuarKinaID", columnNames = "Repertuar_Kina_ID"),
                @UniqueConstraint(name = "Data", columnNames = "Data")
        }
)
public class Repertoire {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    @Column(
            name = "Repertuar_kina_ID",
            unique = true,
            updatable = false
    )
    private int repertoireID;

    @Column(
            name = "Data",
            columnDefinition = "Date"
    )
    @Temporal(TemporalType.DATE)
    private Date date;

    @OneToMany(mappedBy = "repertoire")
    private List<Movie> movie;

    @OneToMany(mappedBy = "repertoire")
    private List<Schedule> schedule;

    public Repertoire(Date date, List<Movie> movie, List<Schedule> schedule) {
        this.date = date;
        this.movie = movie;
        this.schedule = schedule;
    }

    public Repertoire() {
    }

    public int getRepertoireID() {
        return repertoireID;
    }

    public void setRepertoireID(int repertoireID) {
        this.repertoireID = repertoireID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Movie> getFilm() {
        return movie;
    }

    public void setFilm(List<Movie> movie) {
        this.movie = movie;
    }

    public List<Schedule> getSchedule() {
        return schedule;
    }

    public void setSchedule(List<Schedule> schedule) {
        this.schedule = schedule;
    }
}
