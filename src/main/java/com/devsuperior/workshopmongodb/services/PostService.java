package com.devsuperior.workshopmongodb.services;

import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devsuperior.workshopmongodb.models.entities.Post;
import com.devsuperior.workshopmongodb.modules.dto.PostDTO;
import com.devsuperior.workshopmongodb.repositories.PostRepository;
import com.devsuperior.workshopmongodb.services.exceptions.ResourceNotFoundException;

@Service
public class PostService {

	@Autowired
	private PostRepository postRepository;
	
	public PostDTO findById(String id) {
		Post post = getPostById(id);
		return new PostDTO(post);
	}
	
	private Post getPostById(String id) {
		Optional<Post> result = postRepository.findById(id);
		return result.orElseThrow(() -> new ResourceNotFoundException("Objeto não enontrado."));
	}
	
	public List<PostDTO> findByTitle(String text){
		//List<Post> list = postRepository.findByTitleContainingIgnoreCase(text);
		List<Post> list = postRepository.searchByTitle(text);
		return list.stream().map(x -> new PostDTO(x)).collect(Collectors.toList());
	}
	
	public List<PostDTO> fullSearch(String text, String start, String end ){
		Instant startMoment = convertMoment(start, Instant.ofEpochMilli(0L));
		Instant endMoment = convertMoment(end, Instant.now());
		
		List<Post> list = postRepository.fullSearch(text, startMoment, endMoment);
		return list.stream().map(x -> new PostDTO(x)).collect(Collectors.toList());
	}

	private Instant convertMoment(String moment, Instant momentDefault) {
		try {
			return Instant.parse(moment);
		}
		catch(DateTimeParseException e) {
			return momentDefault;
		}
	}
}