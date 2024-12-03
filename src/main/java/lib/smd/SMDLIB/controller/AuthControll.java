package lib.smd.SMDLIB.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lib.smd.SMDLIB.Dto.RegisterDto;
import lib.smd.SMDLIB.model.UserEntity;
import lib.smd.SMDLIB.repo.UserRepo;

@RestController
@RequestMapping("/auth")
public class AuthControll {
	
	private AuthenticationManager authMan;
	private UserRepo userRep;
	private PasswordEncoder passEnc;
	
	@Autowired
	public AuthControll(AuthenticationManager authMan, UserRepo userRep, PasswordEncoder passEnc) {
		this.authMan = authMan;
		this.userRep = userRep;
		this.passEnc = passEnc;
	}
	
	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody RegisterDto regdto){
		UserEntity tstUser = userRep.returnUserByEmail(regdto.username);
		System.out.println(tstUser);
		if(tstUser != null) {
			return new ResponseEntity<>("User with that email already exists!",HttpStatus.BAD_REQUEST);
		}
		userRep.addUserToDB(regdto.name, passEnc.encode(regdto.password), regdto.role, regdto.username);
		return new ResponseEntity<>("User registered!",HttpStatus.OK);
	}
}
