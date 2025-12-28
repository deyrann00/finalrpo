package com.example.finalrpo.repository;

import org.springframework.stereotype.Repository;

import com.example.finalrpo.models.Ticket;
import com.example.finalrpo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findAllByAuthor(User author);
}
