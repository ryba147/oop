package com.example.ticketservice.model.entity;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.List;

@Entity(name = "user")
@Component
@Table(name = "user")
@Scope(value = "prototype")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<Ticket> ticketList;

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTicketList(List<Ticket> ticketList) {
        this.ticketList = ticketList;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public List<Ticket> getTicketList() {
        return ticketList;
    }
}
