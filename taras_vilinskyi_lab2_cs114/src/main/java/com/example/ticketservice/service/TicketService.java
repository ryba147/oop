package com.example.ticketservice.service;

import com.example.ticketservice.model.entity.Ticket;
import com.example.ticketservice.model.helper.Type;
import com.example.ticketservice.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class TicketService {
    @Autowired
    private TicketRepository ticketRepository;

    public Iterable<Ticket> findAllByUserId(Long id){
        return ticketRepository.findAllByUserId(id);
    }

    public Iterable<Ticket> findAllByTypeAndUserId(Type type, Long userId){
        return ticketRepository.findAllByTypeAndUserId(type,userId);
    }

    public Ticket findById(Long id){
        return ticketRepository.findById(id).orElse(null);
    }

    /* генерація квитків */
    public void createRandomTickets(ApplicationContext factory) {
        for(int i = 0; i < 5; ++i) {
            Ticket ticket = (Ticket) factory.getBean("ticket");
            ticket.setType(Type.fromLong((Math.abs(new Random().nextLong())%3)));
            ticketRepository.save(ticket);
        }
    }
}
