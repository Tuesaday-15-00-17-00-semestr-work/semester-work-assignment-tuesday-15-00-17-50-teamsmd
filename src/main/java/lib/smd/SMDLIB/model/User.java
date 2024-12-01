package lib.smd.SMDLIB.model;

import jakarta.persistence.Entity;

@Entity
public record User(
		int user_id, 
		String username, 
		String pass, 
		int role_id, 
		String email
		){
}
