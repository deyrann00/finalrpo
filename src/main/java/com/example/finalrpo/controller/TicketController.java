package com.example.finalrpo.controller;

import com.example.finalrpo.dto.TicketDTO;
import com.example.finalrpo.dto.TicketRequestDTO;
import com.example.finalrpo.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    // 1. READ ALL
    @GetMapping
    public ResponseEntity<List<TicketDTO>> getAllTickets() {
        return ResponseEntity.ok(ticketService.getAllTickets());
    }

    // 2. READ ONE
    @GetMapping("/{id}")
    public ResponseEntity<TicketDTO> getTicket(@PathVariable Long id) {
        return ResponseEntity.ok(ticketService.getTicket(id));
    }

    // 3. CREATE
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<TicketDTO> createTicket(@RequestBody TicketRequestDTO request) {
        return new ResponseEntity<>(ticketService.createTicket(request), HttpStatus.CREATED);
    }

    // 4. UPDATE
    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<TicketDTO> updateTicket(@PathVariable Long id,
                                                  @RequestBody TicketRequestDTO request) {
        return ResponseEntity.ok(ticketService.updateTicket(id, request));
    }

    // 5. DELETE
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPPORT_AGENT')")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long id) {
        ticketService.deleteTicket(id);
        return ResponseEntity.noContent().build();
    }

    // 6. UPDATE STATUS ONLY
    @PutMapping("/{id}/status")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_SUPPORT_AGENT')")
    public ResponseEntity<TicketDTO> updateTicketStatus(@PathVariable Long id,
                                                        @RequestParam String status) {
        return ResponseEntity.ok(ticketService.updateTicketStatus(id, status));
    }
}