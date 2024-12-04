package lib.smd.SMDLIB.model;

import jakarta.persistence.Entity;

@Entity
public record UserEntity(
		int user_id, 
		String username, 
		String pass, 
		String role_id, 
		String email
		){
}
