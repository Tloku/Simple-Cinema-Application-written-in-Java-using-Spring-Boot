package com.Application.springbootapp.Entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Użytkownik")
@Table(
        name = "Użytkownik",
        uniqueConstraints = {
                @UniqueConstraint(name = "uzytkownikID", columnNames = "Użytkownik_ID"),
                @UniqueConstraint(name = "email", columnNames = "Email")
        }
)
public class Użytkownik {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    @Column(
            name = "Użytkownik_ID",
            unique = true,
            updatable = false
    )
    private int uzytkownikID;

    @Column(
            name = "Imie",
            columnDefinition = "varchar(20)"
    )
    private String imie;

    @Column(
            name = "nazwisko",
            columnDefinition = "varchar(30)"
    )
    private String nazwisko;

    @Column(
            name = "email",
            unique = true,
            columnDefinition = "varchar(40)"
    )
    private String email;

    @Column(
            name = "haslo",
            columnDefinition = "varchar(30)"
    )
    private String haslo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rolaID")
    private Rola rola;

    @OneToMany(mappedBy = "uzytkownik")
    private List<Bilet> bilets;

    @OneToMany(mappedBy = "uzytkownik")
    private List<Zamówienie> zamowienie;

    public Użytkownik(String imie, String nazwisko, String email,
                      String haslo, Rola rola) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.email = email;
        this.haslo = haslo;
        this.rola = rola;
    }

    public Użytkownik() {
    }

    public int getUzytkownikID() {
        return uzytkownikID;
    }

    public void setUzytkownikID(int uzytkownikID) {
        this.uzytkownikID = uzytkownikID;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHaslo() {
        return haslo;
    }

    public void setHaslo(String haslo) {
        this.haslo = haslo;
    }

    public Rola getRola() {
        return rola;
    }

    public void setRola(Rola rola) {
        this.rola = rola;
    }

    public List<Bilet> getBilets() {
        return bilets;
    }

    public void setBilets(List<Bilet> bilets) {
        this.bilets = bilets;
    }

    public List<Zamówienie> getZamowienie() {
        return zamowienie;
    }

    public void setZamowienie(List<Zamówienie> zamowienie) {
        this.zamowienie = zamowienie;
    }

    @Override
    public String toString() {
        return "Użytkownik{" +
                "uzytkownikID=" + uzytkownikID +
                ", imie='" + imie + '\'' +
                ", nazwisko='" + nazwisko + '\'' +
                ", email='" + email + '\'' +
                ", haslo='" + haslo + '\'' +
                ", rola=" + rola +
                '}';
    }
}
