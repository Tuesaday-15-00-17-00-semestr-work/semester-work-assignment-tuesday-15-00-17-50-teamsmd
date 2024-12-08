package lib.smd.SMDLIB.Dto.AuthD;

public class JWTResponseDto {
	public String token;
	public String type;
	
	public JWTResponseDto(String token) {
		this.token = token;
	}
}
