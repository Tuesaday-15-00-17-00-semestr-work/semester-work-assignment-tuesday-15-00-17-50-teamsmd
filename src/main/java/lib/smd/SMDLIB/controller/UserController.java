package lib.smd.SMDLIB.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lib.smd.SMDLIB.model.UserEntity;
import lib.smd.SMDLIB.repo.UserRepo;

@RestController
@RequestMapping("/admin")
public class UserController {
		
		
		private final UserRepo userRep;
		
		public UserController(UserRepo userRep) {
			this.userRep = userRep;
		}
		
		@GetMapping("/users/all")
		List<UserEntity> findAllUsers(){
			return userRep.displayTable();
		}
		
		@GetMapping("/users/{id}")
		UserEntity findByID(@PathVariable int id) {
			return userRep.displayUser(id);
		}
		
		@ResponseStatus(HttpStatus.CREATED)
		@PostMapping("/users/add")
		void createUser(@RequestBody String username, String password, int role, String email) {
			userRep.addUserToDB(username, password, role, email);
		}
		
		@ResponseStatus(HttpStatus.GONE)
		@DeleteMapping("/admin/delete")
		void deleteUser(@RequestBody int id) {
			userRep.deleteUserFromDB(id);
		}
		
}
