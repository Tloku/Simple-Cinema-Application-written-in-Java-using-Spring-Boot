package com.Application.springbootapp.Entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Entity(name = "Zamówienie")
@Table(
        name = "Zamówienie",
        uniqueConstraints = {
                @UniqueConstraint(name = "zamowienieID", columnNames = "Zamowienie_ID"),
        }
)
public class Zamówienie {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    @Column(
            name = "Zamowienie_ID",
            unique = true,
            updatable = false
    )
    private int zamowienieID;

    @Column(
            name = "Data_zamowienia",
            columnDefinition = "date"
    )
    @Temporal(TemporalType.DATE)
    private Date dataZamowienia;

    @Column(
            name = "Kwota_zamowienia"
    )
    private double kwotaZamowienia;

    @Column(
           name = "Sposob_platnosci"
    )
    private String sposobPlatnosci;

    @OneToOne(mappedBy = "zamowienie", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Bilet bilet;

    @ManyToOne
    @JoinColumn(name = "uzytkownikID")
    private Użytkownik uzytkownik;

    public Zamówienie(Date dataZamowienia, double kwotaZamowienia, String sposobPlatnosci, Użytkownik uzytkownik) {
        this.dataZamowienia = dataZamowienia;
        this.kwotaZamowienia = kwotaZamowienia;
        this.sposobPlatnosci = sposobPlatnosci;
        this.uzytkownik = uzytkownik;
    }

    public Zamówienie() {}

    public int getZamowienieID() {
        return zamowienieID;
    }

    public void setZamowienieID(int zamowienieID) {
        this.zamowienieID = zamowienieID;
    }

    public Date getDataZamowienia() {
        return dataZamowienia;
    }

    public void setDataZamowienia(Date dataZamowienia) {
        this.dataZamowienia = dataZamowienia;
    }

    public double getKwotaZamowienia() {
        return kwotaZamowienia;
    }

    public void setKwotaZamowienia(double kwotaZamowienia) {
        this.kwotaZamowienia = kwotaZamowienia;
    }

    public String getSposobPlatnosci() {
        return sposobPlatnosci;
    }

    public void setSposobPlatnosci(String sposobPlatnosci) {
        this.sposobPlatnosci = sposobPlatnosci;
    }

    public Bilet getBilet() {
        return bilet;
    }

    public void setBilet(Bilet bilet) {
        this.bilet = bilet;
    }

    public Użytkownik getUzytkownik() {
        return uzytkownik;
    }

    public void setUzytkownik(Użytkownik uzytkownik) {
        this.uzytkownik = uzytkownik;
    }

    @Override
    public String toString() {
        return "Zamówienie{" +
                "zamowienieID=" + zamowienieID +
                ", dataZamowienia=" + dataZamowienia +
                ", kwotaZamowienia=" + kwotaZamowienia +
                ", sposobPlatnosci='" + sposobPlatnosci + '\'' +
                ", bilet=" + bilet +
                ", uzytkownik=" + uzytkownik +
                '}';
    }
}
