package com.Application.springbootapp.Entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
public class RepertuarKina {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    @Column(
            name = "Repertuar_kina_ID",
            unique = true,
            updatable = false
    )
    private int repertuarKinaID;

    @Column(
            name = "Data",
            columnDefinition = "Date"
    )
    @Temporal(TemporalType.DATE)
    private Date data;

    @OneToMany(mappedBy = "repertuarKina")
    private List<Film> film;

    @OneToMany(mappedBy = "repertuarKina")
    private List<Harmonogram> Harmonogram;

    public RepertuarKina(Date data, List<Film> film, List<com.Application.springbootapp.Entities.Harmonogram> harmonogram) {
        this.data = data;
        this.film = film;
        Harmonogram = harmonogram;
    }

    public RepertuarKina() {
    }

    public int getRepertuarKinaID() {
        return repertuarKinaID;
    }

    public void setRepertuarKinaID(int repertuarKinaID) {
        this.repertuarKinaID = repertuarKinaID;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public List<Film> getFilm() {
        return film;
    }

    public void setFilm(List<Film> film) {
        this.film = film;
    }

    public List<com.Application.springbootapp.Entities.Harmonogram> getHarmonogram() {
        return Harmonogram;
    }

    public void setHarmonogram(List<com.Application.springbootapp.Entities.Harmonogram> harmonogram) {
        Harmonogram = harmonogram;
    }
}
