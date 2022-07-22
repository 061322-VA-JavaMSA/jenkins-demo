package com.revature.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.TaskManagerBootApplication;
import com.revature.dtos.UserDTO;
import com.revature.exceptions.UserNotFoundException;
import com.revature.models.Role;
import com.revature.models.User;
import com.revature.services.UserService;

@SpringBootTest(classes=TaskManagerBootApplication.class)
public class UserControllerTest {
	@MockBean
	private UserService us;
	@Autowired
	private WebApplicationContext context;
	
	private ObjectMapper om = new ObjectMapper();
	private MockMvc mockMvc;
	
	@BeforeEach
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	public void getUserById() throws JsonProcessingException, Exception {
		User uservExpected = new User();
		uservExpected.setId(1);
		uservExpected.setUsername("kev");
		uservExpected.setPassword("pass");
		uservExpected.setRole(Role.ADMIN);
		

		UserDTO expected = new UserDTO(uservExpected);
		
		when(us.getUserById(1)).thenReturn(uservExpected);
		
		mockMvc.perform(
				get("/users/1"))
			.andExpect(status().isOk())
			.andExpect(content().json(om.writeValueAsString(expected)));
	}
	
	@Test
	public void getUserByIdNotExist() throws JsonProcessingException, Exception {

		when(us.getUserById(3)).thenThrow(UserNotFoundException.class);
		
		mockMvc.perform(
				get("/users/3"))
			.andExpect(status().isNotFound());
	}
	
	@Test
	public void getUsers() throws JsonProcessingException, Exception {
		User u1 = new User();
		u1.setId(1);
		u1.setUsername("kev");
		u1.setPassword("pass");
		u1.setRole(Role.ADMIN);
		
		List<User> users = new ArrayList<>();
		users.add(u1);

		
		List<UserDTO> usersDTO = new ArrayList<>();
		usersDTO.add(new UserDTO(u1));
		
		when(us.getUsers()).thenReturn(users);
		
		mockMvc.perform(
				get("/users"))
			.andExpect(status().isOk())
			.andExpect(content().json(om.writeValueAsString(usersDTO)));
	}

}
