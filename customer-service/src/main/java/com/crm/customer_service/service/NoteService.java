package com.crm.customer_service.service;

import com.crm.customer_service.dto.NoteDto;

import java.util.List;

public interface NoteService {
    NoteDto createNote(Long customerId, NoteDto noteDto);
    NoteDto updateNote(Long noteId, NoteDto noteDto);
    NoteDto getNoteById(Long noteId);
    List<NoteDto> getNotesByCustomerId(Long customerId);
    void deleteNote(Long noteId);
}
