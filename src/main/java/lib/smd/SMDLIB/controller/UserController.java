package lib.smd.SMDLIB.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lib.smd.SMDLIB.Dto.UserD.UserDelD;
import lib.smd.SMDLIB.Dto.UserD.UserDto;
import lib.smd.SMDLIB.model.UserEntity;
import lib.smd.SMDLIB.repo.UserRepo;

@RestController
@RequestMapping("/admin")
public class UserController {
				
	private final UserRepo userRep;
	private PasswordEncoder passEnc;
		
	public UserController(UserRepo userRep, PasswordEncoder passEnc) {
		this.userRep = userRep;
		this.passEnc = passEnc;
	}

//--------------------------------------GET----------------------------------------|
	@GetMapping("/users/all")
	List<UserEntity> findAllUsers(){
		return userRep.displayTable();
	}
		
	@GetMapping("/users/{id}")
	UserEntity findByID(@PathVariable int id) {
		return userRep.displayUser(id);
	}
	
//--------------------------------------POST----------------------------------------|
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/users/add")
	void createUser(@RequestBody UserDto jsonUser) {
		userRep.addUserToDB(jsonUser.username, passEnc.encode(jsonUser.password), jsonUser.role, jsonUser.email);
	}
	
//--------------------------------------DELETE----------------------------------------|		
	@ResponseStatus(HttpStatus.GONE)
	@DeleteMapping("/users/delete/{id}")
	void deleteUser(@PathVariable int id) {
		userRep.deleteUserFromDB(id);
	}
		
}
