package com.platform.ticket.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.platform.ticket.spring.model.Note;

public interface NoteRepository extends JpaRepository<Note, Integer>{

}
