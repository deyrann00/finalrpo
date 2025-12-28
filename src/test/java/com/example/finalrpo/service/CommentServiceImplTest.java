package com.example.finalrpo.service;

import com.example.finalrpo.dto.CommentDTO;
import com.example.finalrpo.dto.CommentRequestDTO;
import com.example.finalrpo.mapper.CommentMapper;
import com.example.finalrpo.models.Comment;
import com.example.finalrpo.models.Ticket;
import com.example.finalrpo.models.User;
import com.example.finalrpo.repository.CommentRepository;
import com.example.finalrpo.repository.TicketRepository;
import com.example.finalrpo.service.impl.CommentServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommentServiceImplTest {

    @Mock private CommentRepository commentRepository;
    @Mock private TicketRepository ticketRepository;
    @Mock private UserService userService;
    @Mock private CommentMapper commentMapper;

    @InjectMocks
    private CommentServiceImpl commentService;

    @Test
    void testAddComment_Logic() {
        // 1. SETUP
        CommentRequestDTO request = new CommentRequestDTO();
        request.setCommentText("Fixed it!");
        request.setTicketId(100L);

        Ticket mockTicket = new Ticket();
        mockTicket.setId(100L);

        User mockUser = new User();
        mockUser.setEmail("agent@tech.com");

        Comment savedComment = new Comment();
        savedComment.setCommentText("Fixed it!");
        // Simulate DB saving time
        savedComment.setPostDate(LocalDateTime.now());

        CommentDTO expectedDto = new CommentDTO();
        expectedDto.setCommentText("Fixed it!");

        // 2. MOCK
        when(ticketRepository.findById(100L)).thenReturn(Optional.of(mockTicket));
        when(userService.getCurrentUser()).thenReturn(mockUser); // Logic: Auto-detect user
        when(commentRepository.save(any(Comment.class))).thenReturn(savedComment);
        when(commentMapper.toDto(savedComment)).thenReturn(expectedDto);

        // 3. EXECUTE
        CommentDTO result = commentService.addComment(request);

        // 4. VERIFY
        Assertions.assertNotNull(result);
        Assertions.assertEquals("Fixed it!", result.getCommentText());

        verify(commentRepository).save(argThat(comment ->
                comment.getAuthor().equals(mockUser) &&
                        comment.getTicket().equals(mockTicket) &&
                        comment.getPostDate() != null // Logic: Date was set
        ));
    }
}