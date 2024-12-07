package lib.smd.SMDLIB;

import org.slf4j.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import Database.DatabaseConnection;

@SpringBootApplication
public class SmdlibApplication {
	
	private static DatabaseConnection DBC;
	
	private static final Logger log = LoggerFactory.getLogger(SmdlibApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SmdlibApplication.class, args);
		log.info(DBC.connectToDB());
		log.info("Server is UP!");
	}
}
