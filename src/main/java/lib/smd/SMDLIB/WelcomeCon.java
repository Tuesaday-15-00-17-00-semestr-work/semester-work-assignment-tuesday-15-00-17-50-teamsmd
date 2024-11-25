package lib.smd.SMDLIB;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeCon {
	
	@GetMapping("/home")
	public String welcome() {
		return "Welcome to SMD library!";
	}
}
