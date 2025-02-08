package com.crm.customer_service.controller;

import com.crm.customer_service.dto.NoteDto;
import com.crm.customer_service.service.NoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notes")
@RequiredArgsConstructor
public class NoteController {
    private final NoteService noteService;

    @PostMapping("/customer/{customerId}")
    public ResponseEntity<NoteDto> createNote(
            @PathVariable Long customerId,
            @Valid @RequestBody NoteDto noteDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(noteService.createNote(customerId, noteDto));
    }

    @PutMapping("/{noteId}")
    public ResponseEntity<NoteDto> updateNote(
            @PathVariable Long noteId,
            @Valid @RequestBody NoteDto noteDto) {
        return ResponseEntity.ok(noteService.updateNote(noteId, noteDto));
    }

    @GetMapping("/{noteId}")
    public ResponseEntity<NoteDto> getNoteById(@PathVariable Long noteId) {
        return ResponseEntity.ok(noteService.getNoteById(noteId));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<NoteDto>> getNotesByCustomerId(@PathVariable Long customerId) {
        return ResponseEntity.ok(noteService.getNotesByCustomerId(customerId));
    }

    @DeleteMapping("/{noteId}")
    public ResponseEntity<Void> deleteNote(@PathVariable Long noteId) {
        noteService.deleteNote(noteId);
        return ResponseEntity.noContent().build();
    }
}
