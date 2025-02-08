package com.crm.customer_service.service.impl;

import com.crm.customer_service.dto.NoteDto;
import com.crm.customer_service.model.Customer;
import com.crm.customer_service.model.Note;
import com.crm.customer_service.repository.CustomerRepository;
import com.crm.customer_service.repository.NoteRepository;
import com.crm.customer_service.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {
    private final NoteRepository noteRepository;
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public NoteDto createNote(Long customerId, NoteDto noteDto) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Note note = modelMapper.map(noteDto, Note.class);
        note.setCustomer(customer);
        Note savedNote = noteRepository.save(note);

        return modelMapper.map(savedNote, NoteDto.class);
    }

    @Override
    @Transactional
    public NoteDto updateNote(Long noteId, NoteDto noteDto) {
        Note existingNote = noteRepository.findById(noteId)
                .orElseThrow(() -> new RuntimeException("Note not found"));

        existingNote.setContent(noteDto.getContent());
        Note updatedNote = noteRepository.save(existingNote);

        return modelMapper.map(updatedNote, NoteDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public NoteDto getNoteById(Long noteId) {
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new RuntimeException("Note not found"));
        return modelMapper.map(note, NoteDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NoteDto> getNotesByCustomerId(Long customerId) {
        return noteRepository.findByCustomerId(customerId).stream()
                .map(note -> modelMapper.map(note, NoteDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteNote(Long noteId) {
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new RuntimeException("Note not found"));
        noteRepository.delete(note);
    }
}
