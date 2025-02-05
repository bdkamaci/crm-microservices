package com.crm.customer_service.repository;

import com.crm.customer_service.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findByCustomerId(Long customerId);
}
