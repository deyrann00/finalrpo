package com.example.finalrpo.service;

import com.example.finalrpo.dto.CommentDTO;
import com.example.finalrpo.dto.CommentRequestDTO;

import java.util.List;

public interface CommentService {
    CommentDTO addComment(CommentRequestDTO request);
    List<CommentDTO> getCommentsByTicket(Long ticketId);
    void deleteComment(Long id);
}
