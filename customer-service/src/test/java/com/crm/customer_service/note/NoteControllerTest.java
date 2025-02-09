package com.crm.customer_service.note;

import com.crm.customer_service.controller.NoteController;
import com.crm.customer_service.dto.NoteDto;
import com.crm.customer_service.service.NoteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(NoteController.class)
class NoteControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NoteService noteService;

    @Autowired
    private ObjectMapper objectMapper;

    private NoteDto noteDto;

    @BeforeEach
    void setUp() {
        noteDto = new NoteDto();
        noteDto.setId(1L);
        noteDto.setContent("Test note content");
        noteDto.setCustomerId(1L);
    }

    @Test
    void createNote_Success() throws Exception {
        when(noteService.createNote(eq(1L), any(NoteDto.class))).thenReturn(noteDto);

        mockMvc.perform(post("/notes/customer/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(noteDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(noteDto.getId()))
                .andExpect(jsonPath("$.content").value(noteDto.getContent()));

        verify(noteService).createNote(eq(1L), any(NoteDto.class));
    }

    @Test
    void createNote_ValidationFailure() throws Exception {
        NoteDto invalidNote = new NoteDto();
        // Missing required fields

        mockMvc.perform(post("/notes/customer/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidNote)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateNote_Success() throws Exception {
        when(noteService.updateNote(eq(1L), any(NoteDto.class))).thenReturn(noteDto);

        mockMvc.perform(put("/notes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(noteDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(noteDto.getId()))
                .andExpect(jsonPath("$.content").value(noteDto.getContent()));

        verify(noteService).updateNote(eq(1L), any(NoteDto.class));
    }

    @Test
    void getNoteById_Success() throws Exception {
        when(noteService.getNoteById(1L)).thenReturn(noteDto);

        mockMvc.perform(get("/notes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(noteDto.getId()))
                .andExpect(jsonPath("$.content").value(noteDto.getContent()));

        verify(noteService).getNoteById(1L);
    }

    @Test
    void getNotesByCustomerId_Success() throws Exception {
        List<NoteDto> notes = Arrays.asList(noteDto);
        when(noteService.getNotesByCustomerId(1L)).thenReturn(notes);

        mockMvc.perform(get("/notes/customer/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(noteDto.getId()))
                .andExpect(jsonPath("$[0].content").value(noteDto.getContent()));

        verify(noteService).getNotesByCustomerId(1L);
    }

    @Test
    void deleteNote_Success() throws Exception {
        doNothing().when(noteService).deleteNote(1L);

        mockMvc.perform(delete("/notes/1"))
                .andExpect(status().isNoContent());

        verify(noteService).deleteNote(1L);
    }
}
