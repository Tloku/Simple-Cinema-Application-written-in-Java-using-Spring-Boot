package com.Application.springbootapp.Entities;

import javax.persistence.*;
import java.util.List;

@Entity(name = "Użytkownik")
@Table(
        name = "Użytkownik",
        uniqueConstraints = {
                @UniqueConstraint(name = "uzytkownikID", columnNames = "Użytkownik_ID"),
                @UniqueConstraint(name = "email", columnNames = "Email")
        }
)
public class User {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    @Column(
            name = "Użytkownik_ID",
            unique = true,
            updatable = false
    )
    private int userID;

    @Column(
            name = "Imie",
            columnDefinition = "varchar(20)"
    )
    private String name;

    @Column(
            name = "Nazwisko",
            columnDefinition = "varchar(30)"
    )
    private String surname;

    @Column(
            name = "Email",
            unique = true,
            columnDefinition = "varchar(40)"
    )
    private String email;

    @Column(
            name = "Haslo",
            columnDefinition = "varchar(30)"
    )
    private String password;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RolaRola_ID")
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<Ticket> tickets;

    @OneToMany(mappedBy = "user")
    private List<Order> order;

    public User(String name, String surname, String email,
                String password, Role role) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public User() {
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRola() {
        return role;
    }

    public void setRola(Role role) {
        this.role = role;
    }

    public List<Ticket> getBilets() {
        return tickets;
    }

    public void setBilets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public List<Order> getOrder() {
        return order;
    }

    public void setOrder(List<Order> order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "Użytkownik{" +
                "uzytkownikID=" + userID +
                ", imie='" + name + '\'' +
                ", nazwisko='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", haslo='" + password + '\'' +
                ", rola=" + role +
                '}';
    }
}
