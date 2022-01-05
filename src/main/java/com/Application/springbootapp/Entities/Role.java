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
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(
            name = "Rola_ID",
            updatable = false,
            unique = true
    )
    private int roleID;
    @Column(
            name = "Nazwa_roli",
            updatable = false,
            columnDefinition = "varchar(15)",
            unique = true
    )
    private String roleName;

    @OneToOne(mappedBy = "role", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private User user;

    public Role() {}

    public Role(String roleName) {
        this.roleName = roleName;
    }

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Rola{" +
                "Rola_ID=" + roleID +
                ", Nazwa_roli='" + roleName + '\'' +
                '}';
    }
}
