package com.devsuperior.workshopmongodb.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devsuperior.workshopmongodb.models.entities.User;
import com.devsuperior.workshopmongodb.modules.dto.PostDTO;
import com.devsuperior.workshopmongodb.modules.dto.UserDTO;
import com.devsuperior.workshopmongodb.repositories.UserRepository;
import com.devsuperior.workshopmongodb.services.exceptions.ResourceNotFoundException;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public List<UserDTO> findAll(){
		List<User> list = userRepository.findAll();
		return list.stream().map(x -> new UserDTO(x)).collect(Collectors.toList());
	}
	
	public UserDTO findById(String id) {
		User user = getUserById(id);
		return new UserDTO(user);
	}
	
	private User getUserById(String id) {
		Optional<User> result = userRepository.findById(id);
		return result.orElseThrow(() -> new ResourceNotFoundException("Objeto não enontrado."));
	}
	
	public UserDTO insert(UserDTO dto) {
		User user = new User();
		copyUserDtoToUser(dto, user);
		user = userRepository.insert(user);
		return new UserDTO(user);
	}
	
	public UserDTO update(String id, UserDTO dto) {
		User user = getUserById(id);
		copyUserDtoToUser(dto, user);
		return new UserDTO(userRepository.save(user));
	}

	private void copyUserDtoToUser(UserDTO dto, User user) {
		user.setEmail(dto.getEmail());
		user.setName(dto.getName());
	}
	
	public void delete(String id) {
		getUserById(id);
		userRepository.deleteById(id);
	}
	
	public List<PostDTO> getUserPosts(String id){
		User user = getUserById(id);
		return user.getPosts().stream().map(x -> new PostDTO(x)).collect(Collectors.toList());
	}
}
