package com.crm.customer_service.note;

import com.crm.customer_service.dto.NoteDto;
import com.crm.customer_service.model.Customer;
import com.crm.customer_service.model.Note;
import com.crm.customer_service.repository.CustomerRepository;
import com.crm.customer_service.repository.NoteRepository;
import com.crm.customer_service.service.impl.NoteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NoteServiceTest {
    @Mock
    private NoteRepository noteRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private NoteServiceImpl noteService;

    private Customer customer;
    private Note note;
    private NoteDto noteDto;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setId(1L);

        note = new Note();
        note.setId(1L);
        note.setContent("Test note");
        note.setCustomer(customer);

        noteDto = new NoteDto();
        noteDto.setId(1L);
        noteDto.setContent("Test note");
    }

    @Test
    void createNote_Success() {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(modelMapper.map(noteDto, Note.class)).thenReturn(note);
        when(noteRepository.save(any(Note.class))).thenReturn(note);
        when(modelMapper.map(note, NoteDto.class)).thenReturn(noteDto);

        NoteDto result = noteService.createNote(1L, noteDto);

        assertNotNull(result);
        assertEquals(noteDto.getContent(), result.getContent());
        verify(noteRepository).save(any(Note.class));
    }

    @Test
    void createNote_CustomerNotFound() {
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> noteService.createNote(1L, noteDto));
    }

    @Test
    void updateNote_Success() {
        when(noteRepository.findById(1L)).thenReturn(Optional.of(note));
        when(noteRepository.save(any(Note.class))).thenReturn(note);
        when(modelMapper.map(note, NoteDto.class)).thenReturn(noteDto);

        NoteDto result = noteService.updateNote(1L, noteDto);

        assertNotNull(result);
        assertEquals(noteDto.getContent(), result.getContent());
        verify(noteRepository).save(any(Note.class));
    }

    @Test
    void updateNote_NoteNotFound() {
        when(noteRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> noteService.updateNote(1L, noteDto));
    }

    @Test
    void getNoteById_Success() {
        when(noteRepository.findById(1L)).thenReturn(Optional.of(note));
        when(modelMapper.map(note, NoteDto.class)).thenReturn(noteDto);

        NoteDto result = noteService.getNoteById(1L);

        assertNotNull(result);
        assertEquals(noteDto.getContent(), result.getContent());
    }

    @Test
    void getNoteById_NoteNotFound() {
        when(noteRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> noteService.getNoteById(1L));
    }

    @Test
    void getNotesByCustomerId_Success() {
        List<Note> notes = Arrays.asList(note);
        when(noteRepository.findByCustomerId(1L)).thenReturn(notes);
        when(modelMapper.map(note, NoteDto.class)).thenReturn(noteDto);

        List<NoteDto> result = noteService.getNotesByCustomerId(1L);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    void deleteNote_Success() {
        when(noteRepository.findById(1L)).thenReturn(Optional.of(note));
        doNothing().when(noteRepository).delete(note);

        noteService.deleteNote(1L);

        verify(noteRepository).delete(note);
    }

    @Test
    void deleteNote_NoteNotFound() {
        when(noteRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> noteService.deleteNote(1L));
    }
}
