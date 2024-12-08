package lib.smd.SMDLIB.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lib.smd.SMDLIB.SmdlibApplication;
import lib.smd.SMDLIB.Dto.AuthD.LoginDto;
import lib.smd.SMDLIB.Dto.AuthD.RegisterDto;
import lib.smd.SMDLIB.Security.UserService;
import lib.smd.SMDLIB.model.UserEntity;
import lib.smd.SMDLIB.repo.UserRepo;

@RestController
@RequestMapping("/auth")
public class AuthControll {
	
	private AuthenticationManager authMan;
	private UserRepo userRep;
	private PasswordEncoder passEnc;
	private UserService ser;
	
	private static final Logger log = LoggerFactory.getLogger(SmdlibApplication.class);
	
	@Autowired
	public AuthControll(AuthenticationManager authMan, UserRepo userRep, PasswordEncoder passEnc, UserService ser) {
		this.authMan = authMan;
		this.userRep = userRep;
		this.passEnc = passEnc;
		this.ser = ser;
	}

//--------------------------------------LOGIN----------------------------------------|
	@PostMapping("/login")
	public String login(@RequestBody LoginDto logdto){
		log.info("User " + logdto.username + " logged in!");
		return ser.verify(logdto);
	}
//-------------------------------------REGISTER--------------------------------------|
	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody RegisterDto regdto){
		UserEntity tstUser = userRep.returnUserByEmail(regdto.username);
		if(tstUser != null) {
			return new ResponseEntity<>("User with that email already exists!",HttpStatus.BAD_REQUEST);
		}
		userRep.addUserToDB(regdto.name, passEnc.encode(regdto.password), regdto.role, regdto.username);
		return new ResponseEntity<>("User registered!",HttpStatus.OK);
	}
}
