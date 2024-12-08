package lib.smd.SMDLIB.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import lib.smd.SMDLIB.Dto.AuthD.LoginDto;
import lib.smd.SMDLIB.model.UserEntity;

@Service
public class UserService {
	
	@Autowired
	AuthenticationManager authMan;
	
	@Autowired
	private JWTService jwtServ;
	
	public String verify(LoginDto dto) {
		Authentication auth = authMan.authenticate(new UsernamePasswordAuthenticationToken(dto.username, dto.password));
		if(auth.isAuthenticated()) {
			return jwtServ.generateToken(dto.username);
		}
		return "NO";
	}
}
