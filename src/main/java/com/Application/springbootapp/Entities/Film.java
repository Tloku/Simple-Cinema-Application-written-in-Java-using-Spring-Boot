package com.Application.springbootapp.Entities;

import org.springframework.lang.Nullable;
import javax.persistence.*;
import java.time.LocalDate;
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
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(
            name = "Film_ID",
            updatable = false,
            unique = true
    )
    private int filmID;

    @Column(
            name = "Tytul",
            columnDefinition = "varchar(255)"
    )
    private String tytul;

    @Column(
            name = "Dlugosc",
            unique = false
    )
    private int dlugosc;

    @Column(
            name = "Opis",
            columnDefinition = "varchar(255)"
    )
    @Nullable
    private String opis;

    @Column(
            name = "Wytwornia",
            columnDefinition = "varchar(40)"
    )
    private String wytwornia;

    @Column(
            name = "Data_wydania",
            columnDefinition = "date"
    )
    @Temporal(TemporalType.DATE)
    private Date dataWydania;

    @OneToMany(mappedBy = "film")
    private List<Bilet> bilets;

    @ManyToOne
    @JoinColumn(name = "repertuarKinaID")
    private RepertuarKina repertuarKina;

    @ManyToOne
    @JoinColumn(name = "gatunekID")
    private Gatunek gatunek;

    @OneToMany(mappedBy = "film")
    private List<Harmonogram> harmonogram;

    public Film(String tytul, int dlugosc, @Nullable String opis, String wytwornia,
                Date dataWydania, List<Bilet> bilety, RepertuarKina repertuarKina, Gatunek gatunek,
                List<Harmonogram> harmonogram) {
        this.tytul = tytul;
        this.dlugosc = dlugosc;
        this.opis = opis;
        this.wytwornia = wytwornia;
        this.dataWydania = dataWydania;
        this.bilets = bilety;
        this.repertuarKina = repertuarKina;
        this.gatunek = gatunek;
        this.harmonogram = harmonogram;
    }

    public Film() {
    }

    public int getFilmID() {
        return filmID;
    }

    public void setFilmID(int filmID) {
        this.filmID = filmID;
    }

    public String getTytul() {
        return tytul;
    }

    public void setTytul(String tytul) {
        this.tytul = tytul;
    }

    public int getDlugosc() {
        return dlugosc;
    }

    public void setDlugosc(int dlugosc) {
        this.dlugosc = dlugosc;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getWytwornia() {
        return wytwornia;
    }

    public void setWytwornia(String wytwornia) {
        this.wytwornia = wytwornia;
    }

    public Date getDataWydania() {
        return dataWydania;
    }

    public void setDataWydania(Date dataWydania) {
        this.dataWydania = dataWydania;
    }

    public List<Bilet> getBilets() {
        return bilets;
    }

    public void setBilets(List<Bilet> bilets) {
        this.bilets = bilets;
    }

    public RepertuarKina getRepertuarKina() {
        return repertuarKina;
    }

    public void setRepertuarKina(RepertuarKina repertuarKina) {
        this.repertuarKina = repertuarKina;
    }

    public Gatunek getGatunek() {
        return gatunek;
    }

    public void setGatunek(Gatunek gatunek) {
        this.gatunek = gatunek;
    }

    public List<Harmonogram> getHarmonogram() {
        return harmonogram;
    }

    public void setHarmonogram(List<Harmonogram> harmonogram) {
        this.harmonogram = harmonogram;
    }
}
