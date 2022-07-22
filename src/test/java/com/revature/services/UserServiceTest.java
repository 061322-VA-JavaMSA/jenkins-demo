package com.revature.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.revature.TaskManagerBootApplication;
import com.revature.exceptions.UserNotFoundException;
import com.revature.models.Role;
import com.revature.models.User;
import com.revature.repositories.UserRepository;

@SpringBootTest(classes=TaskManagerBootApplication.class)
public class UserServiceTest {

	@MockBean
	private UserRepository ur;
	
	// Spring Container injects our Mock UserRepository bean instead of an actual one.
	@Autowired
	private UserService sut;
	
	@Test
	public void getUsers(){
		User u1 = new User();
		u1.setId(1);
		u1.setUsername("kev");
		u1.setPassword("pass");
		u1.setRole(Role.ADMIN);
		
		List<User> users = new ArrayList<>();
		users.add(u1);
		
		Mockito.when(ur.findAll()).thenReturn(users);
		
		
		List<User> usersActual = sut.getUsers();
		
		assertEquals(users, usersActual);
	}
	
	@Test
	public void getUsersByRole() {
		User u1 = new User();
		u1.setId(1);
		u1.setUsername("kev");
		u1.setPassword("pass");
		u1.setRole(Role.ADMIN);
		
		List<User> users = new ArrayList<>();
		users.add(u1);
		
		Mockito.when(ur.findUserByRole(Role.ADMIN)).thenReturn(users);
		
		
		List<User> usersActual = sut.getByRole(Role.ADMIN);
		
		assertEquals(users, usersActual);
	}
	
	@Test
	public void getUserByIdExists() throws UserNotFoundException {
		User udaoExpected = new User();
		udaoExpected.setId(1);
		udaoExpected.setUsername("kev");
		udaoExpected.setPassword("pass");
		udaoExpected.setRole(Role.ADMIN);
		
		User uservExpected = new User();
		uservExpected.setId(1);
		uservExpected.setUsername("kev");
		uservExpected.setPassword("pass");
		uservExpected.setRole(Role.ADMIN);
		
		
		Mockito.when(ur.findById(1)).thenReturn(Optional.of(udaoExpected));
		
		User uservActual = sut.getUserById(1);
		
		assertEquals(uservExpected, uservActual);
	}
	
	@Test
	public void getUserByIdDoesNotExist() throws UserNotFoundException {
		/*-
		 * Mocking allows us to "mock" dependencies:
		 * 		- in this case sut will call .getUserById() from mockUserDao instead of UserHibernate
		 * 		- We can control what mockUserDao will return, in this case it will return null for id = 3
		 */
		Mockito.when(ur.findById(3)).thenReturn(Optional.empty());
		
		// sut calls mockUserDao.getUserById(1); instead of calling UserHibernate's implementation - ud.getUserById(1)
		assertThrows(UserNotFoundException.class, () -> sut.getUserById(3));
	}
}
