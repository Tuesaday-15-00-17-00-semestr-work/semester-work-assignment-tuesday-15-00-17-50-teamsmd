package lib.smd.SMDLIB.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lib.smd.SMDLIB.model.User;
import lib.smd.SMDLIB.repo.UserRepo;

@RestController
@RequestMapping("/api/users")
public class UserController {
		
		
		private final UserRepo userRep;
		
		public UserController(UserRepo userRep) {
			this.userRep = userRep;
		}
		
		@GetMapping("")
		List<String> findAllUsers(){
			return userRep.findAllUsers();
		}
		
		@GetMapping("/{id}")
		String findByID(@PathVariable int id) {
			return userRep.findByID(id);
		}
}
