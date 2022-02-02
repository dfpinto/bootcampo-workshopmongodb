package com.devsuperior.workshopmongodb.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.devsuperior.workshopmongodb.modules.dto.PostDTO;
import com.devsuperior.workshopmongodb.modules.dto.UserDTO;
import com.devsuperior.workshopmongodb.services.UserService;

@Controller
@RequestMapping(value = "/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	@GetMapping
	public ResponseEntity<List<UserDTO>> findAll(){
		List<UserDTO> list = userService.findAll();
		return ResponseEntity.ok(list);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<UserDTO> findById(@PathVariable String id){
		return ResponseEntity.ok(userService.findById(id));
	}

	@PostMapping
	public ResponseEntity<UserDTO> insert(@RequestBody UserDTO dto){
		UserDTO obj = userService.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).body(obj);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<UserDTO> update(@PathVariable String id, @RequestBody UserDTO dto){
		UserDTO obj = userService.update(id, dto);
		return ResponseEntity.ok(obj);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable String id){
		userService.delete(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping(value = "/{id}/posts")
	public ResponseEntity<List<PostDTO>> getUserPosts(@PathVariable String id){
		List<PostDTO> dtos = userService.getUserPosts(id);
		return ResponseEntity.ok(dtos);
	}
}
