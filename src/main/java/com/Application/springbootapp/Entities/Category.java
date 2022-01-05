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
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(
            name = "Gatunek_ID",
            unique = true,
            updatable = false
    )
    private int categoryID;

    @Column(
            name = "Nazwa",
            columnDefinition = "varchar(40)"
    )
    private String name;

    @OneToMany(mappedBy = "category")
    private List<Movie> movie;

    public Category(String name, List<Movie> movie) {
        this.name = name;
        this.movie = movie;
    }

    public Category() {
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Movie> getFilm() {
        return movie;
    }

    public void setFilm(List<Movie> movie) {
        this.movie = movie;
    }
}
