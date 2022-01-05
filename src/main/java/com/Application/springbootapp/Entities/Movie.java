package com.Application.springbootapp.Entities;

import org.springframework.lang.Nullable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity(name = "Film")
@Table(
        name = "Film",
        uniqueConstraints = {
                @UniqueConstraint(name = "FilmID", columnNames = "Film_ID"),
                @UniqueConstraint(name = "Tytul", columnNames = "Tytul"),
        }
)
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(
            name = "Film_ID",
            updatable = false,
            unique = true
    )
    private int movieID;

    @Column(
            name = "Tytul",
            columnDefinition = "varchar(255)"
    )
    private String Title;

    @Column(
            name = "Dlugosc"
    )
    private int movieLength;

    @Column(
            name = "Opis",
            columnDefinition = "varchar(255)"
    )
    @Nullable
    private String description;

    @Column(
            name = "Wytwornia",
            columnDefinition = "varchar(40)"
    )
    private String studio;

    @Column(
            name = "Rok_wydania",
            columnDefinition = "date"
    )
    @Temporal(TemporalType.DATE)
    private Date dateOfProduction;

    @OneToMany(mappedBy = "movie")
    private List<Ticket> tickets;

    @ManyToOne
    @JoinColumn(name = "Repertuar_kinaRepertuar_kina_ID")
    private Repertoire repertoire;

    @ManyToOne
    @JoinColumn(name = "GatunekGatunek_ID")
    private Category category;

    @OneToMany(mappedBy = "movie")
    private List<Schedule> schedule;

    public Movie(String Title, int movieLength, @Nullable String description, String studio,
                 Date dateOfProduction, List<Ticket> tickets, Repertoire repertoire, Category category,
                 List<Schedule> schedule) {
        this.Title = Title;
        this.movieLength = movieLength;
        this.description = description;
        this.studio = studio;
        this.dateOfProduction = dateOfProduction;
        this.tickets = tickets;
        this.repertoire = repertoire;
        this.category = category;
        this.schedule = schedule;
    }

    public Movie() {
    }

    public int getMovieID() {
        return movieID;
    }

    public void setMovieID(int movieID) {
        this.movieID = movieID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        this.Title = title;
    }

    public int getMovieLength() {
        return movieLength;
    }

    public void setMovieLength(int movieLength) {
        this.movieLength = movieLength;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStudio() {
        return studio;
    }

    public void setStudio(String studio) {
        this.studio = studio;
    }

    public Date getDateOfProduction() {
        return dateOfProduction;
    }

    public void setDateOfProduction(Date dateOfProduction) {
        this.dateOfProduction = dateOfProduction;
    }

    public List<Ticket> getBilets() {
        return tickets;
    }

    public void setBilets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public Repertoire getRepertoire() {
        return repertoire;
    }

    public void setRepertoire(Repertoire repertoire) {
        this.repertoire = repertoire;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Schedule> getSchedule() {
        return schedule;
    }

    public void setSchedule(List<Schedule> schedule) {
        this.schedule = schedule;
    }
}
