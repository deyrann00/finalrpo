package com.example.finalrpo.service.impl;

import com.example.finalrpo.dto.TicketDTO;
import com.example.finalrpo.dto.TicketRequestDTO;
import com.example.finalrpo.mapper.TicketMapper;
import com.example.finalrpo.models.Ticket;
import com.example.finalrpo.models.User;
import com.example.finalrpo.repository.CategoryRepository;
import com.example.finalrpo.repository.TagRepository;
import com.example.finalrpo.repository.TicketRepository;
import com.example.finalrpo.service.TicketService;
import com.example.finalrpo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired private TicketRepository ticketRepository;
    @Autowired private CategoryRepository categoryRepository;
    @Autowired private TagRepository tagRepository;
    @Autowired private UserService userService;
    @Autowired private TicketMapper ticketMapper;

    @Override
    public TicketDTO createTicket(TicketRequestDTO dto) {
        User currentUser = userService.getCurrentUser();

        Ticket ticket = ticketMapper.toEntity(dto);
        ticket.setAuthor(currentUser);
        ticket.setCategory(categoryRepository.findById(dto.getCategoryId()).orElse(null));
        ticket.setTags(tagRepository.findAllById(dto.getTagIds()));

        return ticketMapper.toDto(ticketRepository.save(ticket));
    }

    @Override
    public TicketDTO getTicket(Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));
        return ticketMapper.toDto(ticket);
    }

    @Override
    public List<TicketDTO> getAllTickets() {
        return ticketMapper.toDtoList(ticketRepository.findAll());
    }

    @Override
    public TicketDTO updateTicket(Long id, TicketRequestDTO dto) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        ticket.setTitle(dto.getTitle());
        ticket.setDescription(dto.getDescription());

        if (dto.getCategoryId() != null) {
            ticket.setCategory(categoryRepository.findById(dto.getCategoryId()).orElse(null));
        }
        if (dto.getTagIds() != null) {
            ticket.setTags(tagRepository.findAllById(dto.getTagIds()));
        }

        return ticketMapper.toDto(ticketRepository.save(ticket));
    }

    @Override
    public void deleteTicket(Long id) {
        ticketRepository.deleteById(id);
    }

    @Override
    public void closeTicket(Long ticketId) {
        Ticket t = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));
        t.setStatus("CLOSED");
        ticketRepository.save(t);
    }

    @Override
    public TicketDTO updateTicketStatus(Long id, String status) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found with id: " + id));

        ticket.setStatus(status.toUpperCase());

        Ticket updatedTicket = ticketRepository.save(ticket);
        return ticketMapper.toDto(updatedTicket);
    }
}
