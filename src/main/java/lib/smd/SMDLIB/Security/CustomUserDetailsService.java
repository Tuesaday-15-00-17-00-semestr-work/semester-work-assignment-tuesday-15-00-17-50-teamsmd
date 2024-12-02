package lib.smd.SMDLIB.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lib.smd.SMDLIB.model.UserEntity;
import org.springframework.security.core.userdetails.User;
import lib.smd.SMDLIB.repo.UserRepo;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	private UserRepo userRep;
	
	@Autowired
	public CustomUserDetailsService(UserRepo userRep) {
		this.userRep = userRep;
	}
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserEntity newUser = userRep.returnUserByEmail(email);
		//UserDetails newDet = UserDetails.class.cast(newUser);
		UserDetails retUser = User.withUsername(newUser.email()).password(newUser.pass()).roles(String.valueOf(newUser.role_id())).build();
		return retUser;
	}

}
