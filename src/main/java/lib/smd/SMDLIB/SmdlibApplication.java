package lib.smd.SMDLIB;

import org.slf4j.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SmdlibApplication {
	
	private static final Logger log = LoggerFactory.getLogger(SmdlibApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SmdlibApplication.class, args);
		log.info("ZMENA!");
	}

}
