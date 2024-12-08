package lib.smd.SMDLIB.controller;

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

import lib.smd.SMDLIB.Dto.AuthD.JWTResponseDto;
import lib.smd.SMDLIB.Dto.AuthD.LoginDto;
import lib.smd.SMDLIB.Dto.AuthD.RegisterDto;
import lib.smd.SMDLIB.Security.JTWGenerator;
import lib.smd.SMDLIB.model.UserEntity;
import lib.smd.SMDLIB.repo.UserRepo;

@RestController
@RequestMapping("/auth")
public class AuthControll {
	
	private AuthenticationManager authMan;
	private UserRepo userRep;
	private PasswordEncoder passEnc;
	private JTWGenerator tokgen;
	
	@Autowired
	public AuthControll(AuthenticationManager authMan, UserRepo userRep, PasswordEncoder passEnc, JTWGenerator tokgen) {
		this.authMan = authMan;
		this.userRep = userRep;
		this.passEnc = passEnc;
		this.tokgen = tokgen;
	}

//--------------------------------------LOGIN----------------------------------------|
	@PostMapping("/login")
	public ResponseEntity<JWTResponseDto> login(@RequestBody LoginDto logdto){
		Authentication authentication = authMan.authenticate(
				new UsernamePasswordAuthenticationToken(logdto.username, logdto.password));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = tokgen.generateToken(authentication);
		System.out.println(token);
		return new ResponseEntity<>(new JWTResponseDto(token),HttpStatus.OK);
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
