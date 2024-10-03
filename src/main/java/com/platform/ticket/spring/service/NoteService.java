package com.platform.ticket.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.platform.ticket.spring.model.Note;
import com.platform.ticket.spring.repository.NoteRepository;

@Service
public class NoteService {

	@Autowired
	private NoteRepository repository;
	
	public Note create(Note note) {
		return repository.save(note);
	}
}
