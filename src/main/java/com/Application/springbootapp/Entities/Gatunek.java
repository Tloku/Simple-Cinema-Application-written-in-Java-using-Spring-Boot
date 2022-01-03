package com.Application.springbootapp.Entities;

import javax.persistence.*;
import java.util.List;

@Entity(name = "Gatunek")
@Table(
        name = "Gatunek",
        uniqueConstraints = {
                @UniqueConstraint(name = "Gatunek_ID", columnNames = "Gatunek_ID"),
                @UniqueConstraint(name = "nazwa", columnNames = "Nazwa")
        }
)
public class Gatunek {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(
            name = "Gatunek_ID",
            unique = true,
            updatable = false
    )
    private int gatunekID;

    @Column(
            name = "nazwa",
            columnDefinition = "varchar(40)"
    )
    private String nazwa;

    @OneToMany(mappedBy = "gatunek")
    private List<Film> film;

    public Gatunek(String nazwa, List<Film> film) {
        this.nazwa = nazwa;
        this.film = film;
    }

    public Gatunek() {
    }

    public int getGatunekID() {
        return gatunekID;
    }

    public void setGatunekID(int gatunekID) {
        this.gatunekID = gatunekID;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public List<Film> getFilm() {
        return film;
    }

    public void setFilm(List<Film> film) {
        this.film = film;
    }
}
