package com.Application.springbootapp.Entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity(name = "Sala_kinowa")
@Table(
        name = "Sala_kinowa",
        uniqueConstraints = {
                @UniqueConstraint(name = "salaID", columnNames = "Sala_ID"),
                @UniqueConstraint(name = "numerSali", columnNames = "Numer_sali"),
        }
)
public class SalaKinowa {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(
            name = "Sala_ID",
            updatable = false,
            unique = true
    )
    private int salaID;
    @Column(
            name = "numer_sali",
            unique = true
    )
    private int numerSali;

    @Column(
            name = "ilosc_miejsc"
    )
    private int iloscMiejsc;
    @Column(
            name = "Pietro"
    )
    private int pietro;

    @OneToMany(mappedBy = "salaKinowa")
    private List<Bilet> bilets;

    @OneToMany(mappedBy = "salaKinowa")
    private List<Harmonogram> harmonograms;

    public SalaKinowa(int numerSali, int iloscMiejsc, int pietro, List<Bilet> bilets, List<Harmonogram> harmonograms) {
        this.numerSali = numerSali;
        this.iloscMiejsc = iloscMiejsc;
        this.pietro = pietro;
        this.bilets = bilets;
        this.harmonograms = harmonograms;
    }

    public SalaKinowa() {}

    public int getSalaID() {
        return salaID;
    }

    public void setSalaID(int salaID) {
        this.salaID = salaID;
    }

    public int getNumerSali() {
        return numerSali;
    }

    public void setNumerSali(int numerSali) {
        this.numerSali = numerSali;
    }

    public int getIloscMiejsc() {
        return iloscMiejsc;
    }

    public void setIloscMiejsc(int iloscMiejsc) {
        this.iloscMiejsc = iloscMiejsc;
    }

    public int getPietro() {
        return pietro;
    }

    public void setPietro(int pietro) {
        this.pietro = pietro;
    }

    public List<Bilet> getBilets() {
        return bilets;
    }

    public void setBilets(List<Bilet> bilets) {
        this.bilets = bilets;
    }

    public List<Harmonogram> getHarmonograms() {
        return harmonograms;
    }

    public void setHarmonograms(List<Harmonogram> harmonograms) {
        this.harmonograms = harmonograms;
    }
}
