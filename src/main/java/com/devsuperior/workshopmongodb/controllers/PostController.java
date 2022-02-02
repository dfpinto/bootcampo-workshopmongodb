package com.devsuperior.workshopmongodb.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.devsuperior.workshopmongodb.modules.dto.PostDTO;
import com.devsuperior.workshopmongodb.services.PostService;

@Controller
@RequestMapping(value = "/posts")
public class PostController {

	@Autowired
	private PostService postService;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<PostDTO> findById(@PathVariable String id){
		return ResponseEntity.ok(postService.findById(id));
	}
	
	@GetMapping(value = "/titlesearch")
	public ResponseEntity<List<PostDTO>> findByTitle(@RequestParam(value="text", defaultValue = "") String text){
		List<PostDTO> list = postService.findByTitle(text);
		return ResponseEntity.ok(list);
	}

	@GetMapping(value = "/fullsearch")
	public ResponseEntity<List<PostDTO>> fullSearch(
			@RequestParam(value="text", defaultValue = "") String text,
			@RequestParam(value="start", defaultValue = "") String start,
			@RequestParam(value="end", defaultValue = "") String end
			){
		List<PostDTO> list = postService.fullSearch(text, start, end);
		return ResponseEntity.ok(list);
	}
}
