package com.example.finalrpo.service.impl;

import com.example.finalrpo.dto.CommentDTO;
import com.example.finalrpo.dto.CommentRequestDTO;
import com.example.finalrpo.mapper.CommentMapper;
import com.example.finalrpo.models.Comment;
import com.example.finalrpo.models.Ticket;
import com.example.finalrpo.service.CommentService;
import com.example.finalrpo.service.UserService;
import com.example.finalrpo.repository.CommentRepository;
import com.example.finalrpo.repository.TicketRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final TicketRepository ticketRepository;
    private final UserService userService;
    private final CommentMapper commentMapper;

    @Override
    public CommentDTO addComment(CommentRequestDTO request) {
        Ticket ticket = ticketRepository.findById(request.getTicketId())
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        Comment comment = new Comment();
        comment.setCommentText(request.getCommentText());
        comment.setPostDate(LocalDateTime.now());
        comment.setTicket(ticket);
        comment.setAuthor(userService.getCurrentUser());

        return commentMapper.toDto(commentRepository.save(comment));
    }

    @Override
    public List<CommentDTO> getCommentsByTicket(Long ticketId) {
        return commentMapper.toDtoList(commentRepository.findAllByTicketId(ticketId));
    }

    @Override
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}
