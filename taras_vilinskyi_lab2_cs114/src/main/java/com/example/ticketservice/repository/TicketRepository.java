package com.example.ticketservice.repository;

import com.example.ticketservice.model.entity.Ticket;
import com.example.ticketservice.model.helper.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    Iterable<Ticket> findAllByUserId(Long userId);
    Iterable<Ticket> findAllByTypeAndUserId(Type type, Long userId);
}
