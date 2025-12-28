package com.example.finalrpo.controller;

import com.example.finalrpo.dto.CommentDTO;
import com.example.finalrpo.dto.CommentRequestDTO;
import com.example.finalrpo.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 1. CREATE: Add a comment to a ticket
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CommentDTO> addComment(@RequestBody CommentRequestDTO request) {
        return new ResponseEntity<>(commentService.addComment(request), HttpStatus.CREATED);
    }

    // 2. READ: Get all comments for a specific ticket
    @GetMapping("/ticket/{ticketId}")
    public ResponseEntity<List<CommentDTO>> getCommentsByTicket(@PathVariable Long ticketId) {
        return ResponseEntity.ok(commentService.getCommentsByTicket(ticketId));
    }

    // 3. DELETE: (Optional) Allow Admin or Author to delete a comment
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }
}