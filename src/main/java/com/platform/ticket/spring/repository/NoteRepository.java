package com.platform.ticket.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.platform.ticket.spring.model.Note;

@Repository
public interface NoteRepository extends JpaRepository<Note, Integer>{

}
