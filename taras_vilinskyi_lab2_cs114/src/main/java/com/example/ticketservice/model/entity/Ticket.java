package com.example.ticketservice.model.entity;

import com.example.ticketservice.model.helper.Type;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity(name = "ticket")
@Component
@Table(name = "ticket")
@Scope(value = "prototype")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.ORDINAL)
    private Type type;

    @Column(name = "user_id")
    private Long userId;

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    @JsonIgnore
    public Long getUserId() {
        return userId;
    }

    public Type getType() {
        return type;
    }
}
