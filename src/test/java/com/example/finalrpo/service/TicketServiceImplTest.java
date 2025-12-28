package com.example.finalrpo.service;

import com.example.finalrpo.dto.TicketDTO;
import com.example.finalrpo.dto.TicketRequestDTO;
import com.example.finalrpo.mapper.TicketMapper;
import com.example.finalrpo.models.*;
import com.example.finalrpo.repository.CategoryRepository;
import com.example.finalrpo.repository.TagRepository;
import com.example.finalrpo.repository.TicketRepository;
import com.example.finalrpo.service.impl.TicketServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TicketServiceImplTest {

    @Mock
    private TicketRepository ticketRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private TagRepository tagRepository;
    @Mock
    private UserService userService;
    @Mock
    private TicketMapper ticketMapper;

    @InjectMocks
    private TicketServiceImpl ticketService;

    @Test
    void testCreateTicket_BusinessLogic() {
        // 1. SETUP
        TicketRequestDTO request = new TicketRequestDTO();
        request.setTitle("Printer Error");
        request.setCategoryId(1L);
        request.setTagIds(List.of(10L));

        User mockUser = new User();
        mockUser.setId(99L);
        mockUser.setFullName("John Doe");

        Category mockCategory = new Category();
        mockCategory.setId(1L);

        Ticket mockTicketEntity = new Ticket();
        mockTicketEntity.setId(500L);
        mockTicketEntity.setTitle("Printer Error");
        mockTicketEntity.setAuthor(mockUser);

        TicketDTO expectedResponse = new TicketDTO();
        expectedResponse.setId(500L);
        expectedResponse.setTitle("Printer Error");

        // 2. MOCK
        when(userService.getCurrentUser()).thenReturn(mockUser);
        when(ticketMapper.toEntity(any())).thenReturn(new Ticket());
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(mockCategory));
        when(tagRepository.findAllById(any())).thenReturn(List.of(new Tag()));
        when(ticketRepository.save(any(Ticket.class))).thenReturn(mockTicketEntity);
        when(ticketMapper.toDto(any(Ticket.class))).thenReturn(expectedResponse);

        // 3. EXECUTION
        TicketDTO result = ticketService.createTicket(request);

        // 4. VERIFICATION (Asserts)
        Assertions.assertNotNull(result);
        Assertions.assertEquals("Printer Error", result.getTitle());

        verify(ticketRepository, times(1)).save(any(Ticket.class));
    }

    @Test
    void testCloseTicket_BusinessLogic() {
        // 1. PREP
        Long ticketId = 100L;
        Ticket existingTicket = new Ticket();
        existingTicket.setId(ticketId);
        existingTicket.setStatus("OPEN");

        when(ticketRepository.findById(ticketId)).thenReturn(Optional.of(existingTicket));

        // 2. EXECUTE
        ticketService.closeTicket(ticketId);

        // 3. VERIFY
        Assertions.assertEquals("CLOSED", existingTicket.getStatus());
        verify(ticketRepository, times(1)).save(existingTicket);
    }
}
