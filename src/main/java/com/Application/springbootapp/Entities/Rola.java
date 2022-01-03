package com.Application.springbootapp.Entities;

import javax.persistence.*;

@Entity (name = "Rola")
@Table(
        name = "Rola",
        uniqueConstraints = {
                @UniqueConstraint(name = "rolaID", columnNames = "Rola_ID"),
                @UniqueConstraint(name = "nazwaRoli", columnNames = "Nazwa_roli")
        }
)
public class Rola {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(
            name = "Rola_ID",
            updatable = false,
            unique = true
    )
    private int rolaID;
    @Column(
            name = "Nazwa_roli",
            updatable = false,
            columnDefinition = "varchar(15)",
            unique = true
    )
    private String nazwaRoli;

    @OneToOne(mappedBy = "rola", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Użytkownik uzytkownik;

    public Rola() {}

    public Rola(String nazwaRoli) {
        this.nazwaRoli = nazwaRoli;
    }

    public int getRolaID() {
        return rolaID;
    }

    public void setRolaID(int rolaID) {
        this.rolaID = rolaID;
    }

    public String getNazwaRoli() {
        return nazwaRoli;
    }

    public void setNazwaRoli(String nazwaRoli) {
        this.nazwaRoli = nazwaRoli;
    }

    public Użytkownik getUzytkownik() {
        return uzytkownik;
    }

    public void setUzytkownik(Użytkownik uzytkownik) {
        this.uzytkownik = uzytkownik;
    }

    @Override
    public String toString() {
        return "Rola{" +
                "Rola_ID=" + rolaID +
                ", Nazwa_roli='" + nazwaRoli + '\'' +
                '}';
    }
}
