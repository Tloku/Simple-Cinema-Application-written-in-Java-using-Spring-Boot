package com.Application.springbootapp.Entities;

import javax.persistence.*;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Date;

@Entity(name = "Bilet")
@Table(
        name = "Bilet",
        uniqueConstraints = {
                @UniqueConstraint(name = "BiletID", columnNames = "Bilet_ID"),
        }
)
public class Bilet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(
            name = "Bilet_ID",
            updatable = false,
            unique = true
    )
    private int biletID;

    @Column(
            name = "Godzina_rozpoczęcia",
            columnDefinition = "time"
    )
    @Temporal(TemporalType.TIME)
    private Date godzinaRozpoczecia;


    @Column(
            name = "Numer_miejsca"
    )
    private int numerMiejsca;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zamowienieID")
    private Zamówienie zamowienie;

    @ManyToOne
    @JoinColumn(name = "filmID")
    private Film film;

    @ManyToOne
    @JoinColumn(name = "salaID")
    private SalaKinowa salaKinowa;

    @ManyToOne
    @JoinColumn(name = "uzytkownikID")
    private Użytkownik uzytkownik;

    public Bilet(Time godzinaRozpoczecia, int numerMiejsca, Zamówienie zamowienie, Film film,
                 SalaKinowa salaKinowa, Użytkownik uzytkownik) {
        this.godzinaRozpoczecia = godzinaRozpoczecia;
        this.numerMiejsca = numerMiejsca;
        this.zamowienie = zamowienie;
        this.film = film;
        this.salaKinowa = salaKinowa;
        this.uzytkownik = uzytkownik;
    }

    public Bilet() {
    }

    public int getBiletID() {
        return biletID;
    }

    public void setBiletID(int biletID) {
        this.biletID = biletID;
    }

    public Date getGodzinaRozpoczecia() {
        return godzinaRozpoczecia;
    }

    public void setGodzinaRozpoczecia(Date godzinaRozpoczecia) {
        this.godzinaRozpoczecia = godzinaRozpoczecia;
    }

    public int getNumerMiejsca() {
        return numerMiejsca;
    }

    public void setNumerMiejsca(int numerMiejsca) {
        this.numerMiejsca = numerMiejsca;
    }

    public Zamówienie getZamowienie() {
        return zamowienie;
    }

    public void setZamowienie(Zamówienie zamowienie) {
        this.zamowienie = zamowienie;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public SalaKinowa getSalaKinowa() {
        return salaKinowa;
    }

    public void setSalaKinowa(SalaKinowa salaKinowa) {
        this.salaKinowa = salaKinowa;
    }

    public Użytkownik getUzytkownik() {
        return uzytkownik;
    }

    public void setUzytkownik(Użytkownik uzytkownik) {
        this.uzytkownik = uzytkownik;
    }

    @Override
    public String toString() {
        return "Bilet{" +
                "bilet_ID=" + biletID +
                ", godzinaRozpoczecia=" + godzinaRozpoczecia +
                ", numerMiejsca=" + numerMiejsca +
                ", zamowienie=" + zamowienie +
                ", film=" + film +
                ", salaKinowa=" + salaKinowa +
                ", uzytkownik=" + uzytkownik +
                '}';
    }
}
