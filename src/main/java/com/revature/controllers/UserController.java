package com.revature.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.annotations.Secured;
import com.revature.dtos.UserDTO;
import com.revature.models.Role;
import com.revature.models.User;
import com.revature.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	private UserService us;

	@Autowired
	public UserController(UserService us) {
		super();
		this.us = us;
	}
	
	@Secured(allowedRoles= {"ADMIN"})
	@GetMapping
	public ResponseEntity<List<UserDTO>> getAllUsers(@RequestParam(name="role", required=false) Role role){
		List<UserDTO> usersDTO = new ArrayList<>();
		List<User> users;
		
		// if a query param for role is passed, filter by role
		if(role == null) {
			 users = us.getUsers();
		} else {
			users = us.getByRole(role);
		}
		
		for(User u : users) {
			usersDTO.add(new UserDTO(u));
		}
		
		return new ResponseEntity<>(usersDTO, HttpStatus.OK);
	}
	
	@Secured(allowedRoles= {"ADMIN", "BASIC_USER"})
	@GetMapping("/{id}")
	public ResponseEntity<UserDTO> getUserById(@PathVariable("id") int id){
			UserDTO userDTO = new UserDTO(us.getUserById(id)); 
			return new ResponseEntity<>(userDTO, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<UserDTO> createUser(@Valid @RequestBody User user){

		User newUser = us.addUser(user);
		
		UserDTO userDTO = new UserDTO(newUser);
		
		return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
	}
}
