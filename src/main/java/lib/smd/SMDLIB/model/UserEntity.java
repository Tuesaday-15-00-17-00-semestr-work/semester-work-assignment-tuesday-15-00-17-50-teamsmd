package lib.smd.SMDLIB.model;

public record UserEntity(
		int user_id, 
		String username, 
		String pass, 
		String role_id, 
		String email
		){
}
