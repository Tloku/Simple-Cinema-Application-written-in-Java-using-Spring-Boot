package com.Application.springbootapp.Entities;

import javax.persistence.*;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@Entity(name = "Harmonogram")
@Table(
        name = "Harmonogram",
        uniqueConstraints = {
                @UniqueConstraint(name = "harmonogramID", columnNames = "Harmonogram_ID"),
        }
)
public class Harmonogram {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    @Column(
            name = "Harmonogram_ID",
            unique = true,
            updatable = false
    )
    private int harmonogramID;

    @Column(
            name = "Godzina_startu",
            columnDefinition = "time"
    )
    @Temporal(TemporalType.TIME)
    private Date godzinaStartu;

    @Column(
            name = "Godzina_zakonczenia",
            columnDefinition = "time"
    )
    @Temporal(TemporalType.TIME)
    private Date godzinaZakonczenia;

    @ManyToOne
    @JoinColumn(name = "filmID")
    private Film film;

    @ManyToOne
    @JoinColumn(name = "repertuarKinaID")
    private RepertuarKina repertuarKina;

    @ManyToOne
    @JoinColumn(name = "SalaKinowaID")
    private SalaKinowa salaKinowa;

    public Harmonogram(Date godzinaStartu, Date godzinaZakonczenia,
                       Film film, RepertuarKina repertuarKina, SalaKinowa salaKinowa) {
        this.godzinaStartu = godzinaStartu;
        this.godzinaZakonczenia = godzinaZakonczenia;
        this.film = film;
        this.repertuarKina = repertuarKina;
        this.salaKinowa = salaKinowa;
    }

    public Harmonogram() {
    }

    public int getHarmonogramID() {
        return harmonogramID;
    }

    public void setHarmonogramID(int harmonogramID) {
        this.harmonogramID = harmonogramID;
    }

    public Date getGodzinaStartu() {
        return godzinaStartu;
    }

    public void setGodzinaStartu(Date godzinaStartu) {
        this.godzinaStartu = godzinaStartu;
    }

    public Date getGodzinaZakonczenia() {
        return godzinaZakonczenia;
    }

    public void setGodzinaZakonczenia(Date godzinaZakonczenia) {
        this.godzinaZakonczenia = godzinaZakonczenia;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public RepertuarKina getRepertuarKina() {
        return repertuarKina;
    }

    public void setRepertuarKina(RepertuarKina repertuarKina) {
        this.repertuarKina = repertuarKina;
    }

    public SalaKinowa getSalaKinowa() {
        return salaKinowa;
    }

    public void setSalaKinowa(SalaKinowa salaKinowa) {
        this.salaKinowa = salaKinowa;
    }
}
