package com.crm.customer_service.repository;

import com.crm.customer_service.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Long> {
}
