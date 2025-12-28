package com.example.finalrpo.service;

import com.example.finalrpo.dto.TicketDTO;
import com.example.finalrpo.dto.TicketRequestDTO;
import java.util.List;

public interface TicketService {
    TicketDTO createTicket(TicketRequestDTO ticketRequest);
    TicketDTO getTicket(Long id);              // Read One
    List<TicketDTO> getAllTickets();           // Read All
    TicketDTO updateTicket(Long id, TicketRequestDTO ticketRequest); // Update
    void deleteTicket(Long id);                // Delete
    void closeTicket(Long ticketId);
    TicketDTO updateTicketStatus(Long id, String status);
}
